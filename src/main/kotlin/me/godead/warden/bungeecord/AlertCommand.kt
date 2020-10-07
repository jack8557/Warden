package me.godead.warden.bungeecord

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command

class AlertCommand : Command("bungeealerts", "me.godead.warden.alerts") {

    override fun execute(commandSender: CommandSender, strings: Array<String>) {
        if (commandSender is ProxiedPlayer) {
            val player = commandSender
            val contains: Boolean = Main.instance.alertsOn.contains(player)
            if (contains) {
                Main.instance.alertsOn.remove(player)
            } else {
                Main.instance.alertsOn.add(player)
            }
            player.sendMessage(
                TextComponent(
                    ChatColor.translateAlternateColorCodes(
                        '&',
                        "§8[§cWarden§8] &7Your alerts are now &r" + if (Main.instance.alertsOn.contains(
                                player
                            )
                        ) "&aon" else "&coff"
                    )
                )
            )
        }
    }
}