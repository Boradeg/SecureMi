package com.example.securemi.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.securemi.R
import com.example.securemi.adapter.RvAdapterHelpline
import com.example.securemi.dataClasses.ModalHelplinerRV
import com.example.securemi.databinding.FragmentHelplineBinding
import com.example.securemi.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HelplineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HelplineFragment : Fragment() {
    private lateinit var binding: FragmentHelplineBinding
    private lateinit var arrayList: ArrayList<ModalHelplinerRV>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHelplineBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        arrayList= ArrayList()
        arrayList.add(ModalHelplinerRV("108","Ambulance",R.drawable.ambulance))
        arrayList.add(ModalHelplinerRV("102","Pregnancy Medic",R.drawable.woman))
        arrayList.add(ModalHelplinerRV("101","Fire Service",R.drawable.firetruck))
        arrayList.add(ModalHelplinerRV("100","Police",R.drawable.policeman))
        arrayList.add(ModalHelplinerRV("1091","Woman Helpline",R.drawable.helplinegirl))
        arrayList.add(ModalHelplinerRV("1098","Child Helpline",R.drawable.helpline2))
        arrayList.add(ModalHelplinerRV("1073","Road Accident",R.drawable.accident))
        arrayList.add(ModalHelplinerRV("182","Railway Protection",R.drawable.trainstation))
        arrayList.add(ModalHelplinerRV("112","National helpline",R.drawable.helplinenational))


        binding.rv.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        var rcvAdapter=RvAdapterHelpline(requireContext(),arrayList)
        binding.rv.adapter=RvAdapterHelpline(requireContext(),arrayList)
        return binding.root
    }

    companion object {

    }
}