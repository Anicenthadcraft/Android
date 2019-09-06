package com.ancient.ancient_handcraft.feature.VerifyOTP

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.ancient.ancient_handcraft.R
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_verify_otp.*
import kotlinx.android.synthetic.main.dialog_verify_otp.view.*
import android.view.KeyEvent.KEYCODE_BACK
import android.view.*
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.webhelper.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class VerifyOTPDialog() :
    DialogFragment() {

    val otp: String
        get() = verify_otp_edt.text.toString() ?: ""
    var disposable: Disposable? = null

    companion object {
        private lateinit var header: String
        private lateinit var mobileno: String
        private lateinit var mListener: VerifyOTPDialogClickListener
        private lateinit var mContext: Context
        fun getInstance(
            context: Context,
            mHeader: String,
            mobileNo: String,
            listener: VerifyOTPDialogClickListener
        ): VerifyOTPDialog {
            val verifyOtpDialog = VerifyOTPDialog()
            mContext = context
            mListener = listener
            mobileno = mobileNo
            header = mHeader
            return verifyOtpDialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener(object : DialogInterface.OnKeyListener {
            override fun onKey(
                arg0: DialogInterface, keyCode: Int,
                event: KeyEvent
            ): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //dialog.dismiss()
                }
                return true
            }
        })
        val v = inflater!!.inflate(R.layout.dialog_verify_otp, container, false)
        initView(v)
        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun initView(v: View) {
        v.dialog_header_TV.text = header
        v.submit_tv.setOnClickListener({ v ->
            if (otp.isNullOrEmpty())
                AppUtils.showToastMsg(
                    context!!,
                    context!!.resources.getString(R.string.verifyotp_blank_error)
                )
            else
                if (AppUtils.isOnline(mContext))
                    InitiateVarifyUserProcess(mobileno, otp)
                else
                    AppUtils.showToastMsg(
                        mContext,
                        context!!.resources.getString(R.string.no_internet_connection_check)
                    )
            //mListener.onSubmitClick(otp)

        })
    }

    fun InitiateVarifyUserProcess(mobileNo: String, otp: String) {
        mListener.showDialogLoader()
        ApiClient.apiService.VerifyOTP(
            mobileNo = mobileNo, otp = otp
        ).subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    mListener.hideDialogLoader()
                    when (response?.status ?: "") {
                        200 -> {
                            //view.openDashboard()
                            mListener.onVerifySuccess()
                            dismiss()
                            disposable!!.dispose()
                        }
                        else -> {
                            mListener.onVerifyFailure()
                            AppUtils.showToastMsg(
                                context!!,
                                context!!.resources.getString(R.string.something_went_wrong)
                            )
                            dismiss()
                            //disposable!!.dispose()
                        }
                    }
                },
                { error ->
                    mListener.onVerifyFailure()
                    mListener.hideDialogLoader()
                    AppUtils.showToastMsg(
                        context!!,
                        context!!.resources.getString(R.string.something_went_wrong)
                    )
                    dismiss()
                    //disposable!!.dispose()
                }
            )
    }
}