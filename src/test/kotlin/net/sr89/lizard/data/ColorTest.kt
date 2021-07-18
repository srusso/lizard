package net.sr89.lizard.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ColorTest {
    @Test
    internal fun createColor() {
        val c = color(-0.5, 0.4, 1.7)

        assertEquals(-0.5, c.red)
        assertEquals(0.4, c.green)
        assertEquals(1.7, c.blue)
    }

    @Test
    internal fun addColors() {
        val c1 = color(0.9, 0.6, 0.75)
        val c2 = color(0.7, 0.1, 0.25)

        assertEquals(
            color(1.6, 0.7, 1.0),
            c1 + c2
        )
    }

    @Test
    internal fun subtractColors() {
        val c1 = color(0.9, 0.6, 0.75)
        val c2 = color(0.7, 0.1, 0.25)

        assertEquals(
            color(0.2, 0.5, 0.5),
            c1 - c2
        )
    }

    @Test
    internal fun multiplyColorByScalar() {
        val c = color(0.2, 0.3, 0.4)

        assertEquals(
            color(0.4, 0.6, 0.8),
            c * 2
        )
    }

    @Test
    internal fun multiplyColors() {
        val c1 = color(1, 0.2, 0.4)
        val c2 = color(0.9, 1, 0.1)

        assertEquals(
            color(0.9, 0.2, 0.04),
            c1 * c2
        )
    }
}