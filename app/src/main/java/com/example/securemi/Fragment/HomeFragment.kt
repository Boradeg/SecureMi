package com.example.securemi.Fragment

import android.Manifest
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

    private lateinit var database: DatabaseReference
    val notification="notification send"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)
       // fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        //getCurrentLocations()
        binding.addFrBtn.setOnClickListener {
       startActivity(Intent(requireContext(),AddTrustyNumberActivity::class.java))
        }
        binding.viewfrBtn.setOnClickListener {
            startActivity(Intent(requireContext(),ViewRegisteredActivity::class.java))
        }

        binding.sos.setOnClickListener {
            //        binding.sendData.setOnClickListener {
         //   var data = binding.enterDataEdit.text.toString()
            var data = "hiii,i am in emergency "

            Firebase.database.reference.child("USER")
                .child(questionList.get(0).userNumber.toString()).child("NOTIFICATION").child("n2")
                .setValue(data)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "notification node created", Toast.LENGTH_SHORT)
                        .show()
                }
//
//            }
         try{

             getTrustyNumber()
         }
         catch(e:Exception)
         {
             Toast.makeText(requireContext(), "plz add family or freinds contact", Toast.LENGTH_SHORT).show()
         }
             sendSms()
           // sendNotification()
           // permissionAccess()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun sendNotification() {
//okay
//        Firebase.database.reference.child("USER")
//            .child(numb).child("NOTIFICATION")
//            .setValue(notification)
//            .addOnSuccessListener {
//                Toast.makeText(requireContext(), "notification node created", Toast.LENGTH_SHORT)
//                    .show()
//   Toast.makeText(
//                        requireContext(),
//                       "strted send not
    //                        i",
//                        Toast.LENGTH_SHORT
//                    )
//
//            var data = "Plz Help Me.location: https://maps.google.com/?q=$latitudes,$longitudes"
//            Firebase.database.reference.child("USER")
//                .child(questionList.get(0).userNumber.toString()).child("NOTIFICATION").child("n2")
//                .setValue(data)
//                .addOnSuccessListener {
//                    Toast.makeText(
//                        requireContext(),
//                        "ended noti send",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }

            }


//        var notification="yes notification arrived"
//        val map= mutableMapOf<String,String>()
//        map.put(NOTIFICATION,notification)
//        database.collection(userUid).document().set(map).addOnSuccessListener {
//            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
//            binding.trustyNumber.text.clear()
//            binding.trustyName.text.clear()
//        }.addOnFailureListener {
//            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
//
//        }




    private fun getTrustyNumber() {
        Firebase.firestore.collection(userUid).get().addOnSuccessListener { result ->
            var temp = ArrayList<UserTrustyDetailDataClass>()
            for (res in result.documents) {
                var question: UserTrustyDetailDataClass? =
                    res.toObject(UserTrustyDetailDataClass::class.java)//que come from databese 1 by 1
                temp.add(question!!)                                      //que add in array
            }
            questionList.addAll(temp)
        }
    }

    private fun permissionAccess() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(requireContext(),android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
            {

                sendSms()
            }
            else
            {

                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.SEND_SMS),101)
            }
        }
    }

    private fun sendSms() {

        try{

            var smsManager =SmsManager.getDefault() as SmsManager
         //   Toast.makeText(requireContext(), questionList[0].userNumber.toString(), Toast.LENGTH_SHORT).show()

                for(i in questionList)
                {

                    smsManager.sendTextMessage(i.userNumber.toString(),null,"Plz Help Me.location: https://maps.google.com/?q=$latitudes,$longitudes",null,null)
                }
            Toast.makeText(requireContext(), "Message Sent SuccessFully", Toast.LENGTH_SHORT).show()

        }
        catch (e:java.lang.Exception)
        {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()

        }
    }
    }
