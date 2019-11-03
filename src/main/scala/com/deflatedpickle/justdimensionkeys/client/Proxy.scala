package com.deflatedpickle.justdimensionkeys.client

import com.deflatedpickle.justdimensionkeys.client.event.handler.Forge
import com.deflatedpickle.justdimensionkeys.common.{Proxy => CommonProxy}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

class Proxy extends CommonProxy {
  override def preInit(event: FMLPreInitializationEvent): Unit = {
    super.preInit(event)
    MinecraftForge.EVENT_BUS.register(new Forge)
  }
}
