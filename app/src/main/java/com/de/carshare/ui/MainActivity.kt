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
import com.de.carshare.ui.LoginActivity
import com.de.carshare.R
import com.de.carshare.databinding.ActivityMainBinding
import com.de.carshare.models.City
import com.de.carshare.models.Request
import com.de.carshare.models.User

import com.de.carshare.repositories.RequestRepository
import com.de.carshare.rvAdapter.RequestAdapter
import java.util.Calendar
import java.util.GregorianCalendar

val cities = mutableListOf<City>(
    City("Toronto","on",0.0,0.0),
    City("Ottawa","on",0.0,0.0),
    City("Montreal","qb",0.0,0.0),
    City("Quebec","qb",0.0,0.0)
    )

val users = mutableListOf<User>(
    User("Darren","darreneddy0@gmail.com","416-904-9688","password","on","12F34G"),
    User("Ken","kena@gmail.com","416-905-9688","password","on","12F34G"),
    User("Jade","jader@gmail.com","416-905-9688","password","on","12F34G"),
    User("Ryan","RiGuy@gmail.com","416-905-9688","password","on","12F34G"),
    User("Julius","Jcesar@gmail.com","416-905-9688","password","on","12F34G")
)

val requests = mutableListOf<Request>(
    Request(cities[0].id, cities[1].id, users[0].id, GregorianCalendar(2024,Calendar.JANUARY,1),GregorianCalendar(2024,Calendar.JANUARY,1)),
    Request(cities[0].id, cities[2].id, users[0].id, GregorianCalendar(2023,Calendar.DECEMBER,28),GregorianCalendar(2024,Calendar.DECEMBER,28)),
    Request(cities[0].id, cities[3].id, users[4].id, GregorianCalendar(2024,Calendar.FEBRUARY,2),GregorianCalendar(2024,Calendar.FEBRUARY,3))
)


class MainActivity : AppCompatActivity(),OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var requestRepository:RequestRepository
    private lateinit var requestAdapter:RequestAdapter
    private lateinit var prefs:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestRepository = RequestRepository(applicationContext)
        prefs = applicationContext.getSharedPreferences(packageName, MODE_PRIVATE)

        //inflate hard code with more dummy data
        requests[1].riders.add(users[1].id)
        requests[1].riders.add(users[3].id)
        requests[0].riders.add(users[2].id)
        requests[2].stops.add(cities[2].id)

        // init
        this.binding.menuToolbar.title = "4Rent"
        this.requestAdapter = RequestAdapter(requests)



        // adapter
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
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
        updateMenu()

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
                Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
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
                val intent = Intent(this, CreateUserActivity::class.java)
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