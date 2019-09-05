package com.ancient.ancient_handcraft.feature.ProductDetailsFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.ancient.ancient_handcraft.R
import kotlinx.android.synthetic.main.inflate_product_item.view.*

class MyCustomPagerAdapter(internal var context: Context, internal var images: IntArray) : PagerAdapter() {
    internal var layoutInflater: LayoutInflater


    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = layoutInflater.inflate(R.layout.inflate_product_item, container, false)

        itemView.product_iv.setImageResource(images[position])

        container.addView(itemView)

        //listening to image click
        itemView.product_iv.setOnClickListener {
            Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show()
        }

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
