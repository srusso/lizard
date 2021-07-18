package net.sr89.lizard.data

import net.sr89.lizard.math.*
import net.sr89.lizard.utils.eequals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class MatrixOperationsTest {

    @Test
    internal fun multiplyMatrices() {
        val a = Matrix.create(
            size(4, 4),
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 8, 7, 6,
            5, 4, 3, 2
        )

        val b = Matrix.create(
            size(4, 4),
            -2, 1, 2, 3,
            3, 2, 1, -1,
            4, 3, 6, 5,
            1, 2, 7, 8
        )

        val expectedMultiplicationResult =
            Matrix.create(
                size(4, 4),
                20, 22, 50, 48,
                44, 54, 114, 108,
                40, 58, 110, 102,
                16, 26, 46, 42
            )

        assertEquals(
            expectedMultiplicationResult,
            a * b
        )
    }

    @Test
    internal fun multiplyByIdentityMatrix() {
        val a = Matrix.create(
            size(4, 4),
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 8, 7, 6,
            5, 4, 3, 2
        )

        assertEquals(
            a,
            a * Matrix.identity(4)
        )
    }

    @Test
    internal fun identityMatrixTimesTuple() {
        val t = tuple(1, 2, 3, 4)

        assertEquals(
            t,
            Matrix.identity(4) * t
        )
    }

    @Test
    internal fun multiplyMatrixByTuple() {
        val a = Matrix.create(
            size(4, 4),
            1, 2, 3, 4,
            2, 4, 4, 2,
            8, 6, 4, 1,
            0, 0, 0, 1
        )

        val b = tuple(1, 2, 3, 1)

        assertEquals(tuple(18, 24, 33, 1), a * b)
    }

    @Test
    internal fun transposeSquareMatrix() {
        val a = Matrix.create(
            size(4, 4),
            0, 9, 3, 0,
            9, 8, 0, 8,
            1, 8, 5, 3,
            0, 0, 5, 8
        )

        val expectedTransposed = Matrix.create(
            size(4, 4),
            0, 9, 1, 0,
            9, 8, 8, 0,
            3, 0, 5, 5,
            0, 8, 3, 8
        )

        assertEquals(expectedTransposed, a.transpose())
    }

    @Test
    internal fun transposeRectangularMatrix() {
        val a = Matrix.create(
            size(2, 4),
            0, 9, 3, 0,
            9, 8, 0, 8
        )

        val expectedTransposed = Matrix.create(
            size(4, 2),
            0, 9,
            9, 8,
            3, 0,
            0, 8
        )

        assertEquals(expectedTransposed, a.transpose())
    }

    @Test
    internal fun transposeIdentityMatrix() {
        val a = Matrix.identity(4)

        assertEquals(a, a.transpose())
    }

    @Test
    internal fun twoByTwoMatrixDeterminant() {
        val a = Matrix.create(
            size(2, 2),
            1, 5,
            -3, 2
        )

        assertEquals(17.0, a.determinant())
    }

    @Test
    internal fun subMatrixThreeByThree() {
        val a = Matrix.create(
            size(3, 3),
            1, 5, 0,
            -3, 2, 7,
            0, 6, 3
        )

        val submatrix = Matrix.create(
            size(2, 2),
            -3, 2,
            0, 6
        )

        assertEquals(submatrix, a.subMatrix(0, 2))
    }

    @Test
    internal fun subMatrixFourByFour() {
        val a = Matrix.create(
            size(4, 4),
            -6, 1, 1, 6,
            -8, 5, 8, 6,
            -1, 0, 8, 2,
            -7, 1, -1, 1
        )

        val submatrix = Matrix.create(
            size(3, 3),
            -6, 1, 6,
            -8, 8, 6,
            -7, -1, 1
        )

        assertEquals(submatrix, a.subMatrix(2, 1))
    }

    @Test
    internal fun minorOfMatrix() {
        val a = Matrix.create(
            size(3, 3),
            3, 5, 0,
            2, -1, -7,
            6, -1, 5
        )

        val b = a.subMatrix(1, 0)

        assertEquals(b.determinant(), 25.0)
        assertEquals(a.minor(1, 0), 25.0)
    }

    @Test
    internal fun cofactorOfMatrix() {
        val a = Matrix.create(
            size(3, 3),
            3, 5, 0,
            2, -1, -7,
            6, -1, 5
        )

        assertEquals(a.minor(0, 0), -12.0)
        assertEquals(a.cofactor(0, 0), -12.0)
        assertEquals(a.minor(1, 0), 25.0)
        assertEquals(a.cofactor(1, 0), -25.0)
    }

    @Test
    internal fun threeByThreeMatrixDeterminant() {
        val a = Matrix.create(
            size(3, 3),
            1, 2, 6,
            -5, 8, -4,
            2, 6, 4
        )

        assertEquals(a.cofactor(0, 0), 56.0)
        assertEquals(a.cofactor(0, 1), 12.0)
        assertEquals(a.cofactor(0, 2), -46.0)
        assertEquals(a.determinant(), -196.0)
    }

    @Test
    internal fun fourByFourMatrixDeterminant() {
        val a = Matrix.create(
            size(4, 4),
            -2, -8, 3, 5,
            -3, 1, 7, 3,
            1, 2, -9, 6,
            -6, 7, 7, -9
        )

        assertEquals(a.cofactor(0, 0), 690.0)
        assertEquals(a.cofactor(0, 1), 447.0)
        assertEquals(a.cofactor(0, 2), 210.0)
        assertEquals(a.cofactor(0, 3), 51.0)
        assertEquals(a.determinant(), -4071.0)
    }

    @Test
    internal fun matrixIsInvertible() {
        val a = Matrix.create(
            size(4, 4),
            6, 4, 4, 4,
            5, 5, 7, 6,
            4, -9, 3, -7,
            9, 1, 7, -6
        )

        assertTrue(a.invertible())
    }

    @Test
    internal fun matrixIsNotInvertible() {
        val a = Matrix.create(
            size(4, 4),
            -4, 2, -2, -3,
            9, 6, 2, 6,
            0, -5, 1, -5,
            0, 0, 0, 0
        )

        assertFalse(a.invertible())
    }

    @Test
    internal fun inverseMatrix() {
        val a = Matrix.create(
            size(4, 4),
            -5, 2, 6, -8,
            1, -5, 1, 8,
            7, 7, -6, -7,
            1, -3, 7, 4
        )

        val b = a.inverse()
        val expectedInverse = Matrix.create(
            size(4, 4),
            0.21805, 0.45113, 0.24060, -0.04511,
            -0.80827, -1.45677, -0.44361, 0.52068,
            -0.07895, -0.22368, -0.05263, 0.19737,
            -0.52256, -0.81391, -0.30075, 0.30639
        )

        assertEquals(532.0, a.determinant())
        assertEquals(-160.0, a.cofactor(2, 3))
        assertTrue(b.get(3, 2).eequals(-160.0/532.0))
        assertEquals(105.0, a.cofactor(3, 2))
        assertTrue(b.get(2, 3).eequals(105.0/532.0))
        assertEquals(expectedInverse, b)
    }

    @Test
    internal fun inverseAnotherMatrix() {
        val a = Matrix.create(
            size(4, 4),
            8, -5, 9, 2,
            7, 5, 6, 1,
            -6, 0, 9, 6,
            -3, 0, -9, -4
        )

        val expectedInverse = Matrix.create(
            size(4, 4),
            -0.15385, -0.15385, -0.28205, -0.53846,
            -0.07692, 0.12308, 0.02564, 0.03077,
            0.35897, 0.35897, 0.43590, 0.92308,
            -0.69231, -0.69231, -0.76923, -1.92308
        )

        assertEquals(expectedInverse, a.inverse())
    }

    @Test
    internal fun inverseYetAnotherMatrix() {
        val a = Matrix.create(
            size(4, 4),
            9, 3, 0, 9,
            -5, -2, -6, -3,
            -4, 9, 6, 4,
            -7, 6, 6, 2
        )

        val expectedInverse = Matrix.create(
            size(4, 4),
            -0.04074, -0.07778, 0.14444, -0.22222,
            -0.07778, 0.03333, 0.36667, -0.33333,
            -0.02901, -0.14630, -0.10926, 0.12963,
            0.17778, 0.06667, -0.26667, 0.33333
        )

        assertEquals(expectedInverse, a.inverse())
    }

    @Test
    internal fun multiplyingByInverseGivesBackOriginalMatrix() {
        val a = Matrix.create(
            size(4, 4),
            3, -9, 7, 3,
            3, -8, 2, -9,
            -4, 4, 4, 1,
            -6, 5, -1, 1
        )

        val b = Matrix.create(
            size(4, 4),
            8, 2, 2, 2,
            3, -1, 7, 0,
            7, 0, 5, 4,
            6, -2, 0, 5
        )

        val c = a * b

        assertEquals(a, c * b.inverse())
    }
}