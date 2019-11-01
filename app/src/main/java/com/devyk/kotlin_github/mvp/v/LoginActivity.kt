package com.devyk.kotlin_github.mvp.v

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.bennyhuo.common.ext.hideSoftInput
import com.bennyhuo.mvp.impl.BaseActivity
import com.devyk.common.ext.otherwise
import com.devyk.common.ext.yes
import com.devyk.kotlin_github.R
import com.devyk.kotlin_github.mvp.p.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar_simple.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.sdk15.coroutines.onClick
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity<LoginPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //绑定 Toolbar
        setSupportActionBar(toolbar)

        //调用 anko 扩展
        signInButton.onClick {
            onLogin()
        }


    }

   fun onLogin(){
        p.checkUser(username.text.toString().trim(),password.text.toString().trim())
            .yes {
                hideSoftInput()
                p.doLogin(username.text.toString().trim(),password.text.toString().trim())
            }.otherwise {
                showTips(password,"用户名或者密码不合法")
            }
    }


    private fun showTips(view: EditText, tips: String){
        view.requestFocus()
        view.error = tips
    }

    override fun onRequest() {
        toast("开始登录")
        showProgress(true)

    }

    override fun onSuccess() {
        toast("登录成功")
        showProgress(false)
    }

    override fun onError(error: Throwable) {
        toast("登录失败:${error.message}")
        showProgress(false)
    }

    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
        loginForm.animate().setDuration(shortAnimTime.toLong()).alpha(
            (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                loginForm.visibility = if (show) View.GONE else View.VISIBLE
            }
        })

        loginProgress.animate().setDuration(shortAnimTime.toLong()).alpha(
            (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                loginProgress.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }
}
