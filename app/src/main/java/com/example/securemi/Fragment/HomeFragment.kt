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
   // private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
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

            getTrustyNumber()
            permissionAccess()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

//    private fun getCurrentLocations() {
//        if(checkPermissions())
//        {
//            if(locationEnableds())
//            {
//                //final latitude and longitude
//                if (ActivityCompat.checkSelfPermission(
//                        requireContext(),
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        requireContext(),
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return
//                }
//                fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task->
//                    val location: Location?=task.result
//                    if(location==null)
//                    {
//                        Toast.makeText(requireContext(), "null location", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
//                        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
//                        binding.lati.text= location.latitude.toString()
//                        binding.longi.text= location.longitude.toString()
//                    }
//
//                }
//            }
//            else
//            {
//                //setting open here
//                Toast.makeText(requireContext(), "turn on location", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
//            }
//        }
//        else
//        {
//            //req permission here
//            reqPermission()
//        }
//
//    }

//    private fun reqPermission() {
//        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ,android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_ACCESS_LOCATION)
//    }
//
//    companion object{
//        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
//    }
//    private fun checkPermissions(): Boolean {
//        if(ActivityCompat.checkSelfPermission(requireContext(),
//                android.Manifest.permission.ACCESS_COARSE_LOCATION)
//            ==PackageManager.PERMISSION_GRANTED
//            &&
//            ActivityCompat.checkSelfPermission(requireContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
//        {
//            return true
//        }
//        return false
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode== PERMISSION_REQUEST_ACCESS_LOCATION)
//        {
//            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(requireContext(), "permission granted", Toast.LENGTH_SHORT).show()
//                getCurrentLocations()
//            }
//            else
//            {
//                Toast.makeText(requireContext(), "permission denied", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }
//
//    private fun locationEnableds(): Boolean {
//       // val locationManager:LocationManager=getSystemService(LOCATION_SERVICE)as LocationManager
//      //  val locationManager:LocationManager=getSystemService()as LocationManager
//
//        return locationManager.isProviderEnabled(
//            LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER)
//    }

    private fun getTrustyNumber() {
        Firebase.firestore.collection(numb).get().addOnSuccessListener { result ->
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
            Toast.makeText(requireContext(), questionList[0].userNumber.toString(), Toast.LENGTH_SHORT).show()

//                var numbers=arrayOf("234292929","838383838","373773377")
                for(i in questionList)
                {
                    smsManager.sendTextMessage(i.userNumber.toString(),null,"Plz Help Me.location: https://maps.google.com/?q=$latitudes,$longitudes",null,null)
              //      Toast.makeText(requireContext(), "Message Sent SuccessFully", Toast.LENGTH_SHORT).show()
                }
            Toast.makeText(requireContext(), "Message Sent SuccessFully", Toast.LENGTH_SHORT).show()

            //                    smsManager.sendTextMessage(i.userNumber.toString(),null,"hi",null,null)
               // smsManager.sendTextMessage(questionList[0].userNumber.toString(),null,"hi",null,null)

           // Toast.makeText(requireContext(), questionList[0].userNumber.toString(), Toast.LENGTH_SHORT).show()


        }
        catch (e:java.lang.Exception)
        {
            //
        // Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    }
