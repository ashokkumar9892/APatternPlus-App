package com.example.patternclinic.home.drawerFragments.messages

import android.net.Uri
import android.os.Environment
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.MediaController
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
        binding.ivBackForgot.setOnClickListener {
            finish()
        }
        if (intent.hasExtra(Keys.FILE_TYPE_IMAGE)) {
            val image = intent.getStringExtra(Keys.FILE_TYPE_IMAGE)
            Glide.with(this).load(image).into(binding.ivImageView)
        } else if (intent.hasExtra(Keys.FILE_TYPE_VIDEO)) {
            binding.vvVideo.visibility = View.VISIBLE
            val video = intent.getStringExtra(Keys.FILE_TYPE_VIDEO)
            val mediaController = MediaController(this)
            mediaController.setAnchorView(binding.vvVideo)
            binding.vvVideo.setMediaController(mediaController)
            binding.vvVideo.setVideoURI(Uri.parse(video))
            binding.vvVideo.start()

        } else {


            binding.webView.visibility = View.VISIBLE
            val doc = intent.getStringExtra(Keys.FILE_TYPE_FILE)

            binding.webView.settings.javaScriptEnabled = true
            binding.webView.settings.setLoadWithOverviewMode(true)
            binding.webView.settings.setUseWideViewPort(true)
            binding.webView.webViewClient=object:WebViewClient(){
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view!!.loadUrl(doc!!)
                    return false
                }
            }
//            binding.webView.loadUrl(doc!!)

        }
    }

}