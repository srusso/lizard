package net.sr89.lizard.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import kotlin.test.assertNotEquals

class MatrixTest {

    @Test
    internal fun createFourByFourMatrix() {
        val m = Matrix.create(
            size(4, 4),
            1, 2, 3, 4,
            5.5, 6.5, 7.5, 8.5,
            9, 10, 11, 12,
            13.5, 14.5, 15.5, 16.5
        )

        assertEquals(1.0, m.get(0, 0))
        assertEquals(4.0, m.get(0, 3))
        assertEquals(5.5, m.get(1, 0))
        assertEquals(7.5, m.get(1, 2))
        assertEquals(11.0, m.get(2, 2))
        assertEquals(13.5, m.get(3, 0))
        assertEquals(15.5, m.get(3, 2))
    }

    @Test
    internal fun createTwoByTwoMatrix() {
        val m = Matrix.create(
            size(2, 2),
            -3, 5,
            1, -2
        )

        assertEquals(-3.0, m.get(0, 0))
        assertEquals(5.0, m.get(0, 1))
        assertEquals(1.0, m.get(1, 0))
        assertEquals(-2.0, m.get(1, 1))
    }

    @Test
    internal fun createThreeByThreeMatrix() {
        val m = Matrix.create(
            size(3, 3),
            -3, 5, 0,
            1, -2, 7,
            0, 1, 1
        )

        assertEquals(-3.0, m.get(0, 0))
        assertEquals(-2.0, m.get(1, 1))
        assertEquals(1.0, m.get(2, 2))
    }

    @Test
    internal fun cannotCreateMatrixIfWrongNumberOfValues() {
        try {
            Matrix.create(
                size(3, 3),
                -3, 5, 0,
                1, -2, 7,
                0, 1
            )
            fail("Expected matrix creation to fail")
        } catch (e: IllegalArgumentException) {

        }

        try {
            Matrix.create(
                size(3, 3),
                -3, 5, 0,
                1, -2, 7,
                0, 1, 0, 9
            )
            fail("Expected matrix creation to fail")
        } catch (e: IllegalArgumentException) {

        }
    }

    @Test
    internal fun matricesAreEqual() {
        val m1 = Matrix.create(
            size(3, 3),
            -3, 5, 0,
            1, -2, 7,
            0, 1, 1
        )

        val m2 = Matrix.create(
            size(3, 3),
            -3, 5, 0,
            1, -2, 7,
            0, 1, 1
        )

        assertEquals(m1, m2)
    }

    @Test
    internal fun matricesWithDifferentValuesAreNotEqual() {
        val m1 = Matrix.create(
            size(3, 3),
            -3, 5, 0,
            1, -2, 7,
            0, 1, 1
        )

        val m2 = Matrix.create(
            size(3, 3),
            -3, 5, 0,
            1, -2, 7.5,
            0, 1, 1
        )

        assertNotEquals(m1, m2)
    }

    @Test
    internal fun matricesWithDifferentSizesAreNotEqual() {
        val m1 = Matrix.create(
            size(3, 3),
            -3, 5, 0,
            1, -2, 7,
            0, 1, 1
        )

        val m2 = Matrix.create(
            size(2, 3),
            -3, 5, 0,
            1, -2, 7.5
        )

        assertNotEquals(m1, m2)
    }
}