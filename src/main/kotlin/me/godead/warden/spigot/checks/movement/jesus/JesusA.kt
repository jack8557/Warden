package me.godead.warden.spigot.checks.movement.jesus

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.users.BoundingBox
import me.godead.anticheat.users.User
import me.godead.anticheat.utils.XMaterial

@Info(name = "Jesus", type = 'A')
class JesusA : Check() {

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (user.collisionManager.touchingAny(XMaterial.LILY_PAD) || user.positionManager.isNearBoat) return
        when (user.collisionManager.touchingAll(
            BoundingBox(
                user.player.location.clone().add(0.0, -0.1, 0.1)
            ).expand(0.5, 0.0, 0.5), XMaterial.WATER, XMaterial.LAVA
        ) && user.collisionManager.touchingAny(0.1, XMaterial.AIR)) {
            true -> if (preVL++ > 10) flag(user)
            false -> preVL = 0
        }
    }

}