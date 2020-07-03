package com.github.ryoii.uploader

import java.io.File

class Node {
    var username: String = ""
    var password: String = ""
    var host: String = ""
    var port: Int = 22
    var origin: String = ""
    var dest: String = ""
    var rename: String? = null
}

class Nodes {
    var nodes: List<Node> = emptyList()
}

fun Node.upload() {
    SftpConnector(this).apply {
        try {
            connect(1_000)
            upload(File(origin), dest, rename)
            disconnect()
        } catch (e: Exception) {
            println("连接服务器失败或密码错误")
            return
        }
    }
}