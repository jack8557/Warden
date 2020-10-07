package me.godead.warden.bungeecord

import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class JoinEvent : Listener {
    @EventHandler
    fun onJoin(event: PostLoginEvent) {
        if (event.player.hasPermission("me.godead.warden.alerts")) Main.instance.alertsOn.add(event.player)
    }
}