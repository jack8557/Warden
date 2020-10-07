package me.godead.warden.spigot.checks.combat.aura

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import io.github.retrooper.packetevents.packettype.PacketType
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.extensions.isFlying
import me.godead.anticheat.users.User
import kotlin.math.abs

@Info(name = "KillAura", type = 'B')
class AuraB : Check() {

    private var hitTicks = 0

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (event.packetId.isFlying()) {
            val deltaXZ = user.positionManager.deltaXZ
            val lastDeltaXZ = user.positionManager.lastDeltaXZ

            if (user.actionManager.sprinting && hitTicks++ <= 2) {
                val accel = abs(deltaXZ - lastDeltaXZ)
                if (accel < 0.027) {
                    if (preVL++ > 4) flag(user)
                } else preVL = 0
            }
        } else if (event.packetId == PacketType.Client.USE_ENTITY) hitTicks = 0

    }

}