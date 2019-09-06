package com.ancient.ancient_handcraft.Utils

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import com.ancient.ancient_handcraft.app.PojoObj.Banner.BannerObject

import java.util.ArrayList
import java.util.SortedSet
import java.util.TreeSet
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Kinsuk on 21-11-2017.
 */

object AnimUtils {

    val PAGES = 10
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    val LOOPS = 10
    val FIRST_PAGE = 2
    val BIG_SCALE = 1.0f
    val SMALL_SCALE = 0.8f
    val DIFF_SCALE = BIG_SCALE - SMALL_SCALE


    var bannerArray = ArrayList<BannerObject>()


    var activityOpen: Boolean? = false
    var isDirectReplyInitiated: Boolean? = false

    fun expand(v: View) {
        v.measure(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        val targetHeight = v.measuredHeight

        v.layoutParams.height = 0
        v.visibility = View.VISIBLE
        val a = object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation
            ) {
                v.layoutParams.height = if (interpolatedTime == 1f)
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                else
                    (targetHeight * interpolatedTime).toInt()
                v.requestLayout()

            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        // 1dp/ms//
        // a.setDuration(700);
        v.startAnimation(a)
    }

    fun isValidateMobileLogin(phoneNo: String): Boolean {
        var isValidate: Boolean = false
        if (phoneNo.length != 10) {
        } else {
            isValidate = true
        }
        return isValidate
    }

    fun isValidEmail(emailId: String): Boolean {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(emailId).matches()
    }

    //*****************************************************************
    fun isValidPassword(password: String): Boolean {

        val pattern: Pattern
        val matcher: Matcher
        //final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@#$%&])[A-Za-z\\\\d$@$!%&]{6,20}";
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)

        return matcher.matches()

    }

    fun collapse(v: View) {
        val initialHeight = v.measuredHeight

        val a = object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation
            ) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }

    fun inFromRightAnimation(): Animation {

        val inFromRight = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, +1.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f
        )
        inFromRight.duration = 500
        inFromRight.interpolator = AccelerateInterpolator()
        return inFromRight
    }

    fun outToRightAnimation(): Animation {
        val outtoRight = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, +1.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f
        )
        outtoRight.duration = 500
        outtoRight.interpolator = AccelerateInterpolator()
        return outtoRight
    }
}
