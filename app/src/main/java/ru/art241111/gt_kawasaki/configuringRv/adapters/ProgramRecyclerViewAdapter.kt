package ru.art241111.gt_kawasaki.configuringRv.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.ItemTouchHelperAdapter
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.OnDeleteButtonClick
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.gt_kawasaki.databinding.RecyclerViewProgramItemBinding
import ru.art241111.gt_kawasaki.repository.enities.*

class ProgramRecyclerViewAdapter(private var items: MutableList<RobotCommands>,
                                 private var itemListener: OnItemClickListener,
                                 private var deleteListener: OnDeleteButtonClick,
                                 private val itemTouchHelperAdapter: ItemTouchHelperAdapter,
                                 private val resources: Resources)
    : RecyclerView.Adapter<ProgramRecyclerViewAdapter.ViewHolder>(),
        ItemTouchHelperAdapter {

    /**
     * Create items.
     */
    class ViewHolder(private var binding: RecyclerViewProgramItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(program: RobotCommands, listener: OnItemClickListener?,
                 deleteListener:OnDeleteButtonClick, resources: Resources) {

            binding.programName = program.toString()
            when (program) {
                is CloseGripper -> binding.programName = resources.getText(R.string.command_gripper_close) as String
                is OpenGripper -> binding.programName = resources.getText(R.string.command_gripper_open) as String
                is Move -> binding.programName = "${resources.getText(R.string.command_move) as String} " +
                        "${program.coordinate} " +
                        "${resources.getText(R.string.command_move_value) as String} " +
                        "${program.sizeOfPlant}"
                is MoveToPoint -> binding.programName = "${resources.getText(R.string.command_move_to_point) as String} " +
                        "${program.coordinate.name} ${resources.getText(R.string.command_move_to_point_with_coordinate) as String}" +
                        "${program.coordinate.position}  \n"+
                        "${resources.getText(R.string.command_move_to_point_type) as String} " +
                        "${program.type}"
            }

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
            = holder.bind(items[position], itemListener, deleteListener, resources)

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