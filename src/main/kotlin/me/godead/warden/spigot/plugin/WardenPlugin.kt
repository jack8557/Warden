package me.godead.warden.spigot.plugin

import me.godead.anticheat.plugin.AntiCheatPlugin

class WardenPlugin : AntiCheatPlugin() {

    override fun onStart() = Warden.init(this)

    override fun onStartFinish() = Warden.lateInit()

    override fun onStop() = Warden.terminate(this)

}