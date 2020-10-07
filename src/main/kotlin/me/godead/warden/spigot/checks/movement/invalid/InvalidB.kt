package me.godead.warden.spigot.checks.movement.invalid

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.users.User
import org.bukkit.potion.PotionEffectType

@Info(name = "Invalid", type = 'B')
class InvalidB : Check() {

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (user.collisionManager.isNear("bed")) return
        if (user.player.isInsideVehicle) return
        if (user.player.hasPotionEffect(PotionEffectType.JUMP) && user.player.isFlying) return

        if (user.positionManager.deltaY > 0.6 && user.positionManager.deltaY < 10.0) {
            flag(user)
        }
    }

}