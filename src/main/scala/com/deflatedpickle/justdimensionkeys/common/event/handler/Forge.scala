package com.deflatedpickle.justdimensionkeys.common.event.handler

import com.deflatedpickle.justdimensionkeys.common.item.ItemDimensionKey
import net.minecraft.item.Item
import net.minecraft.world.DimensionType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class Forge {
  @SubscribeEvent
  def onRegisterItem(event: RegistryEvent.Register[Item]): Unit = {
    for (d <- DimensionType.values) {
      event.getRegistry.register(new ItemDimensionKey(d))
    }
  }
}
