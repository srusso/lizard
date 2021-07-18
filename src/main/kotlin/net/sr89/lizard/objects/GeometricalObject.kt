package net.sr89.lizard.objects

import net.sr89.lizard.data.Material
import net.sr89.lizard.data.Matrix
import net.sr89.lizard.data.Tuple
import net.sr89.lizard.data.material
import net.sr89.lizard.data.minus
import net.sr89.lizard.data.normalize
import net.sr89.lizard.data.point
import net.sr89.lizard.data.vector
import net.sr89.lizard.math.inverse
import net.sr89.lizard.math.times
import net.sr89.lizard.math.transpose

interface GeometricalObject {
    fun localNormalAt(objectPoint: Tuple): Tuple
    fun material(): Material
    fun transform(): Matrix
    fun thenTransform(newTransform: Matrix): GeometricalObject
}

data class Sphere(
    val center: Tuple,
    val radius: Double,
    val transform: Matrix,
    val material: Material
) : GeometricalObject {
    override fun thenTransform(newTransform: Matrix) = Sphere(center, radius, newTransform * transform, material)

    fun withMaterial(newMaterial: Material) = Sphere(center, radius, transform, newMaterial)

    override fun localNormalAt(objectPoint: Tuple) = objectPoint - point(0, 0, 0)

    override fun material() = material

    override fun transform(): Matrix = transform
}

fun plane(): Plane = Plane(Matrix.identity(4), material())

data class Plane(
    val transform: Matrix,
    val material: Material
) : GeometricalObject {
    override fun localNormalAt(objectPoint: Tuple): Tuple = vector(0, 1, 0)

    override fun material(): Material = material

    override fun transform(): Matrix = transform
    override fun thenTransform(newTransform: Matrix) = Plane(newTransform * transform, material)
}

fun sphere(): Sphere = sphere(point(0, 0, 0), 1, Matrix.identity(4), material())

private fun sphere(center: Tuple, radius: Number, transform: Matrix, material: Material): Sphere {
    return if (!center.isPoint()) {
        throw IllegalArgumentException("Center of sphere needs to be a point")
    } else {
        Sphere(center, radius.toDouble(), transform, material)
    }
}

fun GeometricalObject.normalAt(worldPoint: Tuple): Tuple {
    val objectPoint = transform().inverse() * worldPoint
    val objectNormal = localNormalAt(objectPoint)
    val worldNormalHack = transform().inverse().transpose() * objectNormal
    val worldNormal = vector(worldNormalHack.x, worldNormalHack.y, worldNormalHack.z)
    return worldNormal.normalize()
}
