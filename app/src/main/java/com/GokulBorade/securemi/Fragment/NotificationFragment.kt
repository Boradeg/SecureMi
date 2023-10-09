package com.GokulBorade.securemi.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.GokulBorade.securemi.activities.locationActivity
import com.GokulBorade.securemi.activities.numb
import com.GokulBorade.securemi.databinding.FragmentNotificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shashank.sony.fancytoastlib.FancyToast

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var db: DatabaseReference
    var msg:String=""

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(layoutInflater)
       // binding.layoutAlert.visibility=View.GONE
       // getSenderMsg()
//        binding.cardview.setOnClickListener {
//            var intent=Intent(requireContext(), locationActivity::class.java).putExtra("1","msg")
//            startActivity(intent)
//        }
        FancyToast.makeText(requireContext(),"You have no Message !",FancyToast.LENGTH_LONG,FancyToast.INFO,true).show()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun getSenderMsg() {

        Toast.makeText(requireContext(), "numb is $numb", Toast.LENGTH_SHORT).show()
        db = FirebaseDatabase.getInstance().getReference("Users2")
        db.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
            numb = it.child("numb").value.toString()
            Toast.makeText(requireContext(), it.child("numb").value.toString(), Toast.LENGTH_SHORT)
                .show()
        }
        Toast.makeText(requireContext(), numb.toString(), Toast.LENGTH_SHORT).show()
        db = FirebaseDatabase.getInstance().getReference("Users")
        db.child(numb.toString()).child("NOTIFICATION").get().addOnSuccessListener {
            Toast.makeText(requireContext(), "enter", Toast.LENGTH_SHORT).show()
            binding.senderEmail.text = it.child("n2").child("userEmail").value.toString()
           // binding.showSenderMsg.text = "message :" + it.child("n2").child("UserLocation").value.toString()
            var ltd = "message :" + it.child("n2").child("Latitudes").value.toString()
            var longi = "message :" + it.child("n2").child("Longitude").value.toString()
            msg="https://maps.google.com/?q=$longi,$ltd"

            binding.cardview.visibility=View.VISIBLE
            if(binding.senderEmail.text==null)
            {
                binding.cardview.visibility=View.GONE
            }
           // binding.showSenderLocation.text = "https://maps.google.com/?q=$longi,$ltd"
        }
    }

}
