package com.example.securemi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.securemi.dataClasses.UserTrustyDetailDataClass
import com.example.securemi.databinding.ActivityViewRegisteredBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
//lateinit var questionList: ArrayList<UserTrustyDetailDataClass>


class ViewRegisteredActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewRegisteredBinding
    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewRegisteredBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.showNumb.setOnClickListener {
            showData()
        }


    }

    private fun showData() {

        Firebase.firestore.collection(numb).get().addOnSuccessListener { result ->
            var temp = ArrayList<UserTrustyDetailDataClass>()
            for (res in result.documents) {
                var question: UserTrustyDetailDataClass? =
                    res.toObject(UserTrustyDetailDataClass::class.java)//que come from databese 1 by 1
                temp.add(question!!)                                      //que add in array
            }
            questionList.addAll(temp)
//            for(i in questionList)
//            {
//                Toast.makeText(this, questionList.get(0).userNumber, Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, questionList.get(1).userNumber, Toast.LENGTH_SHORT).show()
//            }
            binding.showNumb.text = questionList.get(0).userNumber.toString()
            binding.num2show.text = questionList.get(0).userName.toString()
            binding.num1show.text = questionList.get(0).userNumber.toString()

        }
    }


}
