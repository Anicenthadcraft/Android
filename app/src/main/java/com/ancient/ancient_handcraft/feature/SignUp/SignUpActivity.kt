package com.ancient.ancient_handcraft.feature.SignUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.RegisterPayloadPojo
import com.ancient.ancient_handcraft.base.BaseActivity
import com.ancient.ancient_handcraft.feature.Login.LoginActivity
import com.ancient.ancient_handcraft.feature.splash.SplashPresenter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.signup_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import android.text.InputFilter



class SignUpActivity : BaseActivity(), SignUpContract.View, View.OnClickListener {

    private lateinit var signUpPresenter: SignUpContract.Presenter
    private var context: Context?=null
    val maxLengthofEditText = 10

    val firstName by lazy {
        first_name_edt.text.toString()
    }
    val lastName by lazy {
        last_name_edt.text.toString()
    }
    val mobileno by lazy {
        mobile_edt.text.toString()
    }
    val emailId by lazy {
        emailid_edt.text.toString()
    }
    val password by lazy {
        password_edt.text.toString()
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        setContentView(R.layout.signup_activity)
        initView()
        setPresenter(SignUpPresenter(this))
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
                signUpProcess()
                return
            }
            R.id.login_tv -> {
                moveToLogin()
                return
            }
        }
    }

    private fun moveToLogin() {
        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    fun validateInputs(): Boolean {
        var isValidate = true
        if(first_name_edt.text.toString().isNullOrEmpty()){
            AppUtils.showToastMsg(context!!,context!!.resources.getString(R.string.firstname_blank_error))
        }
        else if (last_name_edt.text.toString().isNullOrEmpty()){
            AppUtils.showToastMsg(context!!,context!!.resources.getString(R.string.lastname_blank_error))
        }else if(mobile_edt.text.toString().isNullOrEmpty()){
            AppUtils.showToastMsg(context!!,context!!.resources.getString(R.string.mobileno_blank_error))
        }else if(emailid_edt.text.toString().isNullOrEmpty()){
            AppUtils.showToastMsg(context!!,context!!.resources.getString(R.string.email_blank_error))
        }else if(password_edt.text.toString().isNullOrEmpty()){
            AppUtils.showToastMsg(context!!,context!!.resources.getString(R.string.password_blank_error))
        }else{
            isValidate = false
        }
        return isValidate
    }

    private fun signUpProcess() {
        if(validateInputs()){
            signUpPresenter.InitiateSignUpProcess(RegisterPayloadPojo(firstName=firstName,lastName = lastName,mobileNo = mobileno,email = emailId,isAdmin = false,password = "User123"))
        }

    }

    override fun openDashboard() {
    }

    override fun setPresenter(presenter: SignUpContract.Presenter) {
        signUpPresenter = presenter
    }

    override fun showLoader() {
    }

    override fun hideLoader() {
    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun SearchPanelVisibility(){
        searchbar_rl.visibility = View.VISIBLE
    }

    fun SearchPanelInVisibility(){
        searchbar_rl.visibility = View.GONE
    }

}