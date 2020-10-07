package me.godead.warden.spigot.checks.movement.speed

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.extensions.debug
import me.godead.anticheat.extensions.isFlying
import me.godead.anticheat.users.User
import me.godead.anticheat.utils.XMaterial

@Info(name = "Speed", type = 'A')
class SpeedA : Check() {

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (!event.packetId.isFlying()) return

        if (user.positionManager.airTicks.getTicks() > 1 && !user.player.isFlying && !user.collisionManager.touchingAny(
                XMaterial.WATER) && user.player.walkSpeed <= 0.2F) {

            val predicted = user.positionManager.lastDeltaXZ * 0.91F
            val diff: Double = user.positionManager.deltaXZ - predicted
            if (diff > 0.026) {
                if (preVL++ > 1) {
                    flag(user)
                }
            } else preVL = 0
        }
    }
}