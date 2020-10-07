package me.godead.warden.spigot.checks.movement.fastclimb

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.users.User

@Info(name = "FastClimb", type = 'A')
class FastClimbA : Check() {

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (user.player.isFlying) return
        when (user.positionManager.deltaY > 0.1177 && user.positionManager.climbableTicks.getTicks() < 1) {
            true -> if (preVL++ > 5) flag(user)
            false -> preVL = 0
        }
    }

}