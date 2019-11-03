package com.deflatedpickle.justdimensionkeys.common.event.handler

import com.deflatedpickle.justdimensionkeys.JustDimensionKeys
import com.deflatedpickle.justdimensionkeys.common.item.ItemDimensionKey
import net.minecraft.item.Item
import net.minecraft.world.DimensionType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class Forge {
  @SubscribeEvent
  def onRegisterItemEvent(event: RegistryEvent.Register[Item]): Unit = {
    var d = 0
    // for (d <- DimensionType.values) {
    while (d < DimensionType.values.length) {
      val item = new ItemDimensionKey(DimensionType.values.apply(d))
      JustDimensionKeys.keyList += item
      d += 1
    }
    event.getRegistry.registerAll(JustDimensionKeys.keyList: _*)
  }
}
