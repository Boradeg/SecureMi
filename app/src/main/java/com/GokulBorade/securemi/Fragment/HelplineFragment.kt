package com.GokulBorade.securemi.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.GokulBorade.securemi.R
import com.GokulBorade.securemi.adapter.RvAdapterHelpline
import com.GokulBorade.securemi.dataClasses.ModalHelplinerRV
import com.GokulBorade.securemi.databinding.FragmentHelplineBinding


class HelplineFragment : Fragment() {
    private lateinit var binding: FragmentHelplineBinding
    private lateinit var arrayList: ArrayList<ModalHelplinerRV>
    private lateinit var rvAdapterHelpline: RvAdapterHelpline

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHelplineBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        arrayList= ArrayList()
        arrayList.add(ModalHelplinerRV("108","Ambulance", R.drawable.ambulance))
        arrayList.add(ModalHelplinerRV("102","Pregnancy Medic",R.drawable.woman))
        arrayList.add(ModalHelplinerRV("101","Fire Service",R.drawable.firetruck))
        arrayList.add(ModalHelplinerRV("100","Police",R.drawable.policeman))
        arrayList.add(ModalHelplinerRV("1091","Woman Helpline",R.drawable.helplinegirl))
        arrayList.add(ModalHelplinerRV("1098","Child Helpline",R.drawable.helpline2))
        arrayList.add(ModalHelplinerRV("1073","Road Accident",R.drawable.accident))
        arrayList.add(ModalHelplinerRV("182","Railway Protection",R.drawable.trainstation))
        arrayList.add(ModalHelplinerRV("112","National helpline",R.drawable.helplinenational))
        binding.rv.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        rvAdapterHelpline= RvAdapterHelpline(requireContext(),arrayList)
        rvAdapterHelpline.setOnItemClickListener(object : RvAdapterHelpline.onItemClickListener {
            override fun onItemClick(position: Int) {
                val phoneNumber =arrayList[position].rvNum
                val callLogUri = Uri.parse("tel:$phoneNumber")
                val intent = Intent(Intent.ACTION_VIEW, CallLog.Calls.CONTENT_URI)
                intent.data = callLogUri
                startActivity(intent)
            }
        })
        binding.rv.adapter=rvAdapterHelpline
        return binding.root
    }


}