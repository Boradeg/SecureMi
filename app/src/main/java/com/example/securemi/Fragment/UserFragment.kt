package com.example.securemi.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.securemi.R
import com.example.securemi.activities.SignInActivity
import com.example.securemi.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
 lateinit var binding: FragmentUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding=FragmentUserBinding.inflate(layoutInflater)
             return binding.root
    }


}