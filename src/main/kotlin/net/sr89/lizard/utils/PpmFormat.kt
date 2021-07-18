package net.sr89.lizard.utils

import net.sr89.lizard.data.Canvas
import kotlin.math.ceil

private const val ppmFlavor = "P3"
private const val maxColorIntensity = 255

private fun Int.toColorBounds(): Int = when {
    this < 0 -> 0
    this > maxColorIntensity -> maxColorIntensity
    else -> this
}

private fun Canvas.pixelRows(y: Int): List<String> {
    val columns = 0 until width

    val colorPoints = columns
        .map { x -> pixelAt(x, y) }
        .flatMap { pixel ->
            listOf(
                pixel.red * maxColorIntensity,
                pixel.green * maxColorIntensity,
                pixel.blue * maxColorIntensity,
            )
        }
        .map { ceil(it) }
        .map { it.toInt() }
        .map { it.toColorBounds() }

    var currentLen = 0
    var currentRow = StringBuilder()

    val rows = mutableListOf<String>()

    for (point in colorPoints) {
        val strPoint = point.toString()
        val additionalLength = strPoint.length + 1
        val newLength = currentLen + additionalLength

        if (newLength <= 70) {
            if (currentRow.isNotEmpty()) {
                currentRow.append(' ')
            }
            currentRow.append(strPoint)
            currentLen = newLength
        } else {
            rows.add(currentRow.toString())
            currentRow = StringBuilder(strPoint)
            currentLen = additionalLength
        }
    }

    rows.add(currentRow.toString())

    return rows
}

fun Canvas.toPPMFormat(): String {
    val ppm = StringBuilder()

    ppm.append(
        """
            $ppmFlavor
            $width $height
            $maxColorIntensity
        """.trimIndent()
    )

    val rows = 0 until height

    rows.flatMap { pixelRows(it) }
        .forEach { ppm.appendLine().append(it) }

    return ppm.appendLine().toString()
}
