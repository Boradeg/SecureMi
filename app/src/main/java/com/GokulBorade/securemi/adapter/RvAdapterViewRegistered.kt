package com.GokulBorade.securemi.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.GokulBorade.securemi.R

import com.GokulBorade.securemi.dataClasses.UserTrustyDetailDataClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RvAdapterViewRegistered(val requireContext: Context, private val phoneDetails: ArrayList<UserTrustyDetailDataClass>):
    RecyclerView.Adapter<RvAdapterViewRegistered.exampleViewHolder>() {
    class exampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personName: TextView = itemView.findViewById(R.id.EM_name)
        val personNumber: TextView = itemView.findViewById(R.id.EM_no)
        val deleteContact: ImageView = itemView.findViewById(R.id.deleteContact)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): exampleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_viewregistered, parent, false)
        return exampleViewHolder(view)
    }
    override fun getItemCount(): Int {
        return phoneDetails.size
    }

    override fun onBindViewHolder(holder: exampleViewHolder, position: Int) {
        val item = phoneDetails[position]
        holder.personName.text = phoneDetails[position].userName
        holder.personNumber.text = phoneDetails[position].userNumber
        holder.deleteContact.setOnClickListener {
            var db = Firebase.firestore
            db.collection(FirebaseAuth.getInstance().currentUser!!.email!!).document(phoneDetails[position].docId!!).delete().addOnSuccessListener {
                Toast.makeText(requireContext, "Delete successfully", Toast.LENGTH_SHORT).show()


            }.addOnFailureListener {
                Toast.makeText(requireContext, "Delete Unccessfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}