package com.deflatedpickle.justdimensionkeys.client.event.handler

import com.deflatedpickle.justdimensionkeys.JustDimensionKeys
import com.deflatedpickle.justdimensionkeys.References.ModID
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class Forge {
  @SubscribeEvent
  def onModelRegistryEvent(event: ModelRegistryEvent): Unit = {
    var i = 0
    // for (i <- JustDimensionKeys.keyList) {
    while (i < JustDimensionKeys.keyList.length) {
      registerItemModel(JustDimensionKeys.keyList(i), 0, "inventory")
      i += 1
    }
  }

  /* @SubscribeEvent
  def onColorHandlerItemEvent(event: ColorHandlerEvent.Item): Unit = {
    var i = 0
    // for (i <- JustDimensionKeys.keyList) {
    while (i < JustDimensionKeys.keyList.length) {
      val item = JustDimensionKeys.keyList.apply(i)
      event.getItemColors.registerItemColorHandler(new IItemColor {
        override def colorMultiplier(stack: ItemStack, tintIndex: Int): Int = item.dimension.getName.hashCode // This isn't the best way to get a colour
      }, item)
      i += 1
    }
  } */

  def registerItemModel(item: Item, meta: Int, variant: String): Unit =
    ModelLoader.setCustomModelResourceLocation(
      item,
      meta,
      new ModelResourceLocation(
        s"$ModID:dimension_key",
        variant)
    )
}
