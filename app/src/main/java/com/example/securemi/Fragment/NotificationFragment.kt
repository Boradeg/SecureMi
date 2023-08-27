package com.example.securemi.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.securemi.activities.locationActivity
import com.example.securemi.activities.numb
import com.example.securemi.databinding.FragmentNotificationBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var db: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotificationBinding.inflate(layoutInflater)
        try {
            db = FirebaseDatabase.getInstance().getReference("USER")
            db.child(numb).child("NOTIFICATION").get().addOnSuccessListener {
                val notif = it.child("n2").child("userNumber").value.toString()
                binding.EMNo.text = it.child("n2").child("userName").value.toString()
                if (notif === "null") {
                    binding.viewLocation.visibility = View.GONE
                    binding.viewLocation2.visibility = View.VISIBLE
                } else {
                    binding.viewLocation.visibility = View.VISIBLE
                    binding.viewLocation2.visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "error unexpected", Toast.LENGTH_SHORT).show()
        }
        binding.viewLocation.setOnClickListener {
            startActivity(Intent(requireContext(), locationActivity::class.java))
        }

            return binding.root
    }

}
