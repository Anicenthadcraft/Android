package com.fieldtrackingsystem.base.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment


open class BaseFragment: Fragment() {

    /* protected open fun doStuff()
    {
//        AppUtils.hideSoftKeyboard(activity)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /*override fun onResume() {
        super.onResume()
        doStuff()

    }*/

}