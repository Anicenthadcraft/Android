package com.ancient.ancient_handcraft.feature.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.ancient.ancient_handcraft.BuildConfig
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.Utils.Constants
import com.ancient.ancient_handcraft.base.BaseActivity
import com.ancient.ancient_handcraft.feature.Login.LoginActivity
import com.ancient.ancient_handcraft.feature.SignUp.SignUpActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : BaseActivity(),SplashContract.View {
    private lateinit var mPresenter: SplashContract.Presenter
    private var context: Context?=null

    override fun onCreated(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity)
        setPresenter(SplashPresenter(this))
        context = this
        mPresenter.start()

        //Toast.makeText(this,""+BuildConfig.BASE_URL, Toast.LENGTH_LONG).show();
    }

    override fun setPresenter(presenter: SplashContract.Presenter) {
        mPresenter = presenter
    }

    override fun openLoginActivity() {
        var compatTrans = ActivityOptionsCompat.makeSceneTransitionAnimation(this,splash_base_tv,"splashTrans")
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java),compatTrans.toBundle())
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        //finish()
    }



    override fun openDashboard() {

    }

    override fun showLoader() {
    }

    override fun hideLoader() {
    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
