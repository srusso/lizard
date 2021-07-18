package net.sr89.lizard.data

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CanvasTest {
    private val blackPixel = pixel(color(0, 0, 0))

    @Test
    internal fun canvasIsBlackByDefault() {
        val c = canvas(10, 20)

        assertEquals(10, c.width)
        assertEquals(20, c.height)

        for (i in 0..9) {
            for (j in 0..19) {
                assertEquals(blackPixel, c.pixelAt(i, j))
            }
        }
    }

    @Test
    internal fun createCanvas() {
        val c = canvas(10, 20)
        val red = color(1, 0, 0)

        c.writePixel(2, 3, red)

        assertEquals(pixel(red), c.pixelAt(2, 3))

        for (i in 0..9) {
            for (j in 0..19) {
                if (i != 2 && j != 3) {
                    assertEquals(blackPixel, c.pixelAt(i, j))
                }
            }
        }
    }
}