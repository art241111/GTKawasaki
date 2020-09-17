package ru.art241111.gt_kawasaki.configuringRv.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.ItemTouchHelperAdapter
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.OnDeleteButtonClick
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.gt_kawasaki.databinding.RecyclerViewProgramItemBinding
import ru.art241111.gt_kawasaki.utils.enitiesCommandsAndPosition.commands.RobotCommands

class ProgramRecyclerViewAdapter(private var items: MutableList<RobotCommands>,
                                 private var itemListener: OnItemClickListener,
                                 private var deleteListener: OnDeleteButtonClick,
                                 private val itemTouchHelperAdapter: ItemTouchHelperAdapter)
    : RecyclerView.Adapter<ProgramRecyclerViewAdapter.ViewHolder>(),
        ItemTouchHelperAdapter {

    /**
     * Create items.
     */
    class ViewHolder(private var binding: RecyclerViewProgramItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(program: RobotCommands, listener: OnItemClickListener?,
                 deleteListener:OnDeleteButtonClick) {
            binding.command = program

            if (listener != null) {
                binding.root.setOnClickListener {
                    listener.onItemClick(layoutPosition)
                }

                binding.ivDeletePoint.setOnClickListener {
                    deleteListener.onDeleteButtonClick(layoutPosition)
                }
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
            = holder.bind(items[position], itemListener, deleteListener)

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