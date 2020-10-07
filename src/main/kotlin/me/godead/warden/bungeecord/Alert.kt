package me.godead.warden.bungeecord

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.connection.Server
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.IOException

class Alert : Listener {
    @EventHandler
    fun onAlert(event: PluginMessageEvent) {
        if (event.isCancelled ||
            event.tag != "me.godead.warden:alerts" ||
            event.sender !is Server
        ) return
        try {
            val `in` = DataInputStream(ByteArrayInputStream(event.data))
            val player = `in`.readUTF()
            val cheater = ProxyServer.getInstance().getPlayer(player)
            val check = `in`.readUTF()
            val type = `in`.readUTF()
            val vl = `in`.readInt()
            alert(cheater, check, type, vl)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    private fun alert(player: ProxiedPlayer, check: String, type: String, vl: Int) {
        val component =
            TextComponent(ChatColor.DARK_GRAY.toString() + "[" + ChatColor.RED + "me.godead.warden.spigot.plugin.Warden" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + player.name + ChatColor.GRAY + " is suspected of " + ChatColor.YELLOW + check + ChatColor.GRAY + " (Type " + type + ")" + ChatColor.RED + " x" + vl + ChatColor.GRAY + " on: " + player.server.info.name.toUpperCase())
        for (proxy in Main.instance.proxy.players) {
            if (proxy.hasPermission("me.godead.warden.alerts")) {
                proxy.sendMessage(component as BaseComponent)
            }
        }
    }
}