package com.example.securemi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.securemi.dataClasses.UserTrustyDetailDataClass
import com.example.securemi.databinding.ActivityViewRegisteredBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
//lateinit var questionList: ArrayList<UserTrustyDetailDataClass>


class ViewRegisteredActivity : AppCompatActivity() {
    private lateinit var db: DatabaseReference

    private lateinit var binding: ActivityViewRegisteredBinding
    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewRegisteredBinding.inflate(layoutInflater)
        setContentView(binding.root)
//binding.buttonLogout.text= message
        binding.showNumb.setOnClickListener {
            try {
                showData()
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "You are not added friend or family member",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

            for (r in questionList) {
                Toast.makeText(this, r.userNumber, Toast.LENGTH_SHORT).show()
            }

       // binding.buttonLogout.setOnClickListener {
            //Firebase.auth.currentUser = null
            // startActivity(Intent(this,SignInActivity::class.java).putExtra("null","null"))
       // }
//        binding.sendData.setOnClickListener {
//         //   var data = binding.enterDataEdit.text.toString()
//            var data = "hiii,i am in emergency "
//
//            Firebase.database.reference.child("USER")
//                .child(questionList.get(0).userNumber.toString()).child("NOTIFICATION").child("n2")
//                .setValue(data)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "notification node created", Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//            }
//        binding.receiveData.setOnClickListener {
//            Toast.makeText(this, "started", Toast.LENGTH_SHORT).show()
//
//            db=FirebaseDatabase.getInstance().getReference("USER")
//            db.child(numb).child("NOTIFICATION").get().addOnSuccessListener {
//                var notif = it.child("n2").value.toString()
//                binding.dataReceiveEdit.text=notif
//                Toast.makeText(this, notif.toString(), Toast.LENGTH_SHORT).show()
//            }
//
//        }
    }
    private fun showData() {

        Firebase.firestore.collection(userUid).get().addOnSuccessListener { result ->

            var temp = ArrayList<UserTrustyDetailDataClass>()
            for (res in result.documents) {
                var question: UserTrustyDetailDataClass? =
                    res.toObject(UserTrustyDetailDataClass::class.java)//que come from databese 1 by 1
                temp.add(question!!)                                      //que add in array
            }
            questionList.addAll(temp)
            binding.showNumb.text = questionList.get(0).userNumber.toString()
            binding.num2show.text = questionList.get(0).userName.toString()
            binding.num1show.text = questionList.get(0).userNumber.toString()

           // binding.showNotification.text= questionList.get(0).notification.toString()
        }
    }


}
