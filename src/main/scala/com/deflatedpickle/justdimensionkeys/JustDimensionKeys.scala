package com.deflatedpickle.justdimensionkeys

import com.deflatedpickle.justdimensionkeys.References._
import com.deflatedpickle.justdimensionkeys.common.Proxy
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import org.apache.logging.log4j.LogManager

@Mod(modid = ModID, name = Name, version = Version, acceptedMinecraftVersions = AcceptedVersions, modLanguage = "scala")
object JustDimensionKeys {
  val log = LogManager.getLogger(Name)

  @SidedProxy(clientSide = ClientProxyClass, serverSide = ServerProxyClass)
  var proxy: Proxy = _

  @EventHandler
  def preInit(event: FMLPreInitializationEvent) = {
    log.info("Starting PreInit.")
    proxy.preInit(event)
    log.info("Finished PreInit.")
  }
}
