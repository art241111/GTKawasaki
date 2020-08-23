package ru.art241111.graphicaltoolforkawasaki.repository.enities.expansion

fun List<Float>.toStringForRobot():String{
    var returnString = ""
    this.map { returnString += "$it;"}
    return returnString
}