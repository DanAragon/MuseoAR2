package com.example.desarrollador.museo_ar.Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.desarrollador.museo_ar.Extension.goToActivity
import com.example.desarrollador.museo_ar.R
import kotlinx.android.synthetic.main.activity_forgot_password_activity.*

class ForgotPassword_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_activity)

        buttonGoLogIn2.setOnClickListener{
            goToActivity<LoginActivity>(){
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }
}
