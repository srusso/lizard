package net.sr89.lizard.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LightTest {
    @Test
    internal fun pointLightCreation() {
        val intensity = color(1, 1, 1)
        val position = point(0, 0, 0)
        val light = pointLight(position, intensity)

        assertEquals(position, light.position)
        assertEquals(intensity, light.intensity)
    }
}