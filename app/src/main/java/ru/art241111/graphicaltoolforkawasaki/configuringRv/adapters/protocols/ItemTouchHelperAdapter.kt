package ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onItemDismiss(position: Int)
}