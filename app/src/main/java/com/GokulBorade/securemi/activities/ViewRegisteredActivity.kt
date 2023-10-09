package com.GokulBorade.securemi.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.GokulBorade.securemi.adapter.RvAdapterViewRegistered
import com.GokulBorade.securemi.dataClasses.UserTrustyDetailDataClass
import com.GokulBorade.securemi.databinding.ActivityViewRegisteredBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ViewRegisteredActivity : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var binding: ActivityViewRegisteredBinding
    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityViewRegisteredBinding.inflate(layoutInflater)
       // Toast.makeText(this, FirebaseAuth.getInstance().currentUser!!.email, Toast.LENGTH_SHORT).show()
        binding.arrowBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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
        Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.email!!).get().addOnSuccessListener { result ->

            val temp = ArrayList<UserTrustyDetailDataClass>()
            for (res in result.documents) {

                val question: UserTrustyDetailDataClass? = res.toObject(UserTrustyDetailDataClass::class.java)//que come from databese 1 by 1
                question!!.docId=res.id

               // Toast.makeText(this, res.id, Toast.LENGTH_SHORT).show()
                temp.add(question)
            }
            binding.rv.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            val rcvAdapter= RvAdapterViewRegistered(this, temp)
            binding.rv.adapter= RvAdapterViewRegistered(this, temp)
            rcvAdapter.notifyDataSetChanged()


        }
    }



}
