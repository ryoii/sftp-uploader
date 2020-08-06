package com.github.ryoii.uploader

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.SftpProgressMonitor
import java.io.File
import kotlin.math.min

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
            println("文件或文件夹${origin.name}不存在")
            return
        }

        val channel: ChannelSftp = session.openChannel("sftp").apply {
            connect(node.timeout)
        } as ChannelSftp

        when {
            origin.isFile -> channel.uploadFile(origin, dest, rename)
            origin.isDirectory -> channel.uploadDir(origin, dest)
        }
    }

    private fun ChannelSftp.uploadFile(origin: File, dest: String, rename: String? = null) {
        origin.inputStream().use {
            cd(dest)
            val dst = if (rename.isNullOrBlank()) origin.name else rename
            put(
                /* src = */ it,
                /* dst = */ dst,
                /* monitor */ Monitor(10000, node.name, dst)
                    .apply { MonitorManager.register(this) }
            )
        }
    }

    private fun ChannelSftp.uploadDir(origin: File, dest: String) {
        kotlin.runCatching { mkdir(dest) }

        val files = origin.listFiles() ?: return
        for (file in files) {
            when {
                file.isFile -> uploadFile(file, dest, null)
                file.isDirectory -> {
                    uploadDir(file, file.name)
                    cd("..")
                }
            }
        }
    }
}

class Monitor(internal val size: Long, val node: String, private val fileName: String) : SftpProgressMonitor {

    var current: Long = 0
    var start: Long = 0
    var isFinish: Boolean = false
        private set

    val fileDesc: String
        get() = if (fileName.length >= 8) fileName.substring(0..5) + "..." else fileName

    override fun count(count: Long): Boolean {
        current = min(current + count, size)
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