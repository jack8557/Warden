package me.godead.warden.spigot.checks.movement.speed

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import io.github.retrooper.packetevents.packettype.PacketType
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.extensions.color
import me.godead.anticheat.extensions.debug
import me.godead.anticheat.extensions.getPotionLevel
import me.godead.anticheat.extensions.isFlying
import me.godead.anticheat.users.User
import me.godead.anticheat.utils.XMaterial
import org.bukkit.potion.PotionEffectType

@Info(name = "Speed", type = 'B')
class SpeedB : Check() {

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (!event.packetId.isFlying() || event.packetId == PacketType.Client.FLYING) return

        if (user.player.isFlying) return

        if (user.positionManager.deltaXZ > 100.0) return

        if (user.collisionManager.touchingAny(XMaterial.ICE, XMaterial.PACKED_ICE, XMaterial.BLUE_ICE, XMaterial.FROSTED_ICE)) return

        var maxSpeed =
            if (user.positionManager.deltaY != 0.0) 0.62 else if (user.collisionManager.isNear("stair")) 0.41 else 0.34


        maxSpeed += user.player.getPotionLevel(PotionEffectType.SPEED) * 0.2

        if (user.positionManager.deltaXZ > maxSpeed && user.actionManager.damageTicks.getTicks() > 20) {
            if (preVL++ > 2)
            flag(user)
        } else preVL = 0
    }
}