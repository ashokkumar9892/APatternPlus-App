package com.example.patternclinic.setupDevice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.patternclinic.databinding.ItemDevicesBinding
import com.inuker.bluetooth.library.search.response.SearchResponse

class ConnectDeviceAdapter(var list: List<String>,var click: OnItemClicked) :
    RecyclerView.Adapter<ConnectDeviceAdapter.ViewHolder>() {
   inner class ViewHolder(binding: ItemDevicesBinding) : RecyclerView.ViewHolder(binding.root) {
        var bind = binding
        init {
            itemView.setOnClickListener {
                click.itemClicked(absoluteAdapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemDevicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var unit = list[position]
        holder.bind.deviceName.text = list[position]

    }

    override fun getItemCount(): Int {
        return list.size
    }
}