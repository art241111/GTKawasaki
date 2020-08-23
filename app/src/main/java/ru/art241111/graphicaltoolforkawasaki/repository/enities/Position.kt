package ru.art241111.graphicaltoolforkawasaki.repository.enities

data class Position(var name: String, var position: MutableList<Float>){
    override fun toString(): String {
        return name
    }
}