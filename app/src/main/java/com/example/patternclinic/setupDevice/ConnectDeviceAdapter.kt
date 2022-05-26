package com.example.patternclinic.setupDevice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.patternclinic.R
import com.example.patternclinic.databinding.ItemDevicesBinding

class ConnectDeviceAdapter(var list: List<String>,var click: OnItemClicked) :
    RecyclerView.Adapter<ConnectDeviceAdapter.ViewHolder>() {
    var selected=-1
   inner class ViewHolder(binding: ItemDevicesBinding) : RecyclerView.ViewHolder(binding.root) {
        var bind = binding
        init {
            itemView.setOnClickListener {
                click.itemClicked(absoluteAdapterPosition)
                selected=absoluteAdapterPosition
                notifyDataSetChanged()
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
        if(position==selected){
            holder.bind.mcv.strokeColor=ContextCompat.getColor(holder.itemView.context,R.color.color_primary)
        }else{
            holder.bind.mcv.strokeColor=ContextCompat.getColor(holder.itemView.context,R.color.white)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}