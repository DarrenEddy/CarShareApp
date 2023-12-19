package com.de.carshare.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.de.carshare.R
import com.de.carshare.databinding.ActivityCreateRequestBinding

class CreateRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCreateRequestBinding.inflate(layoutInflater)


        setContentView(this.binding.root)
    }

    private fun validate()
    {}


}