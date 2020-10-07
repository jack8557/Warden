package me.godead.warden.bungeecord

import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Plugin
import java.util.*

class Main : Plugin() {

    var alertsOn: MutableList<ProxiedPlayer> = ArrayList()

    override fun onEnable() {
        instance = this
        proxy.pluginManager.registerCommand(this, AlertCommand())
        proxy.registerChannel("me.godead.warden:alerts")
        proxy.pluginManager.registerListener(this, JoinEvent())
        proxy.pluginManager.registerListener(this, Alert())
    }

    companion object {
        lateinit var instance: Main
    }
}