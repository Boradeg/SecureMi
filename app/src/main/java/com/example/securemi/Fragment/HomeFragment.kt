package com.example.securemi.Fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

           // permissionAccess()

        binding = FragmentHomeBinding.inflate(layoutInflater)
        // fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        //getCurrentLocations()
        binding.addFrBtn.setOnClickListener {
            startActivity(Intent(requireContext(), AddTrustyNumberActivity::class.java))
        }
        binding.viewfrBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ViewRegisteredActivity::class.java))
        }
            var flag=false
       binding.sos.setOnClickListener {
            flag = flag != true
           //binding.sos.playAnimation()
            if (!flag) {
                binding.sos.visibility = View.GONE
                binding.sos2.visibility=View.VISIBLE
               // binding.sos2.playAnimation()
//                binding.sos.loop(true)
                try {
                    permissionAccess2()
                   // permissionAccess()
                   // getDataFromFirestore()
                    sendSms()

                } catch (e: Exception) { Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show() }
            }
            //sos end
            else{
               // binding.sos.loop(false)
                binding.sos.visibility = View.VISIBLE
                binding.sos2.visibility=View.GONE
            }
        }

        // Inflate the layout for this fragment
        return binding.root

    }

    private fun sendSms() {
        try {
            var smsManager = SmsManager.getDefault() as SmsManager
            getDataFromFirestore()
            //   Toast.makeText(requireContext(), questionList[0].userNumber.toString(), Toast.LENGTH_SHORT).show()
            var num=0
            for (num in sendSmsArrayLis) {
                smsManager.sendTextMessage(num.userNumber.toString(), null, message.toString(), null, null)
            }
            Toast.makeText(requireContext(), "Message Sent SuccessFully", Toast.LENGTH_SHORT).show()
        } catch (e: java.lang.Exception) {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
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

    private fun permissionAccess() {
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {

            Toast.makeText(requireContext(), "permission granted for msg send", Toast.LENGTH_SHORT).show()
        }
        else
        {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.SEND_SMS),101)
        }
    }
    private fun getDataFromFirestore() {
        Firebase.firestore.collection(userEmail!!).get().addOnSuccessListener { result ->
            var temp = ArrayList<UserTrustyDetailDataClass>()
            temp.clear()
            sendSmsArrayLis.clear()
            for (res in result.documents) {

                var question: UserTrustyDetailDataClass? = res.toObject(UserTrustyDetailDataClass::class.java)//que come from databese 1 by 1
                temp.add(question!!)
              //  Toast.makeText(requireContext(),res.toString(), Toast.LENGTH_SHORT).show()

            }
            sendSmsArrayLis.addAll(temp)

            var data = UserTrustyDetailDataClass("$numb","$message")
//            for(i in sendSmsArrayLis) {

             //  var k=0
                Firebase.database.reference.child("USER")
                    .child(sendSmsArrayLis[0].userNumber.toString()).child("NOTIFICATION")
                    .child("n2")
                    .setValue(data)
                    .addOnSuccessListener { Toast.makeText(requireContext(), "notification node created", Toast.LENGTH_SHORT).show()
                    }
               // k++;
//            }
        }
    }
}
