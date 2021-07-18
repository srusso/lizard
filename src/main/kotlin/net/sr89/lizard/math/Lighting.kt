package net.sr89.lizard.math

import net.sr89.lizard.World
import net.sr89.lizard.data.Color
import net.sr89.lizard.data.Material
import net.sr89.lizard.data.Pattern
import net.sr89.lizard.data.PointLight
import net.sr89.lizard.data.Tuple
import net.sr89.lizard.data.black
import net.sr89.lizard.data.hit
import net.sr89.lizard.data.magnitude
import net.sr89.lizard.data.minus
import net.sr89.lizard.data.normalize
import net.sr89.lizard.data.plus
import net.sr89.lizard.data.ray
import net.sr89.lizard.data.reflect
import net.sr89.lizard.data.times
import net.sr89.lizard.data.unaryMinus
import net.sr89.lizard.objects.GeometricalObject
import kotlin.math.pow

fun lighting(
    m: Material,
    obj: GeometricalObject,
    light: PointLight,
    point: Tuple,
    eye: Tuple,
    normalv: Tuple,
    inShadow: Boolean = false): Color {
    val surfaceColor = if(m.pattern != null) {
        stripeAt(m.pattern, obj, point)
    } else {
        m.surfaceColor
    }

    val effectiveColor = surfaceColor * light.intensity
    val lightv = (light.position - point).normalize()
    val ambient = effectiveColor * m.ambient
    val lightDotNormal = lightv * normalv

    val (diffuse, specular) = if (lightDotNormal < 0) {
        Pair(black, black)
    } else {
        Pair(
            calculateDiffuse(effectiveColor, m, lightDotNormal),
            calculateSpecular(m, light, lightv, normalv, eye)
        )
    }

    return if (inShadow) {
        ambient
    } else {
        ambient + diffuse + specular
    }
}

fun isShadowed(world: World, point: Tuple): Boolean {
    val v = world.light().position - point
    val distance = v.magnitude()
    val direction = v.normalize()
    val r = ray(point, direction)
    val intersections = r.intersect(world)
    val hit = intersections.hit()

    return hit != null && hit.t < distance
}

fun stripeAt(pattern: Pattern, obj: GeometricalObject, point: Tuple): Color {
    val objectPoint  = obj.transform().inverse() * point
    val patternPoint = pattern.transform().inverse() * objectPoint
    return pattern.colorAt(patternPoint)
}

private fun calculateSpecular(m: Material, light: PointLight, lightv: Tuple, normalv: Tuple, eye: Tuple): Color {
    val reflectv = (-lightv).reflect(normalv)
    val reflectDotEye = reflectv * eye

    return if (reflectDotEye <= 0) {
        black
    } else {
        val factor = reflectDotEye.pow(m.shininess)
        light.intensity * m.specular * factor
    }
}

private fun calculateDiffuse(
    effectiveColor: Color,
    m: Material,
    lightDotNormal: Double
) = effectiveColor * m.diffuse * lightDotNormal