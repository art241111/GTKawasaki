package ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols

/**
 * Interface that defines changes when moving and when deleting
 * items in recycler view.
 *
 * @author Artem Gerasimov
 */
interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onItemDismiss(position: Int)
}