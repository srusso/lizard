package net.sr89.lizard.math

import net.sr89.lizard.World
import net.sr89.lizard.data.Intersections
import net.sr89.lizard.data.Matrix
import net.sr89.lizard.data.Ray
import net.sr89.lizard.data.intersection
import net.sr89.lizard.data.intersections
import net.sr89.lizard.data.minus
import net.sr89.lizard.data.plus
import net.sr89.lizard.data.ray
import net.sr89.lizard.data.times
import net.sr89.lizard.objects.GeometricalObject
import net.sr89.lizard.objects.Plane
import net.sr89.lizard.objects.Sphere
import net.sr89.lizard.utils.eequals
import kotlin.math.sqrt

fun Ray.transform(transformation: Matrix): Ray {
    return ray(transformation * origin, transformation * direction)
}

fun Ray.intersect(obj: GeometricalObject): Intersections {
    if (!obj.transform().invertible()) {
        throw IllegalArgumentException("Matrix is not invertible:\n${obj.transform()}")
    }

    val transformed = transform(obj.transform().inverse())

    return when (obj) {
        is Sphere -> transformed.intersectWith(obj)
        is Plane -> transformed.intersectWith(obj)
        else -> throw IllegalArgumentException("Object cannot be intersected")
    }
}

fun Ray.intersect(world: World): Intersections =
    world.mapObjects { intersect(it) }
        .fold(intersections(), Intersections::plus)
        .sorted()

private fun Ray.intersectWith(sphere: Sphere): Intersections {
    // vector from the sphere's center to the ray origin
    val sphereToRay = origin - sphere.center
    val a = direction * direction
    val b = 2 * (direction * sphereToRay)
    val c = (sphereToRay * sphereToRay) - 1
    val discriminant = b * b - 4 * a * c

    return if (discriminant < 0.0) {
        Intersections(emptyList())
    } else {
        Intersections(
            listOf(
                intersection((-b - sqrt(discriminant)) / (2 * a), sphere),
                intersection((-b + sqrt(discriminant)) / (2 * a), sphere)
            )
        )
    }
}

fun Ray.intersectWith(plane: Plane): Intersections {
    return if (direction.y.eequals(0.0)) {
        intersections()
    } else {
        intersections(intersection(-origin.y / direction.y, plane))
    }
}