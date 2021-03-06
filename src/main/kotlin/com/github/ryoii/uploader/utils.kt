package com.github.ryoii.uploader

import java.io.InputStream
import java.io.OutputStream

fun InputStream.transform(output: OutputStream) = this.copyTo(output)

fun upLine(n: Int) = if (n > 0) { print("\u001B[${n}A") } else Unit
fun delLine() = print("\u001B[2K")
fun saveCursor() = print("\u001B[s")
fun recoverCursor() = print("\u001B[u")
fun hideCursor() = print("\u001B[?25l")
fun showCursor() = print("\u001B[?25h")