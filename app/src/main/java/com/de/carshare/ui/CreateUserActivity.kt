package com.de.carshare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.Toast
import com.de.carshare.R
import com.de.carshare.databinding.ActivityCreateUserBinding
import com.de.carshare.models.User
import com.de.carshare.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth

class CreateUserActivity : AppCompatActivity(),OnClickListener {
    private val TAG = this.javaClass.canonicalName
    private lateinit var binding: ActivityCreateUserBinding
    private lateinit var userRepository: UserRepository
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCreateUserBinding.inflate(layoutInflater)
        this.userRepository = UserRepository(applicationContext)
        this.firebaseAuth = FirebaseAuth.getInstance()

        binding.btnCreateNewUser.setOnClickListener(this)
        setContentView(binding.root)
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.btnCreateNewUser -> {
                if (
                    ValidInput(binding.etName) &&
                    ValidInput(binding.etPhone) &&
                    ValidInput(binding.etEmail) &&
                    ValidInput(binding.etPassword)&&
                    ValidInput(binding.etDlProvince) &&
                    ValidInput(binding.etDlNumber)
                )
                {
                    val newUser = User(binding.etName.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPhone.text.toString(),
                        binding.etPassword.text.toString(),
                        binding.etDlProvince.text.toString(),
                        binding.etDlNumber.text.toString())

                    this.firebaseAuth
                        .createUserWithEmailAndPassword(newUser.email, newUser.getPassword())
                        .addOnCompleteListener(this) { task ->

                            if (task.isSuccessful) {

                                userRepository.addUser(newUser)
                                saveToPrefs(newUser.email)
                                goToMain()
                            } else {
                                Log.d(
                                    TAG,
                                    "createAccount: Unable to create user account : ${task.exception}",
                                )
                                Toast.makeText(
                                    this,
                                    "Account creation failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }



//                        val intent = Intent(this@CreateUserActivity, MainActivity::class.java)
//                        intent.putExtra("USER_ID",newUser.id)
//                        startActivity(intent)
                    }
                }
            }
        }

    private fun saveToPrefs(email: String) {
        val prefs = applicationContext.getSharedPreferences(packageName, MODE_PRIVATE)
        prefs.edit().putString("USER_EMAIL", email).apply()
    }

    private fun goToMain() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun ValidInput(et:EditText):Boolean
    {
        if (et.text.isNullOrEmpty())
        {
            et.error = "Cannot Be Empty"
            return false
        }
        return true
    }
}