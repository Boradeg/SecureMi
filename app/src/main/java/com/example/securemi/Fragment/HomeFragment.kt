package com.example.securemi.Fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.location.Location
import android.location.LocationManager
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.Settings
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getSystemService
import com.example.securemi.activities.*
import com.example.securemi.dataClasses.UserTrustyDetailDataClass

import com.example.securemi.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    var sendSmsArrayLis = ArrayList<UserTrustyDetailDataClass>()
    private lateinit var database: DatabaseReference
    val notification="notification send"
    @SuppressLint("UnlocalizedSms")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        Toast.makeText(requireContext(), "Home Activity", Toast.LENGTH_SHORT).show()

        binding.addFrBtn.setOnClickListener {
            startActivity(Intent(requireContext(), AddTrustyNumberActivity::class.java))
        }
        binding.viewfrBtn.setOnClickListener {
            for (num in sendSmsArrayLis) {
                Toast.makeText(requireContext(), num.userNumber, Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(requireContext(), ViewRegisteredActivity::class.java))
        }
        //permissionAccess2()
        //var flag=false
        binding.sos.setOnClickListener {
            try {

                startActivity(Intent(requireContext(),MainActivity::class.java))
                permissionAccess2()
            }catch (e:Exception)
            {
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
            }
            try {
                val smsManager = SmsManager.getDefault() as SmsManager
                smsManager.sendTextMessage("87654", null, longitudes.toString(), null, null)
                Toast.makeText(requireContext(), longitudes.toString(), Toast.LENGTH_SHORT).show()
               //  sendSms()

            }catch (e:Exception)
            {
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
            }
//           val smsManager = SmsManager.getDefault() as SmsManager
//           Toast.makeText(requireContext(), message.toString(), Toast.LENGTH_SHORT).show()

            // smsManager.sendTextMessage("4444", null, message.toString(), null, null)
//           getDataFromFirestore()
//            flag = flag != true
//            if (!flag) {
//                binding.sos.visibility = View.GONE
//                binding.sos2.visibility=View.VISIBLE
//                try {
//                    permissionAccess2()
//                    sendSms()
//                } catch (e: Exception) { Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show() }
//            }
//            else{
//                binding.sos.visibility = View.VISIBLE
//                binding.sos2.visibility=View.GONE
//            }
        }

        return binding.root
    }
    @SuppressLint("UnlocalizedSms")
    private fun sendSms() {
        try {
            val smsManager = SmsManager.getDefault() as SmsManager
             getDataFromFirestore()
            for (num in sendSmsArrayLis) {
                smsManager.sendTextMessage(num.userNumber.toString(), null, message.toString(), null, null)
            }
            Toast.makeText(requireContext(), "Message Sent SuccessFully", Toast.LENGTH_SHORT).show()
        } catch (e: java.lang.Exception) {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    private fun permissionAccessFormsg() {
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {

            Toast.makeText(requireContext(), "permission granted for msg send", Toast.LENGTH_SHORT).show()
        }
        else
        {

            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.SEND_SMS),101)
        }
    }
    private fun permissionAccess2() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                requireContext(),
                "permission granted for msg send",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(requireContext(), "permission2", Toast.LENGTH_SHORT).show()

            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(Manifest.permission.SEND_SMS),
                101
            )
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun getDataFromFirestore() {
        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.email.toString())
            .get().addOnSuccessListener { result ->
                var temp = ArrayList<UserTrustyDetailDataClass>()
                val smsManager = SmsManager.getDefault() as SmsManager
                for (res in result.documents) {
                    var question: UserTrustyDetailDataClass? =
                        res.toObject(UserTrustyDetailDataClass::class.java)//que come from databese 1 by 1
                    temp.add(question!!)
                    smsManager.sendTextMessage(question.userNumber, null, message.toString(), null, null)
                    // Toast.makeText(requireContext(), message.toString(), Toast.LENGTH_SHORT).show()
                }
//            val smsManager = SmsManager.getDefault() as SmsManager
//            smsManager.sendTextMessage(temp[0].userNumber, null, message.toString(), null, null)
                sendSmsArrayLis.addAll(temp)

//            var data = UserTrustyDetailDataClass("$numb","$message")
//                Firebase.database.reference.child("USER")
//                    .child(sendSmsArrayLis[0].userNumber.toString()).child("NOTIFICATION")
//                    .child("n2")
//                    .setValue(data)
//                    .addOnSuccessListener { Toast.makeText(requireContext(), "notification node created", Toast.LENGTH_SHORT).show()
//                    }
                // }
            }
    }
}
