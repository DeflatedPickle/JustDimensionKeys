package com.deflatedpickle.justdimensionkeys.common.event.handler

import com.deflatedpickle.justdimensionkeys.JustDimensionKeys
import com.deflatedpickle.justdimensionkeys.References.ModID
import com.deflatedpickle.justdimensionkeys.common.item.ItemDimensionKey
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.ResourceLocation
import net.minecraft.world.DimensionType
import net.minecraftforge.client.event.{ColorHandlerEvent, ModelRegistryEvent}
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

import scala.collection.mutable

class Forge {
  @SubscribeEvent
  def onRegisterItemEvent(event: RegistryEvent.Register[Item]): Unit = {
    for (d <- DimensionType.values) {
      val item = new ItemDimensionKey(d)
      JustDimensionKeys.keyList += item
    }
    event.getRegistry.registerAll(JustDimensionKeys.keyList: _*)
  }

  @SubscribeEvent
  def onModelRegistryEvent(event: ModelRegistryEvent): Unit = {
    for (i <- JustDimensionKeys.keyList) {
      registerItemModel(i, 0, "inventory")
    }
  }

  @SubscribeEvent
  def onColorHandlerItemEvent(event: ColorHandlerEvent.Item): Unit =
    for (i <- JustDimensionKeys.keyList) {
      event.getItemColors.registerItemColorHandler(new IItemColor {
        override def colorMultiplier(stack: ItemStack, tintIndex: Int): Int = i.dimension.getName.hashCode // This isn't the best way to get a colour
      }, i)
    }


  def registerItemModel(item: Item, meta: Int, variant: String): Unit =
    ModelLoader.setCustomModelResourceLocation(
      item,
      meta,
      new ModelResourceLocation(
        s"$ModID:dimension_key",
        variant)
    )
}
