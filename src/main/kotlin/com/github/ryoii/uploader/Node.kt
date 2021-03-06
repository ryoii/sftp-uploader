package com.github.ryoii.uploader

import java.io.File

class Node {
    var name: String = "anonymous"
    var username: String = ""
    var password: String = ""
    var host: String = ""
    var port: Int = 22
    var origin: String = ""
    var dest: String = ""
    var rename: String? = null
    var timeout: Int = 30_000
}

class Nodes {
    var nodes: List<Node> = emptyList()
}

fun Node.upload() {
    SftpConnector(this).apply {
        try {
            connect(timeout)
            upload(File(origin), dest, rename)
        } catch (e: Exception) {
            println("节点${name}连接服务器失败或密码错误")
//            e.printStackTrace()
            return
        } finally {
            disconnect()
        }
    }
}
