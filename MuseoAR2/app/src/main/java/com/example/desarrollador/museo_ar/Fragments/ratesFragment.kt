
package com.example.desarrollador.museo_ar.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desarrollador.museo_ar.Dialogues.RateDialog
import com.example.desarrollador.museo_ar.Extension.toast
import com.example.desarrollador.museo_ar.Models.NewRateEvent
import com.example.desarrollador.museo_ar.Models.Rate
import com.example.desarrollador.museo_ar.R
import com.example.desarrollador.museo_ar.RatesAdapter
import com.example.desarrollador.museo_ar.Utils.RxBus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_rates.view.*
import java.util.*
import java.util.EventListener

class ratesFragment : Fragment()
{//inicio de ratesFragment
private lateinit var _view: View

    private lateinit var adapter: RatesAdapter
    private val ratesList: ArrayList<Rate> = ArrayList()

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private  lateinit var ratesDBRef: CollectionReference

    private var ratesSubscription: ListenerRegistration? = null
    private lateinit var rateBusListener: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _view = inflater.inflate(R.layout.fragment_rates, container, false)

        setUpRatesDB()
        setUpCurrentUser()

        setUpRecyclerView()
        setUpFaV()

        subscribeToRatings()
        subscribeToNewRating()

        return _view
    }

    private fun setUpRatesDB()
    {
        ratesDBRef = store.collection("rates")
    }

    private fun setUpCurrentUser()
    {
        currentUser = mAuth.currentUser!!
    }

    private fun setUpRecyclerView()
    {
        val layoutManager = LinearLayoutManager(context)
        adapter = RatesAdapter(ratesList)

        _view.recyclerView.setHasFixedSize(true)
        _view.recyclerView.layoutManager = layoutManager
        _view.recyclerView.itemAnimator = DefaultItemAnimator()
        _view.recyclerView.adapter = adapter
    }

    private fun setUpFaV()
    {
        _view.favRating.setOnClickListener{ RateDialog().show(fragmentManager, "") }
    }

    private fun saveRates(rate: Rate)
    {
        val newRating = HashMap<String, Any>()
        newRating["text"] =  rate.text
        newRating["rate"] = rate.rate
        newRating["createdAt"] = rate.createdAt
        newRating["profileImgURL"] = rate.profileImgURL

        ratesDBRef.add(newRating).addOnCompleteListener{
            activity!!.toast("Rating Added.")
        }
            .addOnFailureListener{
                activity!!.toast("Error, Try Again.")
            }
    }

    private fun subscribeToRatings()
    {
        ratesDBRef
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener(object: EventListener,com.google.firebase.firestore.EventListener<QuerySnapshot>
            {
                override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                    exception?.let {
                        activity!!.toast("Exception!")
                        return
                    }
                    snapshot?.let {
                        ratesList.clear()
                        val rates = it.toObjects(Rate::class.java)
                        ratesList.addAll(rates)
                        adapter.notifyDataSetChanged()
                        _view.recyclerView.smoothScrollToPosition(0)

                    }
                }
            })
    }

    private fun subscribeToNewRating()
    {
        rateBusListener =   RxBus.listen(NewRateEvent::class.java).subscribe({
            saveRates(it.rate)
        })
    }

    override fun onDestroy() {
        rateBusListener.dispose()
        ratesSubscription?.remove()
        super.onDestroy()
    }
}//fin de ratesFragment