package net.sr89.lizard.data

import org.junit.jupiter.api.Test
import kotlin.math.sqrt
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class TuplesTest {
    @Test
    internal fun pointTuple() {
        assertTrue(tuple(4.3, -4.2, 3.1, 1.0).isPoint())
        assertFalse(tuple(4.3, -4.2, 3.1, 1.0).isVector())
        assertTrue(point(4.3, -4.2, 3.1).isPoint())
        assertFalse(point(4.3, -4.2, 3.1).isVector())
    }

    @Test
    internal fun vectorTuple() {
        assertTrue(tuple(4.3, -4.2, 3.1, 0.0).isVector())
        assertFalse(tuple(4.3, -4.2, 3.1, 0.0).isPoint())
        assertTrue(vector(4.3, -4.2, 3.1).isVector())
        assertFalse(vector(4.3, -4.2, 3.1).isPoint())
    }

    @Test
    internal fun addVectorToPoint() {
        val point = tuple(3.0, -2.0, 5.0, 1.0)
        val vector = tuple(-2.0, 3.0, 1.0, 0.0)

        assertEquals(point(1.0, 1.0, 6.0), point + vector)
    }

    @Test
    internal fun addVectorToVector() {
        val v1 = tuple(3.0, -2.0, 5.0, 0.0)
        val v2 = tuple(-2.0, 3.0, 1.0, 0.0)

        assertEquals(vector(1.0, 1.0, 6.0), v1 + v2)
    }

    @Test
    internal fun subtractPoints() {
        val p1 = point(3.0, 2.0, 1.0)
        val p2 = point(5.0, 6.0, 7.0)

        val dif = p1 - p2

        assertEquals(vector(-2.0, -4.0, -6.0), dif)
    }

    @Test
    internal fun pointMinusVector() {
        val point = point(3.0, 2.0, 1.0)
        val vector = vector(5.0, 6.0, 7.0)

        val dif = point - vector

        assertEquals(point(-2.0, -4.0, -6.0), dif)
    }

    @Test
    internal fun subtractVectors() {
        val p1 = vector(3.0, 2.0, 1.0)
        val p2 = vector(5.0, 6.0, 7.0)

        val dif = p1 - p2

        assertEquals(vector(-2.0, -4.0, -6.0), dif)
    }

    @Test
    internal fun subtractVectorFromZeroVector() {
        val zero = vector(0.0, 0.0, 0.0)
        val v = vector(1.0, -2.0, 3.0)

        val dif = zero - v

        assertEquals(vector(-1.0, 2.0, -3.0), dif)
    }

    @Test
    internal fun negateTuple() {
        assertEquals(
            tuple(-1.0, 2.0, -3.0, 4.0),
            -tuple(1.0, -2.0, 3.0, -4.0)
        )
    }

    @Test
    internal fun multiplyTupleByScalar() {
        assertEquals(
            tuple(1.0, -2.0, 3.0, -4.0) * 3.5,
            tuple(3.5, -7.0, 10.5, -14.0)
        )

        assertEquals(
            3.5 * tuple(1.0, -2.0, 3.0, -4.0),
            tuple(3.5, -7.0, 10.5, -14.0)
        )
    }

    @Test
    internal fun divideTupleByScalar() {
        assertEquals(
            tuple(1.0, -2.0, 3.0, -4.0) / 2.0,
            tuple(0.5, -1.0, 1.5, -2.0)
        )
    }

    @Test
    internal fun reflectVector45Degrees() {
        val v = vector(1, -1, 0)
        val n = vector(0, 1, 0)
        val r = v.reflect(n)

        assertEquals(vector(1, 1, 0), r)
    }

    @Test
    internal fun reflectOffSlantedSurface() {
        val v = vector(0, -1, 0)
        val n = vector(sqrt(2.0) / 2, sqrt(2.0) / 2, 0)
        val r = v.reflect(n)

        assertEquals(vector(1, 0, 0), r)
    }
}