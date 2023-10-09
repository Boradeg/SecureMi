package com.GokulBorade.securemi.activities
import com.GokulBorade.securemi.R
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.GokulBorade.securemi.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.*
//
var longitudes=""
var latitudes=""
//var id=""
var userUid:String=""
var message:String = ""
//var questionList = ArrayList<UserTrustyDetailDataClass>()
class MainActivity : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        getUSerUid()   //for get number
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocations()
        permissionAccess2()
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController=navHostFragment!!.findNavController()
        val popupMenu= PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottomnavbarmenu)
        binding.bottomBar.setupWithNavController(popupMenu.menu,navController)
    }

    private fun readNotification() {
        db=FirebaseDatabase.getInstance().getReference("USER")
        db.child(numb).get().addOnSuccessListener {
            var notif = it.child("N2").value.toString()

       //     Toast.makeText(this, notif.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUSerUid() {
        db=FirebaseDatabase.getInstance().getReference("USER")
        db.child(numb).get().addOnSuccessListener {
            userUid = it.child("numb").value.toString()
          //  Toast.makeText(this, userUid.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.statusmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notif -> {
            }
            R.id.hom -> {


            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrentLocations()
    {
        if(checkPermissions())
        {
            if(locationEnableds())
            {
                //final latitude and longitude
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task->
                    val location: Location?=task.result
                    if(location==null)
                    {
                        Toast.makeText(this, "null location", Toast.LENGTH_SHORT).show()
                    }
                    else{
                       // Toast.makeText(this, "success location", Toast.LENGTH_SHORT).show()
                        longitudes = location.longitude.toString()
                      //  Toast.makeText(this, location.latitude.toString(), Toast.LENGTH_SHORT).show()
                        latitudes = location.latitude.toString()
                        message = "HELP ME !! IT's AN EMERGENCY!!\n\nPlease reach ASAP to the location below\n\nhttps://maps.google.com/?q=$latitudes,$longitudes"

                    }

                }
            }
            else
            {
                //setting open here
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        }
        else
        {
            //req permission here
            reqPermission()
        }


    }

    private fun reqPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION
            ,android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }
    private fun checkPermissions(): Boolean {
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== PERMISSION_REQUEST_ACCESS_LOCATION)
        {
            if(grantResults.isNotEmpty()&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocations()
            }
            else
            {

            }
        }
    }
    private fun permissionAccess2() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        } else {

            ActivityCompat.requestPermissions(
                this as Activity,
                arrayOf(Manifest.permission.SEND_SMS),
                101
            )
        }
    }

    private fun locationEnableds(): Boolean {
        val locationManager: LocationManager =getSystemService(Context.LOCATION_SERVICE)as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }


}