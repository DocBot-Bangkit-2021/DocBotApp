package com.example.docbot.ui.welcome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.docbot.R

class SectionPagerAdapter(private val mContext: Context, private var ls: List<Int>) : PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int = ls.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.activity_welcome, container, false)
        val img = view.findViewById<ImageView>(R.id.img_welcome)
        Glide.with(view)
                .load(ls[position])
                .apply(RequestOptions())
                .into(img)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}