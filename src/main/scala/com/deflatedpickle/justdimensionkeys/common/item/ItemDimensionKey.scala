package com.deflatedpickle.justdimensionkeys.common.item

import com.deflatedpickle.justdimensionkeys.JustDimensionKeys
import com.deflatedpickle.justdimensionkeys.References._
import net.minecraft.block.material.Material
import net.minecraft.block.{Block, BlockAir}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.item.{EnumAction, Item, ItemStack}
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{ActionResult, EnumActionResult, EnumFacing, EnumHand}
import net.minecraft.world.{DimensionType, World}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

import scala.util.Random

class ItemDimensionKey(val dimension: DimensionType = DimensionType.OVERWORLD) extends Item {
  this.setRegistryName(s"$ModID:dimension_key_${dimension.name()}")
  // this.setCreativeTab(CreativeTabs.TOOLS)

  override def getItemStackDisplayName(stack: ItemStack): String = s"${dimension.getName.split("_").map(s => s.capitalize).mkString(" ")} Key"

  override def onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack = {
    if (!entityLiving.world.isRemote) {
      JustDimensionKeys.log.info(s"${entityLiving.getDisplayName.getUnformattedText} used a dimension key to travel to ${dimension.getName}")
      entityLiving.changeDimension(this.dimension.getId, (world: World, entity: Entity, yaw: Float) => {
        entity.moveToBlockPosAndAngles(recursiveIsInLand(world, world.getSpawnPoint), yaw, entity.rotationPitch)
      })
    }

    stack
  }

  override def onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult[ItemStack] = {
    val field = classOf[DimensionType].getDeclaredField("id")
    field.setAccessible(true)

    if (playerIn.dimension == field.get(this.dimension).asInstanceOf[Int]) {
      return new ActionResult(EnumActionResult.FAIL, playerIn.getHeldItem(handIn))
    }

    playerIn.setActiveHand(handIn)
    new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn))
  }

  override def getItemUseAction(stack: ItemStack): EnumAction = EnumAction.BOW

  override def getMaxItemUseDuration(stack: ItemStack): Int = 24

  override def getMaxDamage: Int = 6

  @scala.annotation.tailrec
  private def recursiveIsGood(world: World, blockPos: BlockPos): BlockPos = {
    if (world.canBlockSeeSky(blockPos)) blockPos
    else if ((!world.isAirBlock(blockPos) && !world.isAirBlock(blockPos.up))
      || !world.isBlockFullCube(blockPos)) {
      JustDimensionKeys.log.info(s"The blocks at $blockPos and ${blockPos.up} are not air OR the block at $blockPos isn't a full cube")
      recursiveIsGood(world, blockPos.up)
    }
    else {
      JustDimensionKeys.log.info(s"The block at $blockPos is all good! Moving the player there!")
      blockPos
    }
  }

  // TODO: This might be a bit intensive... fix it?
  @scala.annotation.tailrec
  private def recursiveIsInLand(world: World, blockPos: BlockPos, counter: Int = 0): BlockPos = {
    if (world.isAirBlock(blockPos.down)) {
      val direction = EnumFacing.random(Random.self)

      // Look for a block above spawn up to 16 times
      if (counter < 16) {
        JustDimensionKeys.log.info(s"(First Tier Check) The block at $blockPos is air, looking 1 up")
        recursiveIsInLand(world, blockPos.up, counter + 1)
      }
      // Try to find a close block up to 64 times
      else if (counter < 64) {
        val offset = Random.self.nextInt(7) + 1
        JustDimensionKeys.log.info(s"(Second Tier Check) The block at $blockPos is air, checking $offset blocks $direction")
        recursiveIsInLand(world, blockPos.offset(direction, offset), counter + 1)
      }
      // We're never going to find it with small increments, panic!
      else {
        val start = 319
        val end = 1119

        val offset = start + Random.self.nextInt(end - start) + 1
        JustDimensionKeys.log.info(s"(Third Tier Check) The block at $blockPos is air, checking $offset blocks $direction")
        recursiveIsInLand(world, blockPos.offset(direction, offset), 64)
      }
    }
    // A good block was found!
    else {
      JustDimensionKeys.log.info(s"The block at $blockPos isn't air! Checking if it's good")
      recursiveIsGood(world, blockPos)
    }
  }
}
