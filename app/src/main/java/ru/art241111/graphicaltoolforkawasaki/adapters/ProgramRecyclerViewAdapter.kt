package ru.art241111.graphicaltoolforkawasaki.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.art241111.graphicaltoolforkawasaki.adapters.protocols.OnItemClickListener
import ru.art241111.graphicaltoolforkawasaki.databinding.RecyclerViewProgramItemBinding

class ProgramRecyclerViewAdapter(private var items: List<String>,
                                 private var itemListener: OnItemClickListener) : RecyclerView.Adapter<ProgramRecyclerViewAdapter.ViewHolder>() {
    /**
     * Create items.
     */
    class ViewHolder(private var binding: RecyclerViewProgramItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(program: String, listener: OnItemClickListener?) {
            binding.programName = program.replace("@"," ")

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
        items = arrayList
        notifyDataSetChanged()
    }
}