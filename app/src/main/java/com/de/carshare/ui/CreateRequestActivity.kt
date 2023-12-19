package com.de.carshare.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import com.de.carshare.R
import com.de.carshare.databinding.ActivityCreateRequestBinding
import com.de.carshare.models.Request
import com.de.carshare.repositories.RequestRepository
import com.de.carshare.rvAdapter.RequestAdapter
import java.util.Date
import java.util.GregorianCalendar

class CreateRequestActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityCreateRequestBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var requestRepository: RequestRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRequestBinding.inflate(layoutInflater)
        prefs = applicationContext.getSharedPreferences(packageName, MODE_PRIVATE)
        requestRepository = RequestRepository(applicationContext)

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
            R.id.btn_create_new_request -> {
                if (validate(this.binding.etArrival) &&
                    validate(this.binding.etDeparture) &&
                    validate(this.binding.etLeaveDate)) {

                    val dep = this.binding.etDeparture.text.toString()
                    val arr = this.binding.etDeparture.text.toString()
                    // ...
                }
            }
        }
    }


}