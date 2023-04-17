package com.example.securemi.Fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission

import com.example.securemi.activities.AddTrustyNumberActivity

import com.example.securemi.activities.ViewRegisteredActivity
import com.example.securemi.activities.userTrustyNumber

import com.example.securemi.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)
        binding.addTrustyNum.setOnClickListener {
       startActivity(Intent(requireContext(),AddTrustyNumberActivity::class.java))
        }
        binding.viewRegistred.setOnClickListener {
            startActivity(Intent(requireContext(),ViewRegisteredActivity::class.java))
        }
        binding.sos.setOnClickListener {
            permissionAccess()
        }
        // Inflate the layout for this fragment
        return binding.root
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
//                var numbers=arrayOf("234292929","838383838","373773377")
//                for(i in numbers)
//                {
//                    smsManager.sendTextMessage(i,null,"hj",null,null)
//                }

            smsManager.sendTextMessage(userTrustyNumber,null,"hi",null,null)
            Toast.makeText(requireContext(), "Message Sent SuccessFully", Toast.LENGTH_SHORT).show()

        }
        catch (e:java.lang.Exception)
        {

            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    }
