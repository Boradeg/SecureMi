package com.GokulBorade.securemi.Fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface

import android.content.Intent
import android.content.pm.PackageManager

import android.os.Bundle

import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.GokulBorade.securemi.activities.AddTrustyNumberActivity
import com.GokulBorade.securemi.activities.MainActivity
import com.GokulBorade.securemi.activities.ViewRegisteredActivity
import com.GokulBorade.securemi.activities.latitudes
import com.GokulBorade.securemi.activities.longitudes
import com.GokulBorade.securemi.activities.message


import com.GokulBorade.securemi.dataClasses.UserTrustyDetailDataClass
import com.GokulBorade.securemi.databinding.FragmentHomeBinding


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shashank.sony.fancytoastlib.FancyToast

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    var sendSmsArrayLis = ArrayList<UserTrustyDetailDataClass>()
    private lateinit var database: DatabaseReference
    val notification = "notification send"

    @SuppressLint("UnlocalizedSms")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        //startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        binding.addFrnButton.setOnClickListener {
            startActivity(Intent(requireContext(), AddTrustyNumberActivity::class.java))
        }
        binding.viewfrBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ViewRegisteredActivity::class.java))
        }

        binding.sos2.setOnClickListener {
            FancyToast.makeText(
                requireContext(),
                "Message Send Successfully !",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,
                true
            ).show()
            sendSmsToUser()
        }


        return binding.root
    }


    private fun sendSmsToUser() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        try {
            getDataFromFirestoreAndSendMsg()
            //Toast.makeText(requireContext(), longitudes.toString(), Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun permissionAccess2() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        } else {

            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(Manifest.permission.SEND_SMS),
                101
            )
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun getDataFromFirestoreAndSendMsg() {
        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.email.toString())
            .get().addOnSuccessListener { result ->
                val smsManager = SmsManager.getDefault() as SmsManager

                for (res in result.documents) {
                    var question: UserTrustyDetailDataClass? =
                        res.toObject(UserTrustyDetailDataClass::class.java)//que come from databese 1 by 1
                    smsManager.sendTextMessage(
                        question!!.userNumber,
                        null,
                        message.toString(),
                        null,
                        null
                    )

                    val data = hashMapOf(
                        "userEmail" to FirebaseAuth.getInstance().currentUser!!.email.toString(),
                        "UserLocation" to message,
                        "Longitude" to longitudes,
                        "Latitudes" to latitudes

                    )


                }

            }
    }
}

