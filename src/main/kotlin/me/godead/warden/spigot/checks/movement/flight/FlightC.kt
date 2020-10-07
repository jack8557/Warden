package me.godead.warden.spigot.checks.movement.flight

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.extensions.isFlying
import me.godead.anticheat.users.User

@Info(name = "Flight", type = 'C')
class FlightC : Check() {

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (!event.packetId.isFlying()) return
        if (user.collisionManager.isNear("water") || user.collisionManager.isNear("lava")) return
        if (user.positionManager.onGround != (user.positionManager.airTicks.getTicks() == 0)) {
            if (preVL++ > 10) flag(user)
        } else preVL = 0
    }

}