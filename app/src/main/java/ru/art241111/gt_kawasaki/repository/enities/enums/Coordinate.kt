package ru.art241111.gt_kawasaki.repository.enities.enums

enum class Coordinate {
    X,
    Y,
    Z,
    DX,
    DY,
    DZ
}

fun String.getCoordinate(): Coordinate{
    return when(this){
        "X" -> Coordinate.X
        "Y" -> Coordinate.Y
        "Z" -> Coordinate.Z
        "DX" -> Coordinate.DX
        "DY" -> Coordinate.DY
        "DZ" -> Coordinate.DZ
        else -> Coordinate.X
    }
}