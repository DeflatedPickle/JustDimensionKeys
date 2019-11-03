package com.deflatedpickle.justdimensionkeys

import com.deflatedpickle.justdimensionkeys.References._
import com.deflatedpickle.justdimensionkeys.common.Proxy
import com.deflatedpickle.justdimensionkeys.common.item.ItemDimensionKey
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import org.apache.logging.log4j.LogManager

import scala.collection.mutable

@Mod(modid = ModID, name = Name, version = Version, acceptedMinecraftVersions = AcceptedVersions, modLanguage = "scala")
object JustDimensionKeys {
  @SidedProxy(clientSide = ClientProxyClass, serverSide = ServerProxyClass)
  var proxy: Proxy = _

  val log = LogManager.getLogger(Name)

  val keyList: mutable.MutableList[ItemDimensionKey] = mutable.MutableList[ItemDimensionKey]()

  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = {
    log.info("Starting PreInit.")
    proxy.preInit(event)
    log.info("Finished PreInit.")
  }
}
