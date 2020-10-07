package me.godead.warden.spigot.plugin

import me.godead.anticheat.check.Check
import me.godead.anticheat.extensions.registerCheck
import me.godead.warden.spigot.checks.combat.aura.AuraA
import me.godead.warden.spigot.checks.combat.aura.AuraB
import me.godead.warden.spigot.checks.combat.aura.AuraC
import me.godead.warden.spigot.checks.combat.aura.AuraD
import me.godead.warden.spigot.checks.movement.fastclimb.FastClimbA
import me.godead.warden.spigot.checks.movement.flight.FlightA
import me.godead.warden.spigot.checks.movement.flight.FlightB
import me.godead.warden.spigot.checks.movement.flight.FlightC
import me.godead.warden.spigot.checks.movement.gravity.GravityA
import me.godead.warden.spigot.checks.movement.invalid.InvalidA
import me.godead.warden.spigot.checks.movement.invalid.InvalidB
import me.godead.warden.spigot.checks.movement.jesus.JesusA
import me.godead.warden.spigot.checks.movement.speed.SpeedA
import me.godead.warden.spigot.checks.movement.speed.SpeedB
import me.godead.warden.spigot.checks.packets.fastplace.FastPlaceA
import me.godead.warden.spigot.checks.packets.timer.TimerA
import me.godead.warden.spigot.command.WardenCommand
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap

object Warden {

    lateinit var plugin: WardenPlugin

    fun init(plugin: WardenPlugin) {
        this.plugin = plugin
        loadChecks()
        registerCommands("warden", WardenCommand("warden"))
    }

    fun lateInit() {

    }

    fun terminate(plugin: WardenPlugin) {
        this.plugin = plugin
    }

    private fun loadChecks() {
        registerCheck(AuraA())
        registerCheck(AuraB())
        registerCheck(AuraC())
        registerCheck(AuraD())

        registerCheck(FastClimbA())

        registerCheck(FlightA())
        registerCheck(FlightB())
        registerCheck(FlightC())

        registerCheck(GravityA())

        registerCheck(InvalidA())
        registerCheck(InvalidB())

        registerCheck(JesusA())

        registerCheck(SpeedA())
        registerCheck(SpeedB())

        registerCheck(FastPlaceA())

        registerCheck(TimerA())
    }

    private fun registerCommands(prefix: String, command: Command) {
        try {
            val bukkitCommandMap =
                Bukkit.getServer().javaClass.getDeclaredField("commandMap")
            bukkitCommandMap.isAccessible = true
            val commandMap =
                bukkitCommandMap[Bukkit.getServer()] as CommandMap
            commandMap.register(prefix, command)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}