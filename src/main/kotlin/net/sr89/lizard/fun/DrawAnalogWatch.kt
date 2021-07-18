package net.sr89.lizard.`fun`

import net.sr89.lizard.data.canvas
import net.sr89.lizard.data.point
import net.sr89.lizard.data.redColor
import net.sr89.lizard.data.size
import net.sr89.lizard.math.*
import net.sr89.lizard.utils.toPPMFormat
import net.sr89.lizard.utils.writeTo

fun main() {
    val canvas = canvas(500, 500)

    val midday = point(0, 1, 0)

    val hours = (0 until 12).map { hour ->
        rotationZ((Math.PI / 6.0) * hour)
            .then(scaling(180, 180, 0))
            .then(translation(canvas.width / 2, canvas.height / 2, 0))
            .applyTo(midday)
    }

    hours.forEach{ hourCenter ->
        for(i in -2 until 3) {
            for(j in -2 until 3) {
                canvas.writePixel(
                    hourCenter.x.toInt() + i,
                    canvas.height - hourCenter.y.toInt() + j,
                    redColor
                )
            }
        }
    }

    canvas.toPPMFormat().writeTo("./watch.ppm")
}