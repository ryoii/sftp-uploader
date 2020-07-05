package com.github.ryoii.uploader

object MonitorManager {

    private val monitors: MutableList<Monitor> = mutableListOf()
    private var lineCount = 0

    fun register(monitor: Monitor) = monitors.add(monitor)

    @Synchronized
    fun refresh() {
        upLine(lineCount)
        lineCount = 0

        monitors.forEach {

            if (it.isFinish) {
                println("\r[${it.node.name}] ${100}% ${progressString(100)} 已完成    ")
            } else {
                val percent = (100 * it.current / it.size).toInt()
                println("\r[${it.node.name}] ${percent}% ${progressString(percent)} ${it.current / (System.currentTimeMillis() - it.start) } KB/s    ")
            }

            lineCount++
        }
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