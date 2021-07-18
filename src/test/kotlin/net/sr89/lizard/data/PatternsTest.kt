package net.sr89.lizard.data

import net.sr89.lizard.math.lighting
import net.sr89.lizard.math.scaling
import net.sr89.lizard.math.stripeAt
import net.sr89.lizard.math.translation
import net.sr89.lizard.objects.sphere
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PatternsTest {
    @Test
    internal fun `stripe pattern`() {
        val p = stripePattern(white, black)

        // changing y
        assertEquals(white, p.colorAt(point(0, 0, 0)))
        assertEquals(white, p.colorAt(point(0, 1, 0)))
        assertEquals(white, p.colorAt(point(0, 2, 0)))

        // changing z
        assertEquals(white, p.colorAt(point(0, 0, 0)))
        assertEquals(white, p.colorAt(point(0, 0, 1)))
        assertEquals(white, p.colorAt(point(0, 0, 2)))

        // changing x
        assertEquals(white, p.colorAt(point(0, 0, 0)))
        assertEquals(white, p.colorAt(point(0.9, 0, 0)))
        assertEquals(black, p.colorAt(point(1, 0, 0)))
        assertEquals(black, p.colorAt(point(-0.1, 0, 0)))
        assertEquals(black, p.colorAt(point(-1, 0, 0)))
        assertEquals(white, p.colorAt(point(-1.1, 0, 0)))
    }

    @Test
    internal fun `lighting with pattern applied`() {
        val m = material().copy(
            pattern = stripePattern(white, black),
            ambient = 1.0,
            diffuse = 0.0,
            specular = 0.0
        )
        val eyev = vector(0, 0, -1)
        val normalv = vector(0, 0, -1)
        val light = pointLight(point(0, 0, -10), white)

        val c1 = lighting(m, sphere(), light, point(0.9, 0, 0), eyev, normalv)
        val c2 = lighting(m, sphere(), light, point(1.1, 0, 0), eyev, normalv)

        assertEquals(white, c1)
        assertEquals(black, c2)
    }

    @Test
    internal fun `stripes with an object transformation`() {
        val s = sphere().thenTransform(scaling(2, 2, 2))
        val p = stripePattern(white, black)
        val c = stripeAt(p, s, point(1.5, 0, 0))

        assertEquals(white, c)
    }

    @Test
    internal fun `stripes with a pattern transformation`() {
        val s = sphere()
        val p = stripePattern(white, black)
            .copy(transform = scaling(2, 2, 2))
        val c = stripeAt(p, s, point(1.5, 0, 0))

        assertEquals(white, c)
    }

    @Test
    internal fun `stripes with object and pattern transformation`() {
        val s = sphere().thenTransform(scaling(2, 2, 2))
        val p = stripePattern(white, black)
            .copy(transform = translation(0.5, 0, 0))
        val c = stripeAt(p, s, point(2.5, 0, 0))

        assertEquals(white, c)
    }
}