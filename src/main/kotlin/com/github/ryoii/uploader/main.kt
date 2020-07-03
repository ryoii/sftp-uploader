package com.github.ryoii.uploader

import org.yaml.snakeyaml.Yaml
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val nodes = getNodes(args)

    nodes.nodes.forEach {
        it.upload()
    }
}

private fun getNodes(args: Array<String>): Nodes {
    val i = args.indexOfFirst { it == "-t" }
    if (i < 0 || i + 1 == args.size) {
        println("没有指定配置文件路径")
        exitProcess(0)
    }

    val file = File(args[i + 1])
    if (!file.exists()) {
        println("配置文件不存在")
        exitProcess(0)
    }

    return Yaml().loadAs(file.reader(), Nodes::class.java)
}
