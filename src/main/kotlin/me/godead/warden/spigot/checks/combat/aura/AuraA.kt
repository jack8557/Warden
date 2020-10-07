package me.godead.warden.spigot.checks.combat.aura

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import io.github.retrooper.packetevents.packettype.PacketType
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.extensions.isFlying
import me.godead.anticheat.users.User

@Info (name = "KillAura", type = 'A')
class AuraA : Check() {

    var lastFlight = 0L

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (event.packetId.isFlying())

            lastFlight = System.currentTimeMillis()

        else if (event.packetId == PacketType.Client.USE_ENTITY) {

            val delta = System.currentTimeMillis() - lastFlight

            if (delta < 5) {
                if (preVL++ > 5) {
                    flag(user)
                }

            } else preVL = 0

        }
    }

}