package me.godead.warden.spigot.api

import me.godead.anticheat.check.Check
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class WardenFlagEvent(var player: Player, cheat: String, type: Char, vl: Float, check: Check) : Event(), Cancellable {
    
    var cheat: String = cheat
    var type: Char = type
    var vl: Int = vl.toInt()
    var check: Check = check
    private var isCancelled = false
    
    override fun isCancelled(): Boolean {
        return isCancelled
    }

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList = HandlerList()
    }

}