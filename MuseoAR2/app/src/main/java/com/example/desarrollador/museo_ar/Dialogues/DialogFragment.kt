package com.example.desarrollador.museo_ar.Dialogues

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.example.desarrollador.museo_ar.Extension.toast
import com.example.desarrollador.museo_ar.Models.NewRateEvent
import com.example.desarrollador.museo_ar.Models.Rate
import com.example.desarrollador.museo_ar.R
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import com.example.desarrollador.museo_ar.Utils.RxBus
import kotlinx.android.synthetic.main.dialog_rate.view.*

class RateDialog : DialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle? ): Dialog {

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_rate, null)

        return  AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(R.string.dialog_ok)){ _, _ ->
                val textRate = view.editTextRateFeedback.text.toString()
                if(textRate.isNotEmpty()) {
                    val imgURL = FirebaseAuth.getInstance().currentUser!!.photoUrl?.toString() ?: run { "" }
                    val rate = Rate(textRate, view.ratingBarFeedback.rating, Date(), imgURL)
                    RxBus.publish(NewRateEvent(rate))
                }
                else
                {
                    activity!!.toast("fak u, boi")
                }
            }
            .setNegativeButton(getString(R.string.dialog_cancel)) { _, _ ->
                activity!!.toast("Pressed Cancel")
            }
            .create()
    }
}