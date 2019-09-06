package com.ancient.ancient_handcraft.feature.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.app.PojoObj.Login.LoginPayloadpojo
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.RegisterPayloadPojo
import com.ancient.ancient_handcraft.base.Activity.Dashboard.DashboardActivity
import com.ancient.ancient_handcraft.base.BaseActivity
import com.ancient.ancient_handcraft.feature.ForgotPassword.ForgotPasswordActivity
import com.ancient.ancient_handcraft.feature.SignUp.SignUpActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.loader_layout.*
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.signup_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class LoginActivity : BaseActivity(), LoginContract.View, View.OnClickListener {

    lateinit var loginPresenter: LoginContract.Presenter
    private var context: Context? = null

    override fun onCreated(savedInstanceState: Bundle?) {
        setContentView(R.layout.login_activity)
        initView()
        setPresenter(LoginPresenter(this,context!!))
        loginPresenter.start()
    }

    val userMobile
        get() = mobile_no_edt.text.toString() ?: ""

    val password
        get() = password_edt.text.toString() ?: ""

    private fun initView() {
        context = this
        signup_tv.setOnClickListener(this)
        forgot_password_tv.setOnClickListener(this)
        login_btn.setOnClickListener(this)
        tv_header.text = "Login"
        SearchPanelInVisibility()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.signup_tv -> {
                openSignUp()
                return
            }
            R.id.forgot_password_tv -> {
                openForgotPassword()
                return
            }
            R.id.login_btn -> {
                InitiateLoginProcess()
                return
            }
        }
    }

    private fun InitiateLoginProcess() {
        if (validateInputs()) {
            if (AppUtils.isOnline(context!!)) {
                showLoader()
                loginPresenter.InitiateLoginProcess(
                    userMobile, password
                )
            } else
                showErrorMessage(context!!.resources.getString(R.string.no_internet_connection_check))
        }
    }

    fun validateInputs(): Boolean {
        var isValidate = false
        if (userMobile.isNullOrEmpty()) {
            AppUtils.showToastMsg(
                context!!,
                context!!.resources.getString(R.string.mobileno_blank_error)
            )
        } else if (!AppUtils.isValidateMobile(userMobile)) {
            AppUtils.showToastMsg(
                context!!,
                context!!.resources.getString(R.string.mobileno_valid_error)
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

    fun loader_inteface_onclick(v: View?) {
        v!!.setOnClickListener { }
    }

    private fun moveToDashboard() {
        //loginPresenter.validateLoginInfo(login_email.text.toString(),login_password.text.toString())
    }

    fun SearchPanelVisibility() {
        searchbar_rl.visibility = View.VISIBLE
    }

    fun SearchPanelInVisibility() {
        searchbar_rl.visibility = View.GONE
    }


    override fun openDashboard() {
        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun openSignUp() {
        startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun openForgotPassword() {
        startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun setPresenter(presenter: LoginContract.Presenter) {
        loginPresenter = presenter
    }

    override fun showLoader() {
        loader_ll.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        loader_ll.visibility = View.GONE
    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun showErrorMessage(msg: String) {
        AppUtils.showToastMsg(
            context!!,
            msg
        )
    }

}