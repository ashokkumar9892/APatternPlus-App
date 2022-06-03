package com.example.patternclinic.home.drawerFragments.healthTips

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.data.model.Tip
import com.example.patternclinic.databinding.ItemHealthTipsBinding
import com.example.patternclinic.utils.Keys
import com.google.gson.Gson

class HealthTipsAdapter() : RecyclerView.Adapter<HealthTipsAdapter.ViewHolder>() {
    val list by lazy { mutableListOf<Tip>() }
    fun addList(newList: List<Tip>) {
        list.addAll(newList)
        notifyItemRangeInserted(0, newList.size)
    }

    inner class ViewHolder(v: ItemHealthTipsBinding) : RecyclerView.ViewHolder(v.root) {
        val bind = v
        init {
            v.root.setOnClickListener {
                v.root.context.startActivity(Intent(v.root.context,HealthTipDetailActivity::class.java).putExtra(
                    Keys.HEALTH_TIP_DETAIL,Gson().toJson(list[absoluteAdapterPosition])))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemHealthTipsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.bind) {
            Glide.with(root.context).load(list[position].image ?: "")
                .placeholder(R.drawable.dummy_food).into(ivImage)
            tvTitle.text = list[position].sk ?: ""
            tvDescription.text = list[position].description ?: ""
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}