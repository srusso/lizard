package net.sr89.lizard

import net.sr89.lizard.data.color
import net.sr89.lizard.data.get
import net.sr89.lizard.data.material
import net.sr89.lizard.data.point
import net.sr89.lizard.data.pointLight
import net.sr89.lizard.data.ray
import net.sr89.lizard.data.vector
import net.sr89.lizard.math.intersect
import net.sr89.lizard.math.scaling
import net.sr89.lizard.objects.sphere
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class WorldTest {
    @Test
    internal fun `empty world`() {
        val w = emptyWorld()

        assertFalse(w.hasObjects())
        assertFalse(w.hasLightSource())
    }

    @Test
    internal fun `default world`() {
        val light = pointLight(point(-10, 10, -10), color(1, 1, 1))
        val s1 = sphere()
            .withMaterial(
                material()
                    .copy(
                        surfaceColor = color(0.8, 1.0, 0.6),
                        diffuse = 0.7,
                        specular = 0.2
                    )
            )

        val s2 = sphere()
            .thenTransform(scaling(0.5, 0.5, 0.5))

        val w = world()

        assertEquals(light, w.light())
        assertTrue(w.contains(s1))
        assertTrue(w.contains(s2))
    }

    @Test
    internal fun `intersect default world`() {
        val w = world()
        val r = ray(point(0, 0, -5), vector(0, 0, 1))

        val intersections = r.intersect(w)

        assertEquals(4, intersections.size)
        assertEquals(4.0, intersections[0].t)
        assertEquals(4.5, intersections[1].t)
        assertEquals(5.5, intersections[2].t)
        assertEquals(6.0, intersections[3].t)
    }
}