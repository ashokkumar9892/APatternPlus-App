package com.example.patternclinic.selectTeam.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.data.model.CoachProviderListResponse
import com.example.patternclinic.data.model.DoctorInfo
import com.example.patternclinic.databinding.ItemProvidersBinding
import okhttp3.MultipartBody
import okhttp3.MultipartReader
import retrofit2.http.Multipart

class CoachProviderListAdapter(var list: List<DoctorInfo>, var clickEvent: OnItemClicked) :
    RecyclerView.Adapter<CoachProviderListAdapter.ViewHolder>() {
    inner class ViewHolder(viewBind: ItemProvidersBinding) :
        RecyclerView.ViewHolder(viewBind.root) {
        var bind = viewBind

        init {
            itemView.setOnClickListener {
                clickEvent.onClicked(list[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewBind =
            ItemProvidersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBind)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var unit = list[position]
        with(holder.bind) {
            Glide.with(ivImage).load(unit.profileImage).placeholder(R.drawable.dummy_provider)
                .into(ivImage)
            tvName.text = unit.userName
            if (unit.designation != null) {
                tvDesigntaion.text = unit.designation.toString()
            } else {
                tvDesigntaion.text = ""
            }
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
}