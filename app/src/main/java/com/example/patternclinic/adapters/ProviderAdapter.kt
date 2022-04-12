package com.example.patternclinic.adapters

import com.example.patternclinic.R
import com.example.patternclinic.utils.BaseAdapter

class ProviderAdapter:BaseAdapter(R.layout.item_providers) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
      return 6
    }
}