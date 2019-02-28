package com.example.desarrollador.museo_ar.Login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.desarrollador.museo_ar.Extension.goToActivity
import com.example.desarrollador.museo_ar.Extension.toast
import com.example.desarrollador.museo_ar.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(mAuth.currentUser == null){
            toast("Nope")
        }else{
            toast("YEAH BOI")
            mAuth.signOut()
        }
        buttonLogIn.setOnClickListener{
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if(isValidEmailAndPassword(email,password)){
                logInByEmail(email,password)
            }
        }
        textViewForgotPassword.setOnClickListener{goToActivity<ForgotPassword_activity>()}
        buttonSignUp.setOnClickListener{goToActivity<SignUpActivity>()}
    }


    //Metodo para comprobar si el usuario se ha "loggeado" de forma correcta.
    private fun logInByEmail(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                toast("User is now logged in ")
            }else{
                toast("An unexpected error ocurred, please try again.")
            }
        }
    }

    //validar que el correo y la contraseña no sean nulas o vacias
    private fun isValidEmailAndPassword(email:String, password: String): Boolean {
        return !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }
}
