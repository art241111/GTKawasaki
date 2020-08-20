package ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.databinding.RecyclerViewPointsItemBinding
import ru.art241111.graphicaltoolforkawasaki.repository.enity.Position

class PointsRecyclerViewAdapter(private var items: List<Position>,
                                private var itemListener: OnItemClickListener) : RecyclerView.Adapter<PointsRecyclerViewAdapter.ViewHolder>() {
    /**
     * Create items.
     */
    class ViewHolder(private var binding: RecyclerViewPointsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(point: Position, listener: OnItemClickListener?) {
            binding.pointName = point.name
            binding.pointCoordinate = point.position.toString()

            if (listener != null) {
                binding.root.setOnClickListener { listener.onItemClick(layoutPosition) }
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
            = holder.bind(items[position], itemListener)

    override fun getItemCount(): Int = items.size

    /**
     * Data refresh.
     */
    fun replaceData(arrayList: List<Position>) {
        items = arrayList
        notifyDataSetChanged()
    }
}