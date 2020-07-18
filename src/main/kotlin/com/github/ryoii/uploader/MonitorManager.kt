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
            delLine()

            if (it.isFinish) {
                println("\r[${it.node.name}] ${100}% ${progressString(100)} 已完成")
            } else {
                val percent = (100 * it.current / it.size).toInt()
                val speed = it.current / (System.currentTimeMillis() - it.start)
                val left = ((it.size - it.current) shr 10) / speed
                println("\r[${it.node.name}] ${percent}% ${progressString(percent)} ${speedStr(speed)} ${timeStr(left)}")
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

    private val speedArray = arrayOf("KB/s", "MB/s", "GB/s", "TB/s")
    private fun speedStr(speed: Long): String {
        var count = 0
        var s = speed.toDouble()
        while (s >= 1024.0) {
            count++
            s /= 1024.0
        }
        return String.format("%.2f %s", s, speedArray[count])
    }

    private fun timeStr(time: Long) = (time / 60).toString() + "m" + (time % 60).toShort() + "s"
}