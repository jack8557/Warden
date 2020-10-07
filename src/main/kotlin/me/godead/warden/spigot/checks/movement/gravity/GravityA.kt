package me.godead.warden.spigot.checks.movement.gravity

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import io.github.retrooper.packetevents.packettype.PacketType
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.extensions.hasLevitation
import me.godead.anticheat.extensions.isFlying
import me.godead.anticheat.users.User
import org.bukkit.potion.PotionEffectType

@Info(name = "Gravity", type = 'A')
class GravityA : Check() {

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (!event.packetId.isFlying()) return
        if (user.player.isFlying) return
        if (user.player.hasLevitation()) return
        if (user.player.hasPotionEffect(PotionEffectType.JUMP)) return
        if (user.positionManager.airTicks.getTicks() > 10) flag(user)
    }

}