package com.GokulBorade.securemi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.GokulBorade.securemi.Fragment.UserFragment
import com.GokulBorade.securemi.databinding.ActivityCallerDetailBinding


class callerDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCallerDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCallerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveBtn.setOnClickListener {
            if(binding.callerName.text.isNotEmpty()&&binding.callerNumber.text.isNotEmpty())
            {
                  var name=binding.callerName.text.toString()
                  var number=binding.callerNumber.text.toString()
                  var intent=Intent(this, CallActivity2::class.java)
                  intent.putExtra("name",name)
                  startActivity(intent)
//                val fragment=UserFragment()
//                val bundle=Bundle()
//                bundle.putString("name",name)
//                fragment.arguments=bundle
//                supportFragmentManager.beginTransaction().add(R.id.frameLayout,fragment).commit()
            }
            else{
                binding.callerName.error="Enter Valid Name"
                binding.callerNumber.error="Enter Valid Number"
            }
        }
    }
}