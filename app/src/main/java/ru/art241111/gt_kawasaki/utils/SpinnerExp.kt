package ru.art241111.gt_kawasaki.utils

import android.widget.Spinner

// TODO think about how do it differently
fun Spinner.setSelection(itemName: String){
    var position = 0
    for(i in 0 until this.count){
        val name = this.getItemAtPosition(i)
        if(this.getItemAtPosition(i).toString() == itemName) {
            position = i
        }
    }
    this.setSelection(position)
}