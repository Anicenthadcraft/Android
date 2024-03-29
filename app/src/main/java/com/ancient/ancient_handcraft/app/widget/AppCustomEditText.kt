package com.ancient.ancient_handcraft.app.widget
import android.content.Context
import android.graphics.Typeface
import android.text.InputFilter
import android.text.Spanned
import android.util.AttributeSet
import android.widget.EditText
import com.ancient.ancient_handcraft.R


/**
 * Created by Kinsuk on 21-11-2017.
 */
class AppCustomEditText : EditText { //android.support.v7.widget.AppCompatEditText

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

        filters = arrayOf<InputFilter>(EmojiExcludeFilter())
    }

    private inner class EmojiExcludeFilter : InputFilter {

        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
            for (i in start until end) {
                val type = Character.getType(source[i])
                if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                    return ""
                }
            }
            return null
        }
    }
}