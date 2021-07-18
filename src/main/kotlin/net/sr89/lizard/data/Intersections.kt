package net.sr89.lizard.data

import net.sr89.lizard.objects.GeometricalObject

fun intersection(t: Number, obj: GeometricalObject) = Intersection(t.toDouble(), obj)

fun intersections(vararg intersections: Intersection) = Intersections(intersections.toList())

data class Intersection(val t: Double, val obj: GeometricalObject)

data class Intersections(val intersections: List<Intersection>) {
    val size = intersections.size

    fun sorted(): Intersections = Intersections(intersections.sortedBy { it.t })
}

operator fun Intersections.get(i: Int): Intersection = intersections[i]

operator fun Intersections.plus(others: Intersections): Intersections =
    Intersections(intersections + others.intersections)

fun Intersections.hit(): Intersection? = intersections.filter { it.t >= 0.0 }.minByOrNull { it.t }


data class IntersectionState(
    val t: Double,
    val obj: GeometricalObject,
    val point: Tuple,
    val overPoint: Tuple,
    val eyev: Tuple,
    val normalv: Tuple,
    val inside: Boolean
)