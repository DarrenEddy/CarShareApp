package com.de.carshare.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.core.view.updateLayoutParams
import com.de.carshare.R
import com.de.carshare.databinding.ActivityCreateRequestBinding
import com.de.carshare.models.Request
import com.de.carshare.repositories.RequestRepository
import java.text.SimpleDateFormat

import java.util.Calendar

import java.util.Locale

class CreateRequestActivity : AppCompatActivity(), OnClickListener,OnFocusChangeListener{
    private val TAG = this.javaClass.canonicalName
    private lateinit var binding: ActivityCreateRequestBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var requestRepository: RequestRepository
    private var currentStop = 0
    private lateinit var stops: List<EditText>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRequestBinding.inflate(layoutInflater)
        prefs = applicationContext.getSharedPreferences(packageName, MODE_PRIVATE)
        requestRepository = RequestRepository(applicationContext)

        stops = listOf(this.binding.etFirstStop,this.binding.etSecondStop)
        binding.btnOpenDate.setOnClickListener(this)
        binding.btnCreateNewRequest.setOnClickListener(this)
        binding.btnAddStop.setOnClickListener(this)
        binding.btnRemoveStop.setOnClickListener(this)
        binding.etDeparture.onFocusChangeListener = this
        binding.etArrival.onFocusChangeListener = this
        binding.etFirstStop.onFocusChangeListener = this
        binding.etSecondStop.onFocusChangeListener = this
        setContentView(this.binding.root)
    }

    private fun validate(et: EditText): Boolean {
        if (et.text.isNullOrEmpty()) {
            et.error = "Cannot Be Empty"
            return false
        }
        return true
    }

    private fun getUser():String
    {
        var result = ""

        if (prefs.contains("USER_EMAIL"))
        {
            result = prefs.getString("USER_EMAIL","").toString()
        }
        return result
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_open_date -> {
                val initialDate = Calendar.getInstance()
                val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    val date = Calendar.getInstance()
                    date.set(year,month,day,0,0)

                    this.binding.tvDate.text = SimpleDateFormat("EEE, d MMM YYYY", Locale.getDefault()).format(date.time)

                },initialDate.get(Calendar.YEAR),initialDate.get(Calendar.MONTH),initialDate.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.show()
            }

            R.id.btnRemoveStop ->
                {
                    if (currentStop>=0)
                    {
                        removeStop()

                    }
                }

            R.id.btnAddStop -> {


                if (currentStop < stops.size)
                {
                    addStop()
                }

            }


            R.id.btn_create_new_request -> {
                if (validate(this.binding.etArrival) &&
                    validate(this.binding.etDeparture)) {

                    val dep = getAddress(this.binding.etDeparture.text.toString())
                    val arr = getAddress(this.binding.etArrival.text.toString())

                    if (dep == null || arr == null || this.binding.tvDate.text.isNullOrEmpty())
                    {
                        if (dep == null) this.binding.etDeparture.setError("Cannot Find Address")
                        if (arr == null) this.binding.etArrival.setError("Cannot Find Address")
                        if (this.binding.tvDate.text.isNullOrEmpty()) this.binding.tvDate.setError("Need To Select A Date")
                    }
                    else {
                        val  user =  getUser()
                        if (user == ""){
                            Toast.makeText(this, "Not Logged In", Toast.LENGTH_SHORT).show()
                            goToMain()
                        }
                        else {
                            // get stops, upto not including currentStop
                            val stops = mutableListOf<String>()
                            val stopsLocality = mutableListOf<String>()

                            var stopsValid = true
                            for (stopIndex in 0..currentStop-1)
                            {
                                if (this.stops[stopIndex].text.isNullOrEmpty()) {
                                    this.stops[stopIndex].setError("Cannot Be Empty")
                                    stopsValid = false
                                }else{
                                    val stopAddress = getAddress(this.stops[stopIndex].text.toString())
                                    if (stopAddress == null)
                                    {
                                        stopsValid = false

                                    }
                                    else
                                    {
                                        stops.add(stopAddress.getAddressLine(stopAddress.maxAddressLineIndex))
                                        stopsLocality.add(stopAddress.locality)
                                    }

                                }
                            }

                            if (stopsValid){
                            val date = (this.binding.tvDate.text.toString())

                            val request = Request(departCity = dep.locality,
                                arrivalCity = arr.locality,
                                stops = stops,
                                stopsLocality = stopsLocality,
                                departAddress = dep.getAddressLine(dep.maxAddressLineIndex),
                                arrivalAddress = arr.getAddressLine(arr.maxAddressLineIndex),
                                creator = user,
                                arrivalDate = date,
                                departDate = date)
                            requestRepository.addRequest(request)
                            goToMain()
                            }

                        }

                    }


                    // ...
                }
            }
        }
    }

    private fun addStop(){

        val stop = stops[currentStop]
        stop.visibility = (View.VISIBLE)
        this.binding.btnRemoveStop.visibility = View.VISIBLE
        

        this.binding.btnAddStop.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToBottom = stop.id
        }
        currentStop++
        if (currentStop == stops.size) {

            this.binding.btnAddStop.visibility = View.INVISIBLE
        }
    }

    private fun removeStop()
    {

        this.binding.btnAddStop.visibility = View.VISIBLE


        if (currentStop>1)
        {

            val stop = stops[currentStop-1]
            stop.visibility = View.GONE

            this.binding.btnAddStop.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToBottom = stops[currentStop-2].id
            }
        }
        else if (currentStop == 1)
        {
            val stop = stops[currentStop-1]
            stop.visibility = View.GONE
            this.binding.btnRemoveStop.visibility = View.INVISIBLE
            this.binding.btnAddStop.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToBottom = binding.etDeparture.id
            }
        }
        currentStop--
        if (currentStop < 0) currentStop = 0


    }

    override fun onFocusChange(view: View?, isFocused: Boolean) {
        if(!isFocused)
        {
            when(view?.id){
                R.id.et_departure -> {
                        updateAddress(binding.etDeparture)
                }
                R.id.et_arrival -> {
                    updateAddress(binding.etArrival)
                }
                R.id.etFirstStop->
                    {
                        updateAddress(binding.etFirstStop)
                    }
                R.id.etSecondStop->{
                    updateAddress(binding.etSecondStop)
                }
            }
        }

    }
    private fun updateAddress(etAddress:EditText)
    {
        if (etAddress.text.isNotEmpty()){

            val address =getAddress(etAddress.text.toString())
            if (address == null)
            {
                etAddress.setError("Cant Find Address")
            }
            else {
                etAddress.setText(address.getAddressLine(address.maxAddressLineIndex))
            }
        }
        // do nothing on empty
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun getAddress(address:String):Address?
    {
        val geocoder: Geocoder = Geocoder(applicationContext, Locale.getDefault())

        try {
            val searchResults:MutableList<Address>? = geocoder.getFromLocationName(address, 1)
            if (searchResults == null) {
                Toast.makeText(this, "Error Searching", Toast.LENGTH_SHORT).show()
                return null
            }
            if (searchResults.size == 0) {
                Toast.makeText(this, "No Address Found for $address", Toast.LENGTH_SHORT).show()
                return null

            } else {
                return searchResults[0]
            }
        } catch(ex:Exception) {
            Log.e(TAG, "Error encountered while getting coordinate location.")
        }
        return null
    }


}