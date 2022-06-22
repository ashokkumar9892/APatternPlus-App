package com.example.patternclinic.home.drawerFragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.data.model.AppointmentInfo
import com.example.patternclinic.databinding.ItemCoachAppoitmentsBinding
import com.example.patternclinic.home.drawerFragments.appointments.CoachProfileActivity
import com.example.patternclinic.utils.Keys

import com.example.patternclinic.utils.dateConvert_7
import com.google.gson.Gson

class AppointmentAdapter(val list: List<AppointmentInfo>) :
    RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {
    inner class ViewHolder(bind: ItemCoachAppoitmentsBinding) : RecyclerView.ViewHolder(bind.root) {
        val bind = bind

        init {
            bind.root.setOnClickListener {
                itemView.context.startActivity(
                    Intent(
                        itemView.context,
                        CoachProfileActivity::class.java
                    ).putExtra(
                        Keys.APPOINTMENT_DETAIL,
                        Gson().toJson(list[absoluteAdapterPosition])
                    )
                )
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            ItemCoachAppoitmentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.bind) {
            tvName.text = list[position].doctorName
            tvDate.text = dateConvert_7(list[position].appointmentDate)
            tvTime.text = list[position].appointmentTime
            Glide.with(holder.itemView.context).load(list[position].doctorPic ?: "")
                .placeholder(R.drawable.dummy2).into(ivImage)
            tvType.text=list[position].appointmentType?:""
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}