package me.godead.warden.spigot.checks.movement.flight

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import io.github.retrooper.packetevents.packettype.PacketType
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.users.User

@Info(name = "Flight", type = 'A')
class FlightA : Check() {

    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (!user.player.allowFlight && event.packetId == PacketType.Client.ABILITIES) flag(user)
    }

}