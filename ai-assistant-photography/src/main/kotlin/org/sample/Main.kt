package org.sample

import kotlin.math.ceil
import kotlin.math.log2

fun ev(fnum: Double, time: Double): Int {
    val bl = (fnum * fnum) / time
    val ev = log2(bl)
    return ceil(ev).toInt()
}

fun main() {
    println("Enter the sample time")
    val input = readln()
    val sampleTime = if(input == "") 1 / 15.0 else input.toDouble()
    println(ev(4.0, sampleTime))
}