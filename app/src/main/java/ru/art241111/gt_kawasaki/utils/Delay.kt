package ru.art241111.gt_kawasaki.utils

object Delay {
    fun customDelay(time: Long = 100L){
        try {
            Thread.sleep(time)
        } catch (e: java.lang.Exception) {
        }
    }

    fun little(){
        customDelay(50L)
    }

    fun middle(){
        customDelay(1000L)
    }

    fun long(){
        customDelay(5000L)
    }
}