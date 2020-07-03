package com.github.ryoii.uploader

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.SftpProgressMonitor
import java.io.File

class SftpConnector(private val node: Node) {

    private val session = JSch().run {
        getSession(node.username, node.host, node.port).apply {
            setPassword(node.password)
            setConfig("StrictHostKeyChecking", "no")
        }
    }

    fun connect(timeout: Int) = session.connect(timeout)

    fun disconnect() {
        if (session.isConnected) session.disconnect()
    }

    fun upload(origin: File, dest: String, rename: String? = null) {
        if (!origin.exists()) return

        val channel: ChannelSftp = session.openChannel("sftp").apply {
            connect()
        } as ChannelSftp

        origin.inputStream().use {
            channel.cd(dest)
            channel.put(it, rename ?: origin.name, Monitor(origin.length()))
        }
    }
}

class Monitor(private val size: Long) : SftpProgressMonitor {

    var current: Long = 0
    var start: Long = 0

    override fun count(count: Long): Boolean {
        current += count
        val percent = (100 * current / size).toInt()
        print("${percent}% ${progressString(percent)} ${current / (System.currentTimeMillis() - start) } KB/s\r")
        return true
    }

    override fun end() { println("\n") }

    override fun init(op: Int, src: String?, dest: String?, max: Long) {
        start = System.currentTimeMillis()
    }

    private fun progressString(percent: Int, size: Int = 50): String {
        val show = (percent * size / 100.0).toInt()
        return buildString {
            append("|")
            repeat(show) { append("=") }
            append(">")
            repeat(size - show) { append(" ") }
            append("|")
        }
    }
}