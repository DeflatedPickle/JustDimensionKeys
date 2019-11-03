package com.deflatedpickle.justdimensionkeys.common

import com.deflatedpickle.justdimensionkeys.common.event.handler.Forge
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

class Proxy {
  def preInit(event: FMLPreInitializationEvent): Unit = {
    MinecraftForge.EVENT_BUS.register(new Forge)
  }
}
