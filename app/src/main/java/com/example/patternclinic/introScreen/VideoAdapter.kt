package com.example.patternclinic.introScreen

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.VideoView
import androidx.viewpager.widget.PagerAdapter
import com.example.patternclinic.R


class VideoAdapter(val context: Context) : PagerAdapter() {
    var videos = arrayOf<String>(
        "android.resource://" + context.packageName.toString() + "/" + R.raw.v1,
        "android.resource://" + context.packageName.toString() + "/" + R.raw.v2,
        "android.resource://" + context.packageName.toString() + "/" + R.raw.v3,
        "android.resource://" + context.packageName.toString() + "/" + R.raw.v4,
        "android.resource://" + context.packageName.toString() + "/" + R.raw.v5,
        "android.resource://" + context.packageName.toString() + "/" + R.raw.v6,
        "android.resource://" + context.packageName.toString() + "/" + R.raw.v7,
        "android.resource://" + context.packageName.toString() + "/" + R.raw.v8,
        "android.resource://" + context.packageName.toString() + "/" + R.raw.v9
    )

    override fun getCount(): Int {
        return videos.size

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.video_vide,
                container,
                false
            )
        var player = view.findViewById<VideoView>(R.id.video_view)
//        val path = "android.resource://" + context.packageName.toString() + "/" +videos[position]

        player.setVideoURI(Uri.parse(videos[position]))



        player.setOnPreparedListener {
//            it.setVideoScalingMode(WindowManager.LayoutParams.MATCH_PARENT)
            it.setOnCompletionListener {
                player.setVideoURI(Uri.parse(videos[position]))
            }
            it.start()
            it.isLooping = true
//            it.videoHeight=WindowManager.LayoutParams.MATCH_PARENT
        }
        container.addView(view)
        return view
    }
//    fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
//        container.removeView(`object` as View?)
//    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}