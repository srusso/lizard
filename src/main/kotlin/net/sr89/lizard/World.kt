package net.sr89.lizard

import net.sr89.lizard.data.PointLight
import net.sr89.lizard.data.color
import net.sr89.lizard.data.material
import net.sr89.lizard.data.point
import net.sr89.lizard.data.pointLight
import net.sr89.lizard.math.scaling
import net.sr89.lizard.objects.GeometricalObject
import net.sr89.lizard.objects.sphere

fun emptyWorld(): World {
    return World(null, GeometricalObjects(emptyList()))
}

fun world(): World {
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

    return World(light, GeometricalObjects(listOf(s1, s2)))
}

data class GeometricalObjects(val geometricalObjects: List<GeometricalObject>) {
    fun isEmpty(): Boolean = geometricalObjects.isEmpty()

    fun contains(obj: GeometricalObject): Boolean = geometricalObjects.contains(obj)

    operator fun plus(obj: GeometricalObject): GeometricalObjects = GeometricalObjects(geometricalObjects + obj)
}

data class World(
    private val light: PointLight?,
    private val objects: GeometricalObjects
) {
    fun hasObjects(): Boolean = !objects.isEmpty()

    fun hasLightSource(): Boolean = light != null

    fun light(): PointLight = light!!

    fun contains(obj: GeometricalObject): Boolean = objects.contains(obj)

    fun <R> mapObjects(map: (GeometricalObject) -> R): List<R> = objects.geometricalObjects.map(map)

    fun mapIndexed(mapper: (Int, GeometricalObject) -> GeometricalObject): World =
        World(light, GeometricalObjects(objects.geometricalObjects.mapIndexed(mapper)))

    fun plusObject(o: GeometricalObject): World = World(light, objects + o)

    operator fun get(i: Int): GeometricalObject = objects.geometricalObjects[i]
}
