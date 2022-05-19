package com.example.patternclinic.home.drawerFragments.messages

import android.content.Intent
import android.net.Uri
import android.view.View
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
    var googleDocs = "https://docs.google.com/viewer?url="
    override fun binding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_message)
        binding.ivBackForgot.setOnClickListener {
            finish()
        }
        if (intent.hasExtra(Keys.FILE_TYPE_IMAGE)) {
            val image = intent.getStringExtra(Keys.FILE_TYPE_IMAGE)
            Glide.with(this).load(image).into(binding.ivImageView)
        } else if (intent.hasExtra(Keys.FILE_TYPE_VIDEO)) {
            binding.loader.visibility=View.VISIBLE

            binding.vvVideo.setOnPreparedListener {

                    binding.loader.visibility=View.GONE

            }

            binding.vvVideo.visibility = View.VISIBLE
            val video = intent.getStringExtra(Keys.FILE_TYPE_VIDEO)
            val mediaController = MediaController(this)
            mediaController.setAnchorView(binding.vvVideo)
            binding.vvVideo.setMediaController(mediaController)
            binding.vvVideo.setVideoURI(Uri.parse(video))
            binding.vvVideo.start()

        } else {
            binding.loader.visibility = View.VISIBLE
            binding.webView.visibility = View.VISIBLE
            val doc = intent.getStringExtra(Keys.FILE_TYPE_FILE)
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.settings.loadWithOverviewMode = true
            binding.webView.webViewClient=object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    runOnUiThread {
                        binding.loader.visibility = View.GONE
                    }
                }
            }
            binding.webView.settings.useWideViewPort = true
            binding.webView.loadUrl(googleDocs+doc!!)



//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(doc!!))
//            startActivity(browserIntent)

        }
    }

}