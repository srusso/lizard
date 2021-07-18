package net.sr89.lizard.data

import net.sr89.lizard.math.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt
import kotlin.test.asserter

class MatrixTransformationsTest {

    @Test
    internal fun translationMovesPoint() {
        val transformation = translation(5, -3, 2)
        val p = point(-3, 4, 5)

        assertEquals(point(2, 1, 7), transformation * p)
    }

    @Test
    internal fun inverseOfTranslationMovesPointInOppositeDirection() {
        val transformation = translation(5, -3, 2).inverse()
        val p = point(-3, 4, 5)

        assertEquals(point(-8, 7, 3), transformation * p)
    }

    @Test
    internal fun translationDoesNotAffectVectors() {
        val transformation = translation(5, -3, 2)
        val v = vector(-3, 4, 5)

        assertEquals(v, transformation * v)
    }

    @Test
    internal fun scalePoint() {
        val transformation = scaling(2, 3, 4)
        val p = point(-4, 6, 8)

        assertEquals(point(-8, 18, 32), transformation * p)
    }

    @Test
    internal fun scaleVector() {
        val transformation = scaling(2, 3, 4)
        val v = vector(-4, 6, 8)

        assertEquals(vector(-8, 18, 32), transformation * v)
    }

    @Test
    internal fun scaleVectorByInverse() {
        val transformation = scaling(2, 3, 4).inverse()
        val v = vector(-4, 6, 8)

        assertEquals(vector(-2, 2, 2), transformation * v)
    }

    @Test
    internal fun reflectPoint() {
        val transformation = scaling(-1, 1, 1)
        val p = point(2, 3, 4)

        assertEquals(point(-2, 3, 4), transformation * p)
    }

    @Test
    internal fun rotateAroundXAxis() {
        val p = point(0, 1, 0)
        val halfQuarterRotation = rotationX(Math.PI / 4)
        val quarterRotation = rotationX(Math.PI / 2)

        assertEquals(point(0, sqrt(2.0) / 2, sqrt(2.0) / 2), halfQuarterRotation * p)
        assertEquals(point(0, 0, 1), quarterRotation * p)
    }

    @Test
    internal fun inverseXRotation() {
        val p = point(0, 1, 0)
        val halfQuarterRotation = rotationX(Math.PI / 4).inverse()

        assertEquals(point(0, sqrt(2.0) / 2, -sqrt(2.0) / 2), halfQuarterRotation * p)
    }

    @Test
    internal fun rotateAroundYAxis() {
        val p = point(0, 0, 1)
        val halfQuarterRotation = rotationY(Math.PI / 4)
        val quarterRotation = rotationY(Math.PI / 2)

        assertEquals(point(sqrt(2.0) / 2, 0, sqrt(2.0) / 2), halfQuarterRotation * p)
        assertEquals(point(1, 0, 0), quarterRotation * p)
    }

    @Test
    internal fun rotateAroundZAxis() {
        val p = point(0, 1, 0)
        val halfQuarterRotation = rotationZ(Math.PI / 4)
        val quarterRotation = rotationZ(Math.PI / 2)

        assertEquals(point(-sqrt(2.0) / 2, sqrt(2.0) / 2, 0), halfQuarterRotation * p)
        assertEquals(point(-1, 0, 0), quarterRotation * p)
    }

    @Test
    internal fun shearingMovesXProportionallyToY() {
        val transformation = shearing(1, 0, 0, 0, 0, 0)
        val p = point(2, 3, 4)

        assertEquals(point(5, 3, 4), transformation * p)
    }

    @Test
    internal fun shearingMovesXProportionallyToZ() {
        val transformation = shearing(0, 1, 0, 0, 0, 0)
        val p = point(2, 3, 4)

        assertEquals(point(6, 3, 4), transformation * p)
    }

    @Test
    internal fun shearingMovesYProportionallyToX() {
        val transformation = shearing(0, 0, 1, 0, 0, 0)
        val p = point(2, 3, 4)

        assertEquals(point(2, 5, 4), transformation * p)
    }

    @Test
    internal fun shearingMovesYProportionallyToZ() {
        val transformation = shearing(0, 0, 0, 1, 0, 0)
        val p = point(2, 3, 4)

        assertEquals(point(2, 7, 4), transformation * p)
    }

    @Test
    internal fun shearingMovesZProportionallyToX() {
        val transformation = shearing(0, 0, 0, 0, 1, 0)
        val p = point(2, 3, 4)

        assertEquals(point(2, 3, 6), transformation * p)
    }

    @Test
    internal fun shearingMovesZProportionallyToY() {
        val transformation = shearing(0, 0, 0, 0, 0, 1)
        val p = point(2, 3, 4)

        assertEquals(point(2, 3, 7), transformation * p)
    }

    @Test
    internal fun transformationSequence() {
        val p = point(1, 0, 1)
        val a = rotationX(Math.PI / 2.0)
        val b = scaling(5, 5, 5)
        val c = translation(10, 5, 7)

        val p2 = a * p

        assertEquals(point(1, -1, 0), p2)

        val p3 = b * p2

        assertEquals(point(5, -5, 0), p3)

        val p4 = c * p3

        assertEquals(point(15, 0, 7), p4)
    }

    @Test
    internal fun chainTransformationsMustBeAppliedInReverse() {
        val p = point(1, 0, 1)
        val a = rotationX(Math.PI / 2.0)
        val b = scaling(5, 5, 5)
        val c = translation(10, 5, 7)

        val t = c * b * a

        assertEquals(point(15, 0, 7), t * p)
    }

    @Test
    internal fun fluentlyChainTransformations() {
        val p = point(1, 0, 1)
        val newPoint = rotationX(Math.PI / 2.0)
            .then(scaling(5, 5, 5))
            .then(translation(10, 5, 7))
            .applyTo(p)

        assertEquals(point(15, 0, 7), newPoint)
    }

    @Test
    internal fun rayTranslation() {
        val ray = ray(point(1, 2, 3), vector(0, 1, 0))
        val translation = translation(3, 4, 5)
        val ray2 = ray.transform(translation)

        assertEquals(point(4, 6, 8), ray2.origin)
        assertEquals(vector(0, 1, 0), ray2.direction)
    }

    @Test
    internal fun rayScaling() {
        val ray = ray(point(1, 2, 3), vector(0, 1, 0))
        val translation = scaling(2, 3, 4)
        val ray2 = ray.transform(translation)

        assertEquals(point(2, 6, 12), ray2.origin)
        assertEquals(vector(0, 3, 0), ray2.direction)
    }
}