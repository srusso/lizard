package net.sr89.lizard.utils

import java.nio.file.Files
import java.nio.file.Paths

fun String.writeTo(file: String) = Files.writeString(Paths.get(file), this)
