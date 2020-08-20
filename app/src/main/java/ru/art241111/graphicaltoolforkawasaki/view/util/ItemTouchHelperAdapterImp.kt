package ru.art241111.graphicaltoolforkawasaki.view.util

import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.ItemTouchHelperAdapter
import java.util.*

class ItemTouchHelperAdapterImp(private val list: MutableList<String>): ItemTouchHelperAdapter{
    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(list, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(list, i, i - 1)
            }
        }
        return true
    }

    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
    }

}