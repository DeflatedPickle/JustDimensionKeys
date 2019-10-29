package com.deflatedpickle.justdimensionkeys.common.item

import com.deflatedpickle.justdimensionkeys.References._
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.item.{EnumAction, Item, ItemStack}
import net.minecraft.util.{ActionResult, EnumActionResult, EnumHand}
import net.minecraft.world.{DimensionType, World}

class ItemDimensionKey(val dimension: DimensionType = DimensionType.OVERWORLD) extends Item {
  this.setRegistryName(s"$ModID:dimension_key_${dimension.getName}")
  this.setCreativeTab(CreativeTabs.TOOLS)

  override def getItemStackDisplayName(stack: ItemStack): String = s"${dimension.getName.split("_").map(s => s.capitalize).mkString(" ")} Key"

  override def onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack = {
    if (!entityLiving.world.isRemote) {
      entityLiving.changeDimension(this.dimension.getId, (world: World, entity: Entity, yaw: Float) => {
        entity.moveToBlockPosAndAngles(world.getSpawnPoint, yaw, entity.rotationPitch)
      })
    }

    stack
  }

  override def onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult[ItemStack] = {
    if (playerIn.dimension == this.dimension.getId) {
      return new ActionResult(EnumActionResult.FAIL, playerIn.getHeldItem(handIn))
    }

    playerIn.setActiveHand(handIn)
    new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn))
  }

  override def getItemUseAction(stack: ItemStack): EnumAction = EnumAction.BOW

  override def getMaxItemUseDuration(stack: ItemStack): Int = 24

  override def getMaxDamage: Int = 6
}
