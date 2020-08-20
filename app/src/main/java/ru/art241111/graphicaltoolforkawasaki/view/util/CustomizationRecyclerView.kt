package ru.art241111.graphicaltoolforkawasaki.view.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.art241111.graphicaltoolforkawasaki.MainActivity
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.ItemTouchHelperAdapter
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.ProgramRecyclerViewAdapter
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.configuringRv.helpers.SimpleItemTouchHelperCallback
import java.util.*

class CustomizationRecyclerView(recyclerView: RecyclerView,
                                private val activity: MainActivity,
                                private val list: MutableLiveData<MutableList<String>>,
                                onItemClickListener: OnItemClickListener): ItemTouchHelperAdapter{

    private var programRecyclerView: ProgramRecyclerViewAdapter
            = ProgramRecyclerViewAdapter(arrayListOf(),
                                         onItemClickListener,
                                         this)

    init {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = programRecyclerView

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(programRecyclerView)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)

        updateItems()
    }

    fun updateItems(){
        list.observe(activity as MainActivity,
                androidx.lifecycle.Observer {
                    it.let { programRecyclerView.replaceData(it.toList()) }
                })
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(list.value!!, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(list.value!!, i, i - 1)
            }
        }
        return true
    }

    override fun onItemDismiss(position: Int) {
        list.value?.removeAt(position)
    }

}