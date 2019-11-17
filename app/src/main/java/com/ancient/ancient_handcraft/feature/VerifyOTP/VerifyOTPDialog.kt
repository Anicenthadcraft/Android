package com.ancient.ancient_handcraft.feature.VerifyOTP

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.util.Log
import androidx.fragment.app.DialogFragment
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_verify_otp.*
import kotlinx.android.synthetic.main.dialog_verify_otp.view.*
import android.view.*
import com.ancient.ancient_handcraft.AncientHandcraftApplication
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.webhelper.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.app.AppData
import kotlinx.android.synthetic.main.loader_layout.view.*
import android.text.InputType
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.UserSession
import kotlinx.android.synthetic.main.signup_activity.*


class VerifyOTPDialog() :
    DialogFragment() {

    val otp: String
        get() = verify_otp_edt.text.toString() ?: ""
    var disposable: Disposable? = null
    val maxLengthofEditText = 6
    private var appData: AppData? = null
    companion object {
        private lateinit var header: String
        private lateinit var mobileno: String
        private var mOtp: String? = ""
        private lateinit var mListener: VerifyOTPDialogClickListener
        private lateinit var mContext: Context
        fun getInstance(
            context: Context,
            mHeader: String,
            Otp: String?,
            mobileNo: String,
            listener: VerifyOTPDialogClickListener
        ): VerifyOTPDialog {
            val verifyOtpDialog = VerifyOTPDialog()
            mContext = context
            mListener = listener
            mobileno = mobileNo
            mOtp = Otp
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
                } else {
                    return false
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

    private fun initView(view: View) {
        appData = AppData(mContext)
        view.dialog_header_TV.text = header
        view.verify_otp_edt.setFilters(
            arrayOf<InputFilter>(
                InputFilter.LengthFilter(
                    maxLengthofEditText
                )
            )
        )
        view.verify_otp_edt.text = Editable.Factory.getInstance().newEditable(mOtp)
        view.loader_ll.setOnClickListener({ v -> })
        view.cancel_tv.setOnClickListener({ v ->
            disposable?.dispose()
            dismiss()
        })
        view.submit_tv.setOnClickListener({ v ->
            if (otp.isNullOrEmpty())
                AppUtils.showToastMsg(
                    context!!,
                    context!!.resources.getString(R.string.verifyotp_blank_error)
                )
            else
                if (AppUtils.isOnline(mContext)) {
                    AppUtils.hideSoftKeyboard(this.requireActivity())
                    InitiateVarifyUserProcess(view, mobileno, otp)
                } else
                    AppUtils.showToastMsg(
                        mContext,
                        context!!.resources.getString(R.string.no_internet_connection_check)
                    )
            //mListener.onSubmitClick(otp)

        })
    }

    fun InitiateVarifyUserProcess(v: View, mobileNo: String, otp: String) {
        showDialogLoader(v)
        ApiClient.apiService.VerifyOTP(
            mobileNo = mobileNo, otp = otp
        ).subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    hideDialogLoader(v)
                    when (response?.status ?: "") {
                        200 -> {
                            if (AncientHandcraftApplication.Companion.getCurrentActivity.equals("SignUpActivity")) {
                                AppUtils.showToastMsg(
                                    context!!,
                                    context!!.resources.getString(R.string.registration_success_text)
                                )
                            }

                            val session = UserSession(true, response?.payload)
                            appData?.userSession= session
                            //view.openDashboard()
                            mListener.onVerifySuccess()
                            dismiss()
                            disposable?.dispose()
                        }
                        else -> {
                            mListener.onVerifyFailure()
                            AppUtils.showToastMsg(
                                context!!,
                                context!!.resources.getString(R.string.something_went_wrong)
                            )
                            dismiss()
                            disposable?.dispose()
                        }
                    }
                },
                { error ->
                    try {
                        if (error is HttpException) {
                            val body: ResponseBody =
                                (error as HttpException).response()?.errorBody() as ResponseBody
                            val jsonObject = JSONObject(body.string())
                            val status = jsonObject.getInt("status")
                            when (status ?: "") {
                                400 -> {
                                    AppUtils.showToastMsg(
                                        context!!,
                                        context!!.resources.getString(R.string.otp_validation_error)
                                    )
                                }
                                else -> {
                                    AppUtils.showToastMsg(
                                        context!!,
                                        context!!.resources.getString(R.string.something_went_wrong)
                                    )
                                }
                            }

                        } else {
                            AppUtils.showToastMsg(
                                context!!,
                                context!!.resources.getString(R.string.something_went_wrong)
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("Exception", "" + e.message)
                    }
                    mListener.onVerifyFailure()
                    hideDialogLoader(v)
                    disposable?.dispose()
                }
            )
    }

    fun showDialogLoader(v: View) {
        v.loader_ll.visibility = View.VISIBLE
    }

    fun hideDialogLoader(v: View) {
        v.loader_ll.visibility = View.GONE
    }
}