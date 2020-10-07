package me.godead.warden.spigot.command

import me.godead.anticheat.extensions.color
import me.godead.anticheat.extensions.getUser
import me.godead.anticheat.users.User
import me.godead.anticheat.users.UserManager
import me.godead.warden.spigot.plugin.Warden
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import java.util.*


class WardenCommand(name: String) : BukkitCommand(name) {
    override fun execute(
            sender: CommandSender,
            alias: String,
            args: Array<String>
    ): Boolean {
        val isConsole = sender is ConsoleCommandSender
        if (args.isEmpty()) {
            tell(
                    sender,
                    "&f[&b&lW&f] Running Warden version &3R" + Warden.plugin.description.version
            )
            return true
        }
        val data: User? = if (isConsole) null else (sender as Player).getUser()
        if (args[0] == "alerts") {
            if (data == null) return false
            if (!sender.hasPermission("warden.alerts")) {
                tell(sender, config.getString("NoPermission")!!)
                return true
            } else if (isConsole) {
                tell(sender, config.getString("NoConsole")!!)
                return true
            } else {
                if (data.alerts) {
                    tell(sender, config.getString("AlertsOff")!!)
                } else {
                    tell(sender, config.getString("AlertsOn")!!)
                }
                data.alerts = !data.alerts
            }
        } else if (args[0] == "reload") {
            if (!sender.hasPermission("warden.reload")) {
                tell(sender, config.getString("NoPermission")!!)
                return true
            } else {
                Warden.plugin.saveConfig()
                Warden.plugin.reloadConfig()
                tell(sender, config.getString("ReloadMessage")!!)
            }
        } else if (args[0] == "bungee") {
            if (!sender.hasPermission("warden.bungee")) {
                tell(sender, config.getString("NoPermission")!!)
                return true
            } else {
                if (config.getBoolean("BungeeCord-Alerts")) {
                    tell(sender, config.getString("BungeeAlertsOff")!!)
                } else {
                    tell(sender, config.getString("BungeeAlertsOn")!!)
                }
                config["BungeeCord-Alerts"] = !config.getBoolean("BungeeCord-Alerts")
                Warden.plugin.saveConfig()
                Warden.plugin.reloadConfig()
            }
        } else if (args[0] == "support") {
            if (!sender.hasPermission("warden.support")) {
                tell(sender, config.getString("NoPermission")!!)
                return true
            } else {
                tell(sender, "&f[&b&lW&f] Our Discord server:&b https://discord.gg/r4mz3ZX")
            }
        } else if (args[0] == "gui") {
            if (!sender.hasPermission("warden.gui")) {
                tell(sender, config.getString("NoPermission")!!)
                return true
            } else {
                tell(sender, "&f[&b&lW&f] The GUI is currently unavailable.")
            }
        } else if (args[0] == "vl") {
            if (!sender.hasPermission("warden.checkvl")) {
                tell(sender, config.getString("NoPermission")!!)
                return true
            } else if (args.size != 2) {
                tell(sender, config.getString("InvalidArguments")!!)
            } else {
                val player = Bukkit.getPlayer(args[1])
                if (player == null) {
                    tell(sender, config.getString("InvalidPlayer")!!)
                    return true
                }
                if (!player.isOnline) {
                    tell(sender, config.getString("InvalidPlayer")!!)
                    return true
                }
                val user = UserManager.getUser(player.uniqueId) ?: return true
                var totalVl = 0
                for (check in user.checks) {
                    totalVl += check.vl
                }
                tell(
                        sender,
                        Objects.requireNonNull<String>(config.getString("TotalVL"))
                                .replace("%player%", player.name).replace("%vl%", totalVl.toString())
                )
            }
        } else if (args[0] == "clear") {
            if (!sender.hasPermission("warden.clearvl")) {
                tell(sender, config.getString("NoPermission")!!)
                return true
            } else if (args.size != 2) {
                tell(sender, config.getString("InvalidArguments")!!)
            } else {
                val player = Bukkit.getPlayer(args[1])
                if (player == null) {
                    tell(sender, config.getString("InvalidPlayer")!!)
                    return true
                }
                if (!player.isOnline) {
                    tell(sender, config.getString("InvalidPlayer")!!)
                    return true
                }
                val user = UserManager.getUser(player.uniqueId) ?: return true
                for (check in user.checks) {
                    check.vl = 0
                }
                tell(
                        sender,
                        Objects.requireNonNull<String>(config.getString("ClearVL"))
                                .replace("%player%", player.name)
                )
            }
        } else if (args[0] == "world") {
            if (!sender.hasPermission("warden.world")) {
                tell(sender, config.getString("NoPermission")!!)
                return true
            } else if (args.size != 3) {
                tell(sender, config.getString("InvalidArguments")!!)
            } else {
                if (args[1] == "add") {
                    if (config.getStringList("Worlds").contains(args[2])) tell(
                            sender,
                            Objects.requireNonNull<String>(config.getString("WorldAlreadyInList"))
                                    .replace("%world%", args[2])
                    ) else {
                        val worlds = config.getStringList("Worlds")
                        worlds.add(args[2])
                        config["Worlds"] = worlds
                        Warden.plugin.saveConfig()
                        Warden.plugin.reloadConfig()
                        tell(
                                sender,
                                Objects.requireNonNull<String>(config.getString("WorldAdded"))
                                        .replace("%world%", args[2])
                        )
                    }
                } else if (args[1] == "remove") {
                    if (!config.getStringList("Worlds").contains(args[2])) tell(
                            sender,
                            Objects.requireNonNull<String>(config.getString("WorldMissingInList"))
                                    .replace("%world%", args[2])
                    ) else {
                        val worlds = config.getStringList("Worlds")
                        worlds.remove(args[2])
                        config["Worlds"] = worlds
                        Warden.plugin.saveConfig()
                        Warden.plugin.reloadConfig()
                        tell(
                                sender,
                                Objects.requireNonNull<String>(config.getString("WorldRemoved"))
                                        .replace("%world%", args[2])
                        )
                    }
                } else {
                    tell(sender, config.getString("InvalidArguments")!!)
                }
            }
        } else if (args[0] == "test") {
            if (sender is ConsoleCommandSender) return true
        }
        return false
    }

    private val config: FileConfiguration
        get() = Warden.plugin.config

    private fun tell(sender: CommandSender, message: String) {
        sender.sendMessage(message.color())
    }

    init {
        description = "Toggle alerts"
        usageMessage = "/warden alerts"
        permission = "warden.alerts"
        aliases = ArrayList()
    }
}