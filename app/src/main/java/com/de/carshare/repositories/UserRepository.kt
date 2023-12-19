package com.de.carshare.repositories


import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.de.carshare.models.Request
import com.de.carshare.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class UserRepository(private val context:Context) {
    private val TAG = this.toString()
    private val db = Firebase.firestore
    private val COLLECTION_USERS = "Users";

    private val USER_NAME = "name"
    private val USER_EMAIL = "email"
    private val USER_PHONE = "phone"
    private val USER_PASSWORD = "password"
    private val USER_DRIVERS_LICENSE_PROVINCE = "dlProv"
    private val USER_DRIVERS_LICENSE_NUMBER = "dlNum"
    private val USER_REQUESTS = "requests"
    private val USER_BOOKINGS = "bookings"


    var allExpenses: MutableLiveData<List<Request>> = MutableLiveData()

    fun addUser(newUser: User) {
        try {
            val data: MutableMap<String, Any> = HashMap()
            data[USER_NAME] = newUser.name
            data[USER_EMAIL] = newUser.email
            data[USER_PHONE] = newUser.phone
            data[USER_PASSWORD] = newUser.getPassword()
            data[USER_DRIVERS_LICENSE_PROVINCE] = newUser.dlProv
            data[USER_DRIVERS_LICENSE_NUMBER] = newUser.dlNum
            data[USER_REQUESTS] = newUser.requests
            data[USER_BOOKINGS] = newUser.bookings

            db.collection(COLLECTION_USERS).add(data)
                .addOnSuccessListener { docRef ->
                    Log.d(
                        TAG,
                        "SUCCESSFULLY ADDED WITH ID ${docRef.id}"
                    )
                }
                .addOnFailureListener { ex ->
                    Log.d(
                        TAG,
                        "UNsuccessful in adding with exception: ${ex.toString()}"
                    )
                }


        } catch (e: java.lang.Exception) {
            Log.d(TAG, "Unable to Add to DB due to $e")
        }
    }

}