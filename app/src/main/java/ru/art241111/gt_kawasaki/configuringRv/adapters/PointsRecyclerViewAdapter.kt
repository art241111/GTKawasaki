package ru.art241111.gt_kawasaki.configuringRv.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.art241111.gt_kawasaki.R
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.OnDeleteButtonClick
import ru.art241111.gt_kawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.gt_kawasaki.databinding.RecyclerViewPointsItemBinding
import ru.art241111.gt_kawasaki.utils.entitiesCommandsAndPosition.Position

class PointsRecyclerViewAdapter(private var items: List<Position>,
                                private var itemListener: OnItemClickListener,
                                private var deleteListener: OnDeleteButtonClick,
                                private val resources: Resources) : RecyclerView.Adapter<PointsRecyclerViewAdapter.ViewHolder>() {
    /**
     * Create items.
     */
    class ViewHolder(private var binding: RecyclerViewPointsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(point: Position, listener: OnItemClickListener?,
                 deleteListener:OnDeleteButtonClick, resources: Resources) {

            binding.pointName = "${resources.getText(R.string.point_name)}: ${point.name}"
            binding.pointCoordinate = "${resources.getText(R.string.point_coordinate)} ${point.position}"

            if (listener != null) {
                binding.root.setOnClickListener{
                    listener.onItemClick(layoutPosition)
                }

                binding.ivDeletePoint.setOnClickListener{
                    deleteListener.onDeleteButtonClick(layoutPosition)
                }
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerViewPointsItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], itemListener, deleteListener, resources)

    override fun getItemCount(): Int = items.size

    /**
     * Data refresh.
     */
    fun replaceData(arrayList: List<Position>) {
        items = arrayList
        notifyDataSetChanged()
    }
}