package com.ancient.ancient_handcraft.feature.SignUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.RegisterPayloadPojo
import com.ancient.ancient_handcraft.base.BaseActivity
import com.ancient.ancient_handcraft.feature.Login.LoginActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.signup_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import android.text.InputFilter
import com.ancient.ancient_handcraft.base.Activity.Dashboard.DashboardActivity
import com.ancient.ancient_handcraft.feature.VerifyOTP.VerifyOTPDialog
import com.ancient.ancient_handcraft.feature.VerifyOTP.VerifyOTPDialogClickListener
import kotlinx.android.synthetic.main.loader_layout.*


class SignUpActivity : BaseActivity(), SignUpContract.View, View.OnClickListener,
    VerifyOTPDialogClickListener {


    private lateinit var signUpPresenter: SignUpContract.Presenter
    private var context: Context? = null
    val maxLengthofEditText = 10

    val firstName
        get() = first_name_edt.text.toString() ?: ""

    val lastName
        get() = last_name_edt.text.toString() ?: ""

    val mobileno
        get() = mobile_edt.text.toString() ?: ""

    val emailId
        get() = emailid_edt.text.toString() ?: ""

    val password
        get() = password_edt.text.toString() ?: ""

    private val verifyOtpDialog by lazy {
        VerifyOTPDialog.getInstance(
            context!!,
            context!!.resources.getString(R.string.verify_otp_header),
            "",
            this
        )
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        setContentView(R.layout.signup_activity)
        initView()
        setPresenter(SignUpPresenter(this, context!!))
        signUpPresenter.start()
    }

    private fun initView() {
        context = this

        signup_btn.setOnClickListener(this)
        tv_header.text = "SignUp"
        SearchPanelInVisibility()

        mobile_edt.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(maxLengthofEditText)))

        login_tv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.signup_btn -> {
                //verifyOtpDialog.show((context as SignUpActivity).supportFragmentManager, "verifyOtpDialog")
                signUpProcess()
                return
            }
            R.id.login_tv -> {
                moveToLogin()
                return
            }
        }
    }

    fun loader_inteface_onclick(v: View?) {
        v!!.setOnClickListener { }
    }

    private fun moveToLogin() {
        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    fun validateInputs(): Boolean {
        var isValidate = false
        if (firstName.isNullOrEmpty()) {
            AppUtils.showToastMsg(
                context!!,
                context!!.resources.getString(R.string.firstname_blank_error)
            )
        } else if (lastName.isNullOrEmpty()) {
            AppUtils.showToastMsg(
                context!!,
                context!!.resources.getString(R.string.lastname_blank_error)
            )
        } else if (mobileno.isNullOrEmpty()) {
            AppUtils.showToastMsg(
                context!!,
                context!!.resources.getString(R.string.mobileno_blank_error)
            )
        } else if (!AppUtils.isValidateMobile(mobileno)) {
            AppUtils.showToastMsg(
                context!!,
                context!!.resources.getString(R.string.mobileno_valid_error)
            )
        } else if (emailId.isNullOrEmpty()) {
            AppUtils.showToastMsg(
                context!!,
                context!!.resources.getString(R.string.email_blank_error)
            )
        } else if (!AppUtils.isValidEmail(emailId)) {
            AppUtils.showToastMsg(
                context!!,
                context!!.resources.getString(R.string.email_valid_error)
            )
        } else if (password.isNullOrEmpty()) {
            AppUtils.showToastMsg(
                context!!,
                context!!.resources.getString(R.string.password_blank_error)
            )
        } else {
            AppUtils.showToastMsg(
                context!!,
                context!!.resources.getString(R.string.success)
            )
            isValidate = true
        }
        return isValidate
    }

    private fun signUpProcess() {
        if (validateInputs()) {
            if (AppUtils.isOnline(context!!)) {
                showLoader()
                signUpPresenter.InitiateSignUpProcess(
                    RegisterPayloadPojo(
                        firstName = firstName,
                        lastName = lastName,
                        mobileNo = mobileno,
                        email = emailId,
                        isAdmin = false,
                        password = password
                    )
                )
            } else
                showErrorMessage(context!!.resources.getString(R.string.no_internet_connection_check))
        }
    }

    override fun openDashboard() {
        startActivity(Intent(this@SignUpActivity, DashboardActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    override fun setPresenter(presenter: SignUpContract.Presenter) {
        signUpPresenter = presenter
    }

    override fun showLoader() {
        loader_ll.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        loader_ll.visibility = View.GONE
    }

    override fun showErrorMessage(msg: String) {
        AppUtils.showToastMsg(
            context!!,
            msg
        )
    }

    override fun openOtpVerification() {

    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
        verifyOtpDialog.disposable = disposable
    }

    fun SearchPanelVisibility() {
        searchbar_rl.visibility = View.VISIBLE
    }

    fun SearchPanelInVisibility() {
        searchbar_rl.visibility = View.GONE
    }

    override fun onSubmitClick(otp: String) {
        showErrorMessage(otp)
    }

    override fun showDialogLoader() {
        loader_ll.visibility = View.VISIBLE
    }

    override fun hideDialogLoader() {
        loader_ll.visibility = View.GONE
    }

    override fun onVerifySuccess() {
        openDashboard()
    }

    override fun onVerifyFailure() {

    }


}