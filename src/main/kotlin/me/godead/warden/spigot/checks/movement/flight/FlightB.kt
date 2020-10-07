package me.godead.warden.spigot.checks.movement.flight

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.users.User
import org.bukkit.potion.PotionEffectType
import kotlin.math.abs

@Info(name = "Flight", type = 'B')
class FlightB : Check() {

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (user.player.isFlying ||
            user.player.isInsideVehicle ||
            user.positionManager.isNearBoat
        ) return

        if (user.player.hasPotionEffect(PotionEffectType.JUMP)) return

        val predicted = (user.positionManager.lastDeltaY - 0.08) * 0.9800000190734863

        if (user.positionManager.airTicks.getTicks() > 6 && abs(predicted) >= 0.005 && user.actionManager.damageTicks.getTicks() > 20) {
            when (user.positionManager.deltaY - predicted > 0.001) {
                true -> if (preVL++ > 5) flag(user)
                false -> preVL = 0
            }
        }
    }
}