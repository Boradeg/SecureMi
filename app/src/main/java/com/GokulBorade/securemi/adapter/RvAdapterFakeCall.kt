package com.example.rvupdated

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.GokulBorade.securemi.R
import com.GokulBorade.securemi.dataClasses.ModalFakeCallRv


//step1-> inherit RecyclerView.Adapter then create exapleViewHolder in <>
//add constructor invocation and add this class in our class
//pass argument in rcvAdapter and implement member
class RvAdapterFakeCall(val requireContext: Context, private val phoneDetails: ArrayList<ModalFakeCallRv>)
    :RecyclerView.Adapter<RvAdapterFakeCall.exampleViewHolder>() {



    class exampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val personName: TextView = itemView.findViewById(R.id.personNameRvFakeCll)
        val personImage: ImageView = itemView.findViewById(R.id.personImageRvFakeCll)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): exampleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_fake_cll, parent, false)
        return exampleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return phoneDetails.size
    }


    override fun onBindViewHolder(holder: exampleViewHolder, position: Int) {
        var food=phoneDetails[position]
        holder.personName.text = phoneDetails[position].personNameFakeCall
        holder.personImage.setImageResource(phoneDetails[position].personImageFakeCall)

    }

}