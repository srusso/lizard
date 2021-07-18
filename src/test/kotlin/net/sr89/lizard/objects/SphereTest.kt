package net.sr89.lizard.objects

import net.sr89.lizard.data.Matrix
import net.sr89.lizard.data.normalize
import net.sr89.lizard.data.point
import net.sr89.lizard.data.vector
import net.sr89.lizard.math.rotationZ
import net.sr89.lizard.math.scaling
import net.sr89.lizard.math.translation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

internal class SphereTest {
    @Test
    internal fun sphereDefaultTransformation() {
        val s = sphere()

        assertEquals(Matrix.identity(4), s.transform)
    }

    @Test
    internal fun updateSphereTransformation() {
        val t = translation(2, 3, 4)

        val s = sphere().thenTransform(t)

        assertEquals(t, s.transform)
    }

    @Test
    internal fun sphereNormalOnXAxis() {
        val s = sphere()
        val n = s.normalAt(point(1, 0, 0))

        assertEquals(vector(1, 0, 0), n)
    }

    @Test
    internal fun sphereNormalOnYAxis() {
        val s = sphere()
        val n = s.normalAt(point(0, 1, 0))

        assertEquals(vector(0, 1, 0), n)
    }

    @Test
    internal fun sphereNormalOnZAxis() {
        val s = sphere()
        val n = s.normalAt(point(0, 0, 1))

        assertEquals(vector(0, 0, 1), n)
    }

    @Test
    internal fun sphereNormalAtNonAxialPoint() {
        val p = sqrt(3.0) / 3.0
        val s = sphere()
        val n = s.normalAt(point(p, p, p))

        assertEquals(vector(p, p, p), n)
    }

    @Test
    internal fun sphereNormalIsNormalized() {
        val p = sqrt(3.0) / 3.0
        val s = sphere()
        val n = s.normalAt(point(p, p, p))

        assertEquals(n, n.normalize())
    }

    @Test
    internal fun normalOfTranslatedSphere() {
        val s = sphere().thenTransform(translation(0, 1, 0))
        val n = s.normalAt(point(0, 1.70711, -0.70711))

        assertEquals(vector(0, 0.70711, -0.70711), n)
    }

    @Test
    internal fun normalOfTransformedSphere() {
        val s = sphere()
            .thenTransform(rotationZ(Math.PI / 5))
            .thenTransform(scaling(1, 0.5, 1))

        val n = s.normalAt(point(0, sqrt(2.0) / 2, -sqrt(2.0) / 2))

        assertEquals(vector(0, 0.97014, -0.24254), n)
    }
}