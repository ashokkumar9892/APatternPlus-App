package com.example.patternclinic.utils



import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.patternclinic.R


@BindingAdapter("app:tint")
fun ImageView.setImageTint(@ColorInt color: Int) {
    setColorFilter(color)
}

@BindingAdapter("adapter")
fun RecyclerView.setRecyclerViewAdapter(adapterClass: RecyclerView.Adapter<*>?) {
    adapter = adapterClass
}


@BindingAdapter("adapter")
fun ViewPager2.setViewPagerAdapter(adapterClass: RecyclerView.Adapter<*>?) {
    adapter = adapterClass
}

@BindingAdapter("setImage")
fun ImageView.setImage(url: String) {
    Glide.with(this).load(url)
        .error(R.drawable.ic_dummy_user)
        .placeholder(R.drawable.ic_dummy_user)
        .into(this)
}

