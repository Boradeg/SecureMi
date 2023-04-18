package com.example.securemi.Fragment

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
import com.example.securemi.activities.*
import com.example.securemi.dataClasses.UserTrustyDetailDataClass

import com.example.securemi.databinding.FragmentHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

            getTrustyNumber()
            permissionAccess()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

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
                    smsManager.sendTextMessage(i.userNumber.toString(),null,"hi",null,null)
              //      Toast.makeText(requireContext(), "Message Sent SuccessFully", Toast.LENGTH_SHORT).show()
                }
            Toast.makeText(requireContext(), "Message Sent SuccessFully", Toast.LENGTH_SHORT).show()

            //                    smsManager.sendTextMessage(i.userNumber.toString(),null,"hi",null,null)
               // smsManager.sendTextMessage(questionList[0].userNumber.toString(),null,"hi",null,null)

           // Toast.makeText(requireContext(), questionList[0].userNumber.toString(), Toast.LENGTH_SHORT).show()


        }
        catch (e:java.lang.Exception)
        {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    }
