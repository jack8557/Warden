package me.godead.warden.spigot.checks.combat.aura

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import io.github.retrooper.packetevents.packettype.PacketType
import io.github.retrooper.packetevents.packetwrappers.`in`.useentity.WrappedPacketInUseEntity
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.users.User

@Info(name = "KillAura", type = 'D')
class AuraD : Check() {

    var hits = 0

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        when (event.packetId) {
            PacketType.Client.ARM_ANIMATION -> hits = 0
            PacketType.Client.USE_ENTITY -> {
                if (WrappedPacketInUseEntity(event.nmsPacket).action == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {
                    if (hits++ > 2) flag(user)
                }
            }
        }
    }
}