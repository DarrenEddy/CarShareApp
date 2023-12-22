package com.de.carshare.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.de.carshare.R
import com.de.carshare.databinding.ActivityMainBinding
import com.de.carshare.models.Request

import com.de.carshare.repositories.RequestRepository
import com.de.carshare.rvAdapter.RequestAdapter

class MainActivity : AppCompatActivity(),OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var requestRepository:RequestRepository
    private lateinit var requestAdapter:RequestAdapter
    private lateinit var prefs:SharedPreferences
    private lateinit var requestList: ArrayList<Request>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestRepository = RequestRepository(applicationContext)
        prefs = applicationContext.getSharedPreferences(packageName, MODE_PRIVATE)

        // init
        this.binding.menuToolbar.title = "4Rent"




        // adapter
        this.requestList = ArrayList()
        this.requestAdapter = RequestAdapter(requestList)

        binding.rvRequests.adapter = requestAdapter
        this.binding.rvRequests.layoutManager = LinearLayoutManager(this)
        binding.rvRequests.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        setSupportActionBar(this.binding.menuToolbar)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        updateMenu()

        requestRepository.getAllRequests()
        requestRepository.allRequest.observe(this,androidx.lifecycle.Observer { requests ->
            if (requests != null){
                this.requestList.clear()
                this.requestList.addAll(requests)
                this.requestAdapter.notifyDataSetChanged()
            }
        })

    }


    private fun updateMenu()
    {
        val prefs = applicationContext.getSharedPreferences(packageName, MODE_PRIVATE)
        if (prefs.contains("USER_EMAIL"))
        {
            this.binding.menuToolbar.menu.clear()
            this.binding.menuToolbar.inflateMenu(R.menu.menu_options_user)
        }
        else
        {
            this.binding.menuToolbar.menu.clear()
            this.binding.menuToolbar.inflateMenu(R.menu.menu_options)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        updateMenu()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_item_login -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_item_logout -> {
                prefs.edit().remove("USER_EMAIL").commit()
                updateMenu()
                return true
            }
            R.id.menu_item_create_ride -> {
                val intent = Intent(this, CreateRequestActivity::class.java)
                startActivity(intent)
                return true
            }
            


            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
        }
    }
}