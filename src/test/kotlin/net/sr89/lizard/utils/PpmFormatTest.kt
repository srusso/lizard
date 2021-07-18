package net.sr89.lizard.utils

import net.sr89.lizard.data.canvas
import net.sr89.lizard.data.color
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PpmFormatTest {
    @Test
    internal fun canvasToPpm() {
        val c = canvas(5, 3)

        c.writePixel(0, 0, color(1.5, 0, 0))
        c.writePixel(2, 1, color(0, 0.5, 0))
        c.writePixel(4, 2, color(-0.5, 0, 1))

        val expectedPpm = """
            P3
            5 3
            255
            255 0 0 0 0 0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 128 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0 0 0 0 0 255
            
        """.trimIndent()

        assertEquals(expectedPpm, c.toPPMFormat())
    }

    @Test
    internal fun longLinesAreWrappedTo70Chars() {
        val c = canvas(10, 2)
        val color = color(1, 0.8, 0.6)

        for (x in 0 until 10) {
            for(y in 0 until 2) {
                c.writePixel(x, y, color)
            }
        }

        val expectedPpm = """
            P3
            10 2
            255
            255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204
            153 255 204 153 255 204 153 255 204 153 255 204 153
            255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204
            153 255 204 153 255 204 153 255 204 153 255 204 153
            
        """.trimIndent()

        assertEquals(expectedPpm, c.toPPMFormat())
    }
}