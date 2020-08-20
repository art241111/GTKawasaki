package ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.ItemTouchHelperAdapter
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.databinding.RecyclerViewProgramItemBinding
import ru.art241111.graphicaltoolforkawasaki.repository.enity.CloseGripper
import ru.art241111.graphicaltoolforkawasaki.repository.enity.OpenGripper
import ru.art241111.graphicaltoolforkawasaki.repository.enity.RobotCommands

class ProgramRecyclerViewAdapter(private var items: MutableList<RobotCommands>,
                                 private var itemListener: OnItemClickListener,
                                 private val itemTouchHelperAdapter: ItemTouchHelperAdapter)
    : RecyclerView.Adapter<ProgramRecyclerViewAdapter.ViewHolder>(),
        ItemTouchHelperAdapter {

    /**
     * Create items.
     */
    class ViewHolder(private var binding: RecyclerViewProgramItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(program: RobotCommands, listener: OnItemClickListener?) {

            binding.programName = program.toString()
            when (program) {
                is CloseGripper -> binding.programName = "Закрыть захват"
                is OpenGripper -> binding.programName = "Открыть захват"
            }

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
    fun replaceData(arrayList: List<RobotCommands>) {
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