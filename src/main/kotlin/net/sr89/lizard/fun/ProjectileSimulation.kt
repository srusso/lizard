package net.sr89.lizard.`fun`

import net.sr89.lizard.data.*
import net.sr89.lizard.utils.toPPMFormat
import net.sr89.lizard.utils.writeTo

data class Projectile(val position: Tuple, val velocity: Tuple) {

}

data class Environment(val gravity: Tuple, val wind: Tuple) {

}

fun tick(env: Environment, projectile: Projectile): Projectile =
    Projectile(
        projectile.position + projectile.velocity,
        projectile.velocity + env.gravity + env.wind
    )

fun main() {
    val p = Projectile(point(0, 1, 0), vector(1, 1.4, 0).normalize() * 11.25)
    val e = Environment(vector(0, -0.1, 0), vector(-0.01, 0, 0.0))
    val canvas = canvas(900, 550)

    var projectile = p

    while(projectile.position.y > 0.0) {
        projectile = tick(e, projectile)

        canvas.writePixel(
            projectile.position.x.toInt(),
            canvas.height - projectile.position.y.toInt(),
            redColor)
    }

    canvas.toPPMFormat().writeTo("./projectile.ppm")
}