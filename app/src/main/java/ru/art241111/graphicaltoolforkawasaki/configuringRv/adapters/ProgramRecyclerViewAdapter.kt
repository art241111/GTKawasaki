package ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.databinding.RecyclerViewProgramItemBinding
import java.util.*

class ProgramRecyclerViewAdapter(private var items: MutableList<String>,
                                 private var itemListener: OnItemClickListener,
                                 private val itemTouchHelperAdapter: ItemTouchHelperAdapter)
    : RecyclerView.Adapter<ProgramRecyclerViewAdapter.ViewHolder>(),
        ItemTouchHelperAdapter {

    /**
     * Create items.
     */
    class ViewHolder(private var binding: RecyclerViewProgramItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(program: String, listener: OnItemClickListener?) {
            binding.programName = program.replace("@", " ")

            if (listener != null) {
                binding.root.setOnClickListener { listener.onItemClick(layoutPosition) }
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerViewProgramItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], itemListener)

    override fun getItemCount(): Int = items.size

    /**
     * Data refresh.
     */
    fun replaceData(arrayList: List<String>) {
        items = arrayList.toMutableList()
        notifyDataSetChanged()
    }

    /**
     * Implements ItemTouchHelper
     */
    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean{
        notifyItemMoved(fromPosition, toPosition)
        return itemTouchHelperAdapter.onItemMove(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        itemTouchHelperAdapter.onItemDismiss(position)
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}