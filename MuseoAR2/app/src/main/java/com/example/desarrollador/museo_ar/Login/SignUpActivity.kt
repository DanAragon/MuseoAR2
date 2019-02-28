package com.example.desarrollador.museo_ar.Login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.desarrollador.museo_ar.R
import com.google.firebase.auth.FirebaseAuth

import android.content.Intent
import com.example.desarrollador.museo_ar.Extension.goToActivity
import com.example.desarrollador.museo_ar.Extension.toast

import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonGoLogIn.setOnClickListener {
            goToActivity<LoginActivity>(){
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }

        buttonCreateUser.setOnClickListener {
            val email= editTextEmail2.text.toString()
            val password = editTextPassword2.text.toString()
            if(isValidEmailAndPassword(email,password)){
                signUpByEmail(email,password)
            }else{
                toast("make sure the password and email are correct")
            }
        }

    }

    private fun signUpByEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    toast("Se han enviado un email a tu correo, comfirmar antes de entrar a la aplicacion")

                } else {

                   toast("An un expected error ocurred, please try again.")
                }

            }
    }
    private fun isValidEmailAndPassword(email: String, password: String): Boolean {
        return !email.isNullOrEmpty()&& !password.isNullOrEmpty() &&
                password == editTextPasswordConfirm.text.toString()
    }

}
