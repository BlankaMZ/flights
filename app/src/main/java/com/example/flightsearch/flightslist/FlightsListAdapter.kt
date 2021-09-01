package com.example.flightsearch.flightslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flightsearch.databinding.FlightItemViewBinding
import com.example.flightsearch.domain.FlightInfo

class FlightsListAdapter(val clickListener: FlightsListListener) :
    ListAdapter<FlightInfo, FlightsListAdapter.ViewHolder>(FlightsListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)

    }

    class ViewHolder private constructor(val binding: FlightItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FlightInfo, clickListener: FlightsListListener) {
            binding.flightInfo = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =
                    LayoutInflater.from(parent.context)
                val binding = FlightItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class FlightsListDiffCallback : DiffUtil.ItemCallback<FlightInfo>() {
    override fun areItemsTheSame(oldItem: FlightInfo, newItem: FlightInfo): Boolean {
        return oldItem.flightNumber == newItem.flightNumber
    }

    override fun areContentsTheSame(oldItem: FlightInfo, newItem: FlightInfo): Boolean {
        return oldItem == newItem
    }
}

class FlightsListListener(val clickListener: (fligtInfo: FlightInfo) -> Unit) {
    fun onClick(fligtInfo: FlightInfo) = clickListener(fligtInfo)
}


