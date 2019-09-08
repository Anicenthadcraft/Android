package com.ancient.ancient_handcraft.feature.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.app.ActivityOptionsCompat
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.app.AppData
import com.ancient.ancient_handcraft.base.Activity.Dashboard.DashboardActivity
import com.ancient.ancient_handcraft.base.BaseActivity
import com.ancient.ancient_handcraft.feature.Login.LoginActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : BaseActivity(), SplashContract.View {

    private lateinit var mPresenter: SplashContract.Presenter
    private var context: Context? = null
    private var appData: AppData? = null

    override fun onCreated(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.splash_activity)
        context = this
        initView()
        setPresenter(SplashPresenter(this))
        mPresenter.start()

        //Toast.makeText(this,""+BuildConfig.BASE_URL, Toast.LENGTH_LONG).show();
    }

    private fun initView() {
        appData = AppData(context!!)
    }

    override fun setPresenter(presenter: SplashContract.Presenter) {
        mPresenter = presenter
    }

    override fun moveToNextActivity() {
        if (appData?.userSession?.isLoggedIn!!) {
            startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        } else {
            var compatTrans = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                splash_base_tv,
                "splashTrans"
            )
            startActivity(
                Intent(this@SplashActivity, LoginActivity::class.java),
                compatTrans.toBundle()
            )
        }
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    override fun showLoader() {
    }

    override fun hideLoader() {
    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun showMessage(msg: String) {
    }
}
