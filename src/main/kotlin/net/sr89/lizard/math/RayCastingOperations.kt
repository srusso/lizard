package net.sr89.lizard.math

import net.sr89.lizard.World
import net.sr89.lizard.data.Color
import net.sr89.lizard.data.Intersection
import net.sr89.lizard.data.IntersectionState
import net.sr89.lizard.data.Ray
import net.sr89.lizard.data.black
import net.sr89.lizard.data.hit
import net.sr89.lizard.data.plus
import net.sr89.lizard.data.position
import net.sr89.lizard.data.times
import net.sr89.lizard.data.unaryMinus
import net.sr89.lizard.objects.normalAt
import net.sr89.lizard.utils.DELTA

fun prepareComputations(intersection: Intersection, ray: Ray): IntersectionState {
    val point = position(ray, intersection.t)
    val normalv = intersection.obj.normalAt(point)
    val eyev = -ray.direction
    val inside = normalv * eyev < 0
    val signedNormalV = if (inside) -normalv else normalv
    val overPoint = point + signedNormalV * DELTA

    return IntersectionState(
        intersection.t,
        intersection.obj,
        point,
        overPoint,
        eyev,
        signedNormalV,
        inside
    )
}

fun shadeHit(world: World, comps: IntersectionState): Color {
    val shadowed = isShadowed(world, comps.overPoint)
    return lighting(comps.obj.material(), comps.obj, world.light(), comps.overPoint, comps.eyev, comps.normalv, shadowed)
}

fun colorAt(world: World, ray: Ray): Color {
    val hit = ray.intersect(world).hit()

    return if (hit == null) {
        black
    } else {
        shadeHit(world, prepareComputations(hit, ray))
    }
}