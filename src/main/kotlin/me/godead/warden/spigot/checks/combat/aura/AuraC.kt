package me.godead.warden.spigot.checks.combat.aura

import io.github.retrooper.packetevents.enums.ClientVersion
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import io.github.retrooper.packetevents.packettype.PacketType
import io.github.retrooper.packetevents.packetwrappers.`in`.useentity.WrappedPacketInUseEntity
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.users.User

@Info(name = "KillAura", type = 'C')
class AuraC : Check() {

    var swung = false

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (!user.clientVersion.isLowerThan(ClientVersion.v_1_9) || user.clientVersion.equals(ClientVersion.UNKNOWN)) return
        when (event.packetId) {
            PacketType.Client.ARM_ANIMATION -> swung = true
            PacketType.Client.USE_ENTITY -> {
                if (WrappedPacketInUseEntity(event.nmsPacket).action == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {
                    if (!swung) flag(user)
                }
            }
            else -> swung = false
        }
    }
}