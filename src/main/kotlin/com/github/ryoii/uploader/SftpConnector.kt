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
        if (!origin.exists()) {
            println("文件${origin.name}不存在")
            return
        }

        val channel: ChannelSftp = session.openChannel("sftp").apply {
            connect()
        } as ChannelSftp

        origin.inputStream().use {
            channel.cd(dest)
            channel.put(
                it, if (rename.isNullOrBlank()) origin.name else rename,
                Monitor(origin.length(), node).apply { MonitorManager.register(this) }
            )
        }
    }
}

class Monitor(internal val size: Long, val node: Node) : SftpProgressMonitor {

    var current: Long = 0
    var start: Long = 0
    var isFinish: Boolean = false
        private set

    override fun count(count: Long): Boolean {
        current += count
        MonitorManager.refresh()
        return true
    }

    override fun end() {
        isFinish = true
        MonitorManager.refresh()
    }

    override fun init(op: Int, src: String?, dest: String?, max: Long) {
        start = System.currentTimeMillis()
    }
}