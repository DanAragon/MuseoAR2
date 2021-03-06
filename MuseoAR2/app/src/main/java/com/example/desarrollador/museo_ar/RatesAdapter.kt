package com.example.desarrollador.museo_ar

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.desarrollador.museo_ar.Extension.inflate
import com.example.desarrollador.museo_ar.Models.Rate
import com.example.desarrollador.museo_ar.Utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_rates_items.view.*
import java.text.SimpleDateFormat

class RatesAdapter(private val items: List<Rate>) : RecyclerView.Adapter<RatesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(parent.inflate(R.layout.fragment_rates_items))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(rate: Rate) = with(itemView){
            textViewRate.text = rate.text
            textViewStar.text = "${rate.rate}"
            textViewCalendar.text = SimpleDateFormat("dd, MMM , yyy").format(rate.createdAt)
            if(rate.profileImgURL.isNullOrEmpty())
            {
                Picasso.get().load(R.drawable.avatar).resize(100, 100).centerCrop()
                    .transform(CircleTransform()).into(imageViewProfile)
            }
            else {
                Picasso.get().load(rate.profileImgURL).resize(100, 100).centerCrop()
                    .transform(CircleTransform()).into(imageViewProfile)
            }
        }
    }
}