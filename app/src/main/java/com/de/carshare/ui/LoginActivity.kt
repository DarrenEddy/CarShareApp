package com.de.carshare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.Toast
import com.de.carshare.R
import com.de.carshare.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(),OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(layoutInflater)
        this.firebaseAuth = FirebaseAuth.getInstance()


        this.binding.btnLogin.setOnClickListener(this)
        this.binding.btnCreateNewUser.setOnClickListener(this)


        setContentView(this.binding.root)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_Login ->
                {
                    Toast.makeText(this, "attempting Login", Toast.LENGTH_SHORT).show()

                    if (ValidInput(this.binding.etEmail) && ValidInput(this.binding.etPassword))
                    {
                        val email = this.binding.etEmail.text.toString()
                        val password = this.binding.etPassword.text.toString()

                        this.firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
                            if (task.isSuccessful)
                            {
                                saveToPrefs(email)
                                goToMain()
                            }
                            else
                            {
                                Toast.makeText(this, "Unable To Login", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }
            R.id.btn_create_new_user -> {
                val intent = Intent(this,CreateUserActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun saveToPrefs(email: String) {
        val prefs = applicationContext.getSharedPreferences(packageName, MODE_PRIVATE)
        prefs.edit().putString("USER_EMAIL", email).apply()
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun ValidInput(et: EditText):Boolean
    {
        if (et.text.isNullOrEmpty())
        {
            et.error = "Cannot Be Empty"
            return false
        }
        return true
    }
}