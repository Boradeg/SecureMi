package com.example.securemi.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.securemi.R
import com.example.securemi.activities.SignInActivity
import com.example.securemi.activities.numb
import com.example.securemi.databinding.FragmentUserBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    private lateinit var db: DatabaseReference
    val myNumber:String?=null
    private lateinit var fusedLocationProviderClients: FusedLocationProviderClient
 lateinit var binding: FragmentUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentUserBinding.inflate(layoutInflater)
//        db = FirebaseDatabase.getInstance().getReference("Users")
//        db.child(numb).child("NOTIFICATION").get().addOnSuccessListener {
//            Toast.makeText(requireContext(), currentUserNumber, Toast.LENGTH_SHORT).show()
//            Toast.makeText(
//                requireContext(),
//                it.child("n2").child("userEmail").value.toString(),
//                Toast.LENGTH_SHORT
//            ).show()
//
//
//        }.addOnFailureListener {
//            Toast.makeText(requireContext(), "No Notification", Toast.LENGTH_SHORT).show()
//        }
        var k:String=""
        Toast.makeText(requireContext(), "numb is $numb", Toast.LENGTH_SHORT).show()
        db = FirebaseDatabase.getInstance().getReference("Users2")
        db.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
            numb=it.child("numb").value.toString()
            Toast.makeText(requireContext(),it.child("numb").value.toString(), Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(requireContext(),numb.toString(), Toast.LENGTH_SHORT).show()
        db = FirebaseDatabase.getInstance().getReference("Users")
        db.child(numb.toString()).child("NOTIFICATION").get().addOnSuccessListener {
            Toast.makeText(requireContext(), "enter", Toast.LENGTH_SHORT).show()
            binding.username.text=it.child("n2").child("userEmail").value.toString()
            binding.email.text=it.child("n2").child("UserLocation").value.toString()
        }
             return binding.root

    }




}