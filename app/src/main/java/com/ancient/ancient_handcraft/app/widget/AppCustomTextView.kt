package com.ancient.ancient_handcraft.app.widget
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ancient.ancient_handcraft.R

/**
 * Created by Kinsuk on 21-11-2017.
 */
class AppCustomTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
        setCustomFont(context, attrs)
    }


    private fun setCustomFont(ctx: Context, attrs: AttributeSet) {
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.ViewStyle)
        val customFont = a.getString(R.styleable.ViewStyle_customFont)
        setCustomFont(ctx, customFont)
        a.recycle()
    }

    fun setCustomFont(ctx: Context, asset: String?): Boolean {
        var tf: Typeface? = null
        try {
            tf = Typeface.createFromAsset(ctx.assets, asset)
        } catch (e: Exception) {
            return false
        }

        typeface = tf
        return true
    }

    fun init() {
        val tf = Typeface.createFromAsset(context.assets, "fonts/Lato-Regular.ttf")
        setTypeface(tf, Typeface.NORMAL)

    }



}