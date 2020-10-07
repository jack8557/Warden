package me.godead.warden.spigot.checks.packets.timer

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent
import me.godead.anticheat.check.Check
import me.godead.anticheat.check.Info
import me.godead.anticheat.extensions.isFlying
import me.godead.anticheat.users.User
import java.util.concurrent.TimeUnit

@Info(name = "Timer", type = 'A')
// kind of taken from verus. i will make my own when I have time.
class TimerA : Check() {

    private val checkTime = (45L).toNanos()
    private var lastFlagTicks = 0
    private var lastFlag: Long = 0
    private var lastPacket: Long = 0
    private var offset: Long = -100L

    private fun Long.fromNanos() = TimeUnit.NANOSECONDS.toMillis(this)


    private fun Long.toNanos() = TimeUnit.MILLISECONDS.toNanos(this)


    override fun onPacketReceive(event: PacketReceiveEvent, user: User) {
        if (user.player.isInsideVehicle) return
        if (!event.packetId.isFlying()) return

        val nanoTime = System.nanoTime()
        offset += (50L).toNanos() - (nanoTime - lastPacket)
        if (offset > checkTime) {
            if ((nanoTime - lastFlag).fromNanos() > 1L) flag(user)
            lastFlag = nanoTime
            lastFlagTicks = 0
            offset = 0L
        } else {
            ++lastFlagTicks
        }
        lastPacket = nanoTime
    }

}