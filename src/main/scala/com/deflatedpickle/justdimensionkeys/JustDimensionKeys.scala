package com.deflatedpickle.justdimensionkeys

import com.deflatedpickle.justdimensionkeys.References._
import com.deflatedpickle.justdimensionkeys.common.Proxy
import net.minecraft.item.Item
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import org.apache.logging.log4j.LogManager

import scala.collection.mutable

@Mod(modid = ModID, name = Name, version = Version, acceptedMinecraftVersions = AcceptedVersions, modLanguage = "scala")
object JustDimensionKeys {
  val log = LogManager.getLogger(Name)

  @SidedProxy(clientSide = ClientProxyClass, serverSide = ServerProxyClass)
  var proxy: Proxy = _

  val keyList: mutable.MutableList[Item] = mutable.MutableList[Item]()

  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = {
    log.info("Starting PreInit.")
    proxy.preInit(event)
    log.info("Finished PreInit.")
  }
}
