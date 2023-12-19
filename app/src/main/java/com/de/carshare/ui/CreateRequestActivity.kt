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
import com.de.carshare.R
import com.de.carshare.databinding.ActivityCreateRequestBinding
import com.de.carshare.models.Request
import com.de.carshare.repositories.RequestRepository
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CreateRequestActivity : AppCompatActivity(), OnClickListener,OnFocusChangeListener{
    private val TAG = this.javaClass.canonicalName
    private lateinit var binding: ActivityCreateRequestBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var requestRepository: RequestRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRequestBinding.inflate(layoutInflater)
        prefs = applicationContext.getSharedPreferences(packageName, MODE_PRIVATE)
        requestRepository = RequestRepository(applicationContext)


        binding.btnOpenDate.setOnClickListener(this)
        binding.btnCreateNewRequest.setOnClickListener(this)
        binding.etDeparture.onFocusChangeListener = this
        binding.etArrival.onFocusChangeListener = this
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
                            val date = (this.binding.tvDate.text.toString())

                            val request = Request(departCity = dep.locality, arrivalCity = arr.locality, dep.getAddressLine(dep.maxAddressLineIndex),arr.getAddressLine(arr.maxAddressLineIndex),user,date,date)
                            requestRepository.addRequest(request)
                            goToMain()

                        }

                    }


                    // ...
                }
            }
        }
    }

    override fun onFocusChange(view: View?, isFocused: Boolean) {
        if(!isFocused)
        {
            when(view?.id){
                R.id.et_departure -> {
                    if (binding.etDeparture.text.isNotEmpty()){

                        val address =getAddress(binding.etDeparture.text.toString())
                        if (address == null)
                        {
                            binding.etDeparture.setError("Cant Find Address")
                        }
                        else {
                            this.binding.etDeparture.setText(address.getAddressLine(address.maxAddressLineIndex))
                        }
                    }
                    // do nothing on empty
                }
                R.id.et_arrival -> {
                    if (binding.etArrival.text.isNotEmpty()){

                        val address =getAddress(binding.etArrival.text.toString())
                        if (address == null)
                        {
                            binding.etArrival.setError("Cant Find Address")
                        }
                        else {
                            this.binding.etArrival.setText(address.getAddressLine(address.maxAddressLineIndex))
                        }
                    }
                    // do nothing on empty
                }
            }
        }

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