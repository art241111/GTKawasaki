package ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.enums

enum class TypesOfMovementToThePoint {
    LMOVE,
    JMOVE
}

fun String.getTypeOfMovementToThePoint(): TypesOfMovementToThePoint{
    return when(this){
        "LMOVE" -> TypesOfMovementToThePoint.LMOVE
        "JMOVE" -> TypesOfMovementToThePoint.JMOVE
        else -> TypesOfMovementToThePoint.LMOVE
    }
}