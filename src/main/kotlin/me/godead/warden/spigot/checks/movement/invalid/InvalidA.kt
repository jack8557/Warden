package me.godead.warden.spigot.checks.movement.invalid

import io.github.retrooper.packetevents.enums.ClientVersion
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.extensions.isFlying
import me.godead.anticheat.users.User
import me.godead.anticheat.utils.XMaterial

@Info (name = "Invalid", type = 'A')
class InvalidA : Check() {

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (!user.clientVersion.isLowerThan(ClientVersion.v_1_9)) return
        if (!event.packetId.isFlying()) return
        if (user.collisionManager.isNear("water") || user.collisionManager.isNear("lava")) return
        if (user.positionManager.deltaY < 0.005 && user.positionManager.deltaY > 0) flag(user)
    }

}