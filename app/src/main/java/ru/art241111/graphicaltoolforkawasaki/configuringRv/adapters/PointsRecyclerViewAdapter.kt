package ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.art241111.graphicaltoolforkawasaki.configuringRv.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.databinding.RecyclerViewPointsItemBinding

class PointsRecyclerViewAdapter(private var items: List<String>,
                                private var itemListener: OnItemClickListener) : RecyclerView.Adapter<PointsRecyclerViewAdapter.ViewHolder>() {
    /**
     * Create items.
     */
    class ViewHolder(private var binding: RecyclerViewPointsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(point: String, listener: OnItemClickListener?) {
            binding.pointName = point.substringBefore("@")
            binding.pointCoordinate = point.substringAfter("@")

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
    fun replaceData(arrayList: List<String>) {
        items = arrayList
        notifyDataSetChanged()
    }
}