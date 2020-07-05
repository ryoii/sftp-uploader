package com.github.ryoii.uploader

import java.io.InputStream
import java.io.OutputStream

fun InputStream.transform(output: OutputStream) = this.copyTo(output)

fun upLine(n: Int) = if (n > 0) { print("\u001B[${n}F") } else Unit
fun delLine() = println("\u001B[2F")