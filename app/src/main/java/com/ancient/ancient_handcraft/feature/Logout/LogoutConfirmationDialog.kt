package com.ancient.ancient_handcraft.feature.Logout

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.app.AppData
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_logout.view.*
import kotlinx.android.synthetic.main.dialog_verify_otp.*
import kotlinx.android.synthetic.main.dialog_verify_otp.view.*
import kotlinx.android.synthetic.main.dialog_verify_otp.view.cancel_tv
import kotlinx.android.synthetic.main.dialog_verify_otp.view.dialog_header_TV
import kotlinx.android.synthetic.main.dialog_verify_otp.view.submit_tv


class LogoutConfirmationDialog() :
    DialogFragment() {

    var disposable: Disposable? = null
    private var appData: AppData? = null

    companion object {
        private lateinit var header: String
        private lateinit var body: String
        private lateinit var mListener: LogoutConfirmationDialogClickListener
        private lateinit var mContext: Context
        fun getInstance(
            context: Context,
            mHeader: String,
            mBody: String,
            listener: LogoutConfirmationDialogClickListener
        ): LogoutConfirmationDialog {
            val verifyOtpDialog = LogoutConfirmationDialog()
            mContext = context
            mListener = listener
            header = mHeader
            body = mBody
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
                } else {
                    return false
                }
                return true
            }
        })
        val v = inflater!!.inflate(R.layout.dialog_logout, container, false)

        initView(v)
        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun initView(view: View) {
        view.body_tv.visibility = View.VISIBLE
        view.body_tv.text = body

        view.submit_tv.text = mContext.resources.getString(R.string.confirm_text)

        appData = AppData(mContext)
        view.dialog_header_TV.text = header
        view.cancel_tv.setOnClickListener({ v ->
            disposable?.dispose()
            dismiss()
        })
        view.submit_tv.setOnClickListener({ v ->
            mListener.onSubmitClick()
        })
    }
}