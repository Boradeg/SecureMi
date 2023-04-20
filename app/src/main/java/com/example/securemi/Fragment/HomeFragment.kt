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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)
       // fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        //getCurrentLocations()
        binding.addTrustyNum.setOnClickListener {
       startActivity(Intent(requireContext(),AddTrustyNumberActivity::class.java))
        }
        binding.viewRegistred.setOnClickListener {
            startActivity(Intent(requireContext(),ViewRegisteredActivity::class.java))
        }

        binding.sos.setOnClickListener {
         try{
             getTrustyNumber()
         }
         catch(e:Exception)
         {
             Toast.makeText(requireContext(), "plz add family or freinds contact", Toast.LENGTH_SHORT).show()
         }
             sendSms()
           // permissionAccess()
        }
        // Inflate the layout for this fragment
        return binding.root
    }


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
