package ru.art241111.gt_kawasaki.repository.enities.expansion

fun List<Float>.toStringForRobot():String{
    var returnString = ""
    this.map { returnString += "$it;"}
    return returnString
}