package com.example.securemi.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.securemi.R
import com.example.securemi.activities.numb
import com.example.securemi.activities.questionList
import com.example.securemi.databinding.FragmentHomeBinding
import com.example.securemi.databinding.FragmentNotificationBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
               binding = FragmentNotificationBinding.inflate(layoutInflater)
        try {


            Toast.makeText(requireContext(), "started", Toast.LENGTH_SHORT).show()
            db = FirebaseDatabase.getInstance().getReference("USER")
            db.child(numb).child("NOTIFICATION").get().addOnSuccessListener {
                var notif = it.child("n2").value.toString()
                if (notif == null) {
                    Toast.makeText(requireContext(), "you have no notification", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    binding.dataReceiveEdit2.text = notif

                }
//                Toast.makeText(requireContext(), "ended", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception)
        {
            Toast.makeText(requireContext(), "error unexpected", Toast.LENGTH_SHORT).show()
        }
            // Inflate the layout for this fragment
            return binding.root
        }

        companion object {

        }
}
