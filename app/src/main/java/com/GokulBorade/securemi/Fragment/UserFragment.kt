package com.GokulBorade.securemi.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.GokulBorade.securemi.R
import com.example.rvupdated.RvAdapterFakeCall
import com.GokulBorade.securemi.activities.AddTrustyNumberActivity
import com.GokulBorade.securemi.activities.CallActivity2
import com.GokulBorade.securemi.activities.callActivity1
import com.GokulBorade.securemi.activities.callerDetailActivity
import com.GokulBorade.securemi.dataClasses.ModalFakeCallRv
import com.GokulBorade.securemi.databinding.FragmentUserBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.database.DatabaseReference


class UserFragment : Fragment() {
    private lateinit var db: DatabaseReference
    val myNumber:String?=null
    private lateinit var fusedLocationProviderClients: FusedLocationProviderClient
 lateinit var binding: FragmentUserBinding
    private lateinit var arrayList: ArrayList<ModalFakeCallRv>
    private lateinit var rvAdapterFakeCall: RvAdapterFakeCall
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentUserBinding.inflate(layoutInflater)
         //setDataFromFirebase()
        arrayList= ArrayList()
        arrayList.add(ModalFakeCallRv("Brother", R.drawable.brother))
        arrayList.add(ModalFakeCallRv("Dad",R.drawable.dad))
        arrayList.add(ModalFakeCallRv("Mother",R.drawable.mother))
        arrayList.add(ModalFakeCallRv("Sister",R.drawable.rimzim))
        arrayList.add(ModalFakeCallRv("Husband",R.drawable.husband))
        arrayList.add(ModalFakeCallRv("Ravi",R.drawable.ravi))
        arrayList.add(ModalFakeCallRv("Dhanraj",R.drawable.man_profile))
        arrayList.add(ModalFakeCallRv("Punam",R.drawable.punam))
        arrayList.add(ModalFakeCallRv("Rimzim",R.drawable.rimzim))
        binding.rvFakeCall.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        rvAdapterFakeCall= RvAdapterFakeCall(requireContext(),arrayList)
        binding.rvFakeCall.adapter=rvAdapterFakeCall

        binding.callBtn.setOnClickListener {
            startActivity(Intent(requireContext(), CallActivity2::class.java))
        }
        binding.editIcon.setOnClickListener {
            startActivity(Intent(requireContext(), callerDetailActivity::class.java))
        }
        //binding.callerName2.setText(arguments?.getString("name"))
        return binding.root

    }




}