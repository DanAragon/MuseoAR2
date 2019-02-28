package com.example.desarrollador.museo_ar.Extension

import android.app.Activity
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowId
import android.widget.ImageView
import android.widget.Toast

fun Activity.toast(message: CharSequence, duration: Int =
                   Toast.LENGTH_SHORT) = Toast.makeText(this,message,duration).show()

fun Activity.toast(resourceId: Int, duration: Int = Toast.LENGTH_SHORT)=
                    Toast.makeText(this, resourceId, duration).show()
fun ViewGroup.inflate(layoutId: Int) = LayoutInflater.from(context).inflate(layoutId,this,false)!!

//fun ImageView.loadByURL(url: String) = Picasso.get().load(url).into(this)

//fun ImageView.loadByResource(resource: Int) = Picasso.get().load(resource).fit().into(this)


inline fun <reified T : Activity> Activity.goToActivity(noinline init: Intent.() -> Unit = {}){
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}


//skereeeeeeeeeeeeeeeee