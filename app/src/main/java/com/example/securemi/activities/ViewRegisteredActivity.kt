package com.example.securemi.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.securemi.adapter.RvAdapterHelpline
import com.example.securemi.adapter.RvAdapterViewRegistered
import com.example.securemi.dataClasses.UserTrustyDetailDataClass
import com.example.securemi.databinding.ActivityViewRegisteredBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
//var arrayList2 = ArrayList<UserTrustyDetailDataClass>()

class ViewRegisteredActivity : AppCompatActivity() {
    private lateinit var db: DatabaseReference

    private lateinit var binding: ActivityViewRegisteredBinding
    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityViewRegisteredBinding.inflate(layoutInflater)
        binding.arrowBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        try{
            getDataFromFirestore()
        }catch(e:java.lang.Exception){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
        setContentView(binding.root)
    }



    @SuppressLint("NotifyDataSetChanged")
    private fun getDataFromFirestore() {
        Firebase.firestore.collection(userEmail!!).get().addOnSuccessListener { result ->

            var temp = ArrayList<UserTrustyDetailDataClass>()
            for (res in result.documents) {

                var question: UserTrustyDetailDataClass? = res.toObject(UserTrustyDetailDataClass::class.java)//que come from databese 1 by 1
                question!!.docId=res.id

                Toast.makeText(this, res.id, Toast.LENGTH_SHORT).show()
                temp.add(question!!)
            }
            binding.rv.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            var rcvAdapter= RvAdapterViewRegistered(this, temp)
            binding.rv.adapter= RvAdapterViewRegistered(this, temp)
            rcvAdapter.notifyDataSetChanged()


        }
    }



}
