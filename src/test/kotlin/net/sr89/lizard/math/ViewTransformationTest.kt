package net.sr89.lizard.math

import net.sr89.lizard.data.Matrix
import net.sr89.lizard.data.point
import net.sr89.lizard.data.size
import net.sr89.lizard.data.vector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ViewTransformationTest {
    @Test
    internal fun `view transformation matrix for the default orientation`() {
        assertEquals(
            Matrix.identity(4),
            viewTransform(
                point(0, 0, 0),
                point(0, 0, -1),
                vector(0, 1, 0)
            )
        )
    }

    @Test
    internal fun `view transformation looking in the positive z direction`() {
        assertEquals(
            scaling(-1, 1, -1),
            viewTransform(
                point(0, 0, 0),
                point(0, 0, 1),
                vector(0, 1, 0)
            )
        )
    }

    @Test
    internal fun `view transformation moves the world`() {
        assertEquals(
            translation(0, 0, -8),
            viewTransform(
                point(0, 0, 8),
                point(0, 0, 0),
                vector(0, 1, 0)
            )
        )
    }

    @Test
    internal fun `arbitrary view transformation`() {
        assertEquals(
            Matrix.create(
                size(4, 4),
                -0.50709, 0.50709, 0.67612, -2.36643,
                0.76772, 0.60609, 0.12122, -2.82843,
                -0.35857, 0.59761, -0.71714, 0,
                0, 0, 0, 1,
            ),
            viewTransform(
                point(1, 3, 2),
                point(4, -2, 8),
                vector(1, 1, 0)
            )
        )
    }
}