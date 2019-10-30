package com.deflatedpickle.justdimensionkeys

import com.deflatedpickle.justdimensionkeys.References._
import com.deflatedpickle.justdimensionkeys.common.event.handler.Forge
import com.deflatedpickle.justdimensionkeys.common.item.ItemDimensionKey
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager

import scala.collection.mutable

@Mod(modid = ModID, name = Name, version = Version, acceptedMinecraftVersions = AcceptedVersions, modLanguage = "scala")
object JustDimensionKeys {
  val log = LogManager.getLogger(Name)

  val keyList: mutable.MutableList[ItemDimensionKey] = mutable.MutableList[ItemDimensionKey]()

  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = {
    log.info("Starting PreInit.")
    MinecraftForge.EVENT_BUS.register(new Forge)
    log.info("Finished PreInit.")
  }
}
