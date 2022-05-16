package com.example.patternclinic.home.drawerFragments.messages

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.patternclinic.R
import com.example.patternclinic.base.BaseActivity
import com.example.patternclinic.databinding.ActivityViewMessageBinding
import com.example.patternclinic.utils.Keys

class ViewMessageActivity : BaseActivity() {
    lateinit var binding: ActivityViewMessageBinding
    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_message)
        if (intent.hasExtra(Keys.FILE_TYPE_IMAGE)) {
            val image = intent.getStringExtra(Keys.FILE_TYPE_IMAGE)
            Glide.with(this).load(image).into(binding.ivImageView)
        }else{
            binding.vvVideo.visibility= View.VISIBLE
            val video= intent.getStringExtra(Keys.FILE_TYPE_VIDEO)
            binding.vvVideo.setVideoURI(Uri.parse(video))
        }
    }

}