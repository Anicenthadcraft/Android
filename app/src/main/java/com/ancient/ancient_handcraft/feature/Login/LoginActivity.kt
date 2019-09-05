package com.ancient.ancient_handcraft.feature.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.base.Activity.Dashboard.DashboardActivity
import com.ancient.ancient_handcraft.feature.ForgotPassword.ForgotPasswordActivity
import com.ancient.ancient_handcraft.feature.SignUp.SignUpActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class LoginActivity : AppCompatActivity(),LoginContract.View, View.OnClickListener {

    lateinit var loginPresenter : LoginContract.Presenter
    private var context: Context?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        setPresenter(LoginPresenter(this))
        loginPresenter.start()
        initView()
    }

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
                moveToDashboard()
                return
            }
        }
    }

    fun loader_inteface_onclick(v: View?) {
        v!!.setOnClickListener {  }
    }

    private fun moveToDashboard() {
        loginPresenter.validateLoginInfo(login_email.text.toString(),login_password.text.toString())
    }

    fun SearchPanelVisibility(){
        searchbar_rl.visibility = View.VISIBLE
    }

    fun SearchPanelInVisibility(){
        searchbar_rl.visibility = View.GONE
    }


    override fun openDashboard() {
        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        AppUtils.showToastMsg(this,"Move to Dashboard")
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoader() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addDisposable(disposable: Disposable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}