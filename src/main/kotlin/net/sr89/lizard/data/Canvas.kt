package net.sr89.lizard.data

fun canvas(width: Int, height: Int): Canvas = Canvas(width, height)

class Canvas(val width: Int, val height: Int) {

    private val pixels = Array(width) { Array(height) { black } }

    fun pixelAt(x: Int, y: Int): Pixel {
        return pixels[x][y]
    }

    fun writePixel(x: Int, y: Int, color: Color) {
        if (x >=0 && x < pixels.size && y >=0 && y < pixels[0].size) {
            pixels[x][y] = color
        }
    }
}