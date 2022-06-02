package com.example.patternclinic.home.drawerFragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.patternclinic.data.model.AppointmentInfo
import com.example.patternclinic.databinding.ItemCoachAppoitmentsBinding

import com.example.patternclinic.utils.dateConvert_7

class AppointmentAdapter(val list: List<AppointmentInfo>) :
    RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {
    inner class ViewHolder(bind: ItemCoachAppoitmentsBinding) : RecyclerView.ViewHolder(bind.root) {
        val bind = bind

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            ItemCoachAppoitmentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.bind){
            tvName.text=list[position].doctorName
            tvDate.text= dateConvert_7(list[position].appointmentDate)
            tvTime.text=list[position].appointmentTime
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}