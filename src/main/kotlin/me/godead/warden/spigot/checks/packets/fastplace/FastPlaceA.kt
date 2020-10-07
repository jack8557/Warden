package me.godead.warden.spigot.checks.packets.fastplace

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import io.github.retrooper.packetevents.packettype.PacketType
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.extensions.isFlying
import me.godead.anticheat.users.User

@Info(name = "FastPlace", type = 'A')
class FastPlaceA : Check() {

    var i = 0

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (event.packetId == PacketType.Client.BLOCK_PLACE) {
            if (i++ > 1) flag(user)
        } else if (event.packetId.isFlying()) i = 0
    }


}