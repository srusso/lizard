package net.sr89.lizard.data

import org.junit.jupiter.api.Test
import kotlin.math.sqrt
import kotlin.test.assertEquals

internal class VectorOperationsTest {
    @Test
    internal fun magnitudeOfUnitVector() {
        assertEquals(vector(1, 0, 0).magnitude(), 1.0)
        assertEquals(vector(0, 1, 0).magnitude(), 1.0)
        assertEquals(vector(0, 0, 1).magnitude(), 1.0)
    }

    @Test
    internal fun magnitudeOfVectors() {
        assertEquals(vector(1, 2, 3).magnitude(), sqrt(14.0))
        assertEquals(vector(-1, -2, -3).magnitude(), sqrt(14.0))
    }

    @Test
    internal fun normalizeVectors() {
        assertEquals(
            vector(4, 0, 0).normalize(),
            vector(1, 0, 0)
        )

        val magnitude = sqrt(14.0)
        assertEquals(
            vector(1, 2, 3).normalize(),
            vector(1 / magnitude, 2 / magnitude, 3 / magnitude)
        )
    }

    @Test
    internal fun magnitudeOfNormalizedVectorIsOne() {
        assertEquals(
            vector(4, 0, 0).normalize().magnitude(),
            1.0
        )
    }

    @Test
    internal fun dotProduct() {
        assertEquals(
            vector(1, 2, 3) * vector(2, 3, 4),
            20.0
        )
    }

    @Test
    internal fun crossProduct() {
        val a = vector(1, 2, 3)
        val b = vector(2, 3, 4)

        assertEquals(vector(-1, 2, -1), a.cross(b))
        assertEquals(vector(1, -2, 1), b.cross(a))
    }
}