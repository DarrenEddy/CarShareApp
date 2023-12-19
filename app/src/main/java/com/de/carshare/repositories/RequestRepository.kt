package com.de.carshare.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.de.carshare.models.Request
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class RequestRepository(private val context:Context) {
    private val TAG = this.toString()
    private val db = Firebase.firestore
    private val COLLECTION_REQUESTS = "Requests";

    private val DEPART_CITY = "departCity"
    private val ARRIVAL_CITY = "arrivalCity"
    private val DEPART_ADDRESS = "departAddress"
    private val ARRIVAL_ADDRESS = "arrivalAddress"
    private val CREATOR = "creator"
    private val DEPART_DATE = "departDate"
    private val ARRIVAL_DATE = "arrivalDate"
    private val STOPS = "stops"
    private val STATUS = "status"
    private val RIDERS = "riders"


    var allRequest: MutableLiveData<List<Request>> = MutableLiveData()

    fun addRequest(newRequest: Request) {
        try {
            val data: MutableMap<String, Any> = HashMap()
            data[DEPART_CITY] = newRequest.departCity
            data[ARRIVAL_CITY] = newRequest.arrivalCity
            data[ARRIVAL_ADDRESS] =newRequest.arrivalAddress
            data[DEPART_ADDRESS] = newRequest.departAddress
            data[CREATOR] = newRequest.creator
            data[DEPART_DATE] = newRequest.departDate
            data[ARRIVAL_DATE] = newRequest.arrivalDate
            data[STOPS] = newRequest.stops
            data[STATUS] = newRequest.status
            data[RIDERS] = newRequest.riders

            db.collection(COLLECTION_REQUESTS).document(newRequest.id).set(data)
                .addOnSuccessListener { docRef ->
                    Log.d(
                        TAG,
                        "SUCCESSFULLY ADDED WITH ID $docRef"
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


    fun getAllRequests() {
        try {
            db.collection(COLLECTION_REQUESTS).addSnapshotListener(EventListener { result, error ->
//                Will be notified of any changes on the collection
                if (error != null) {
                    Log.e(TAG, "LISTENING FAILED: ${error.toString()}")
                    return@EventListener
                }
                if (result == null) {
                    Log.d(TAG, "No Data in result of retrieve all")
                } else {
                    Log.d(TAG, "Got Results of size: ${result.size()}")
                    val tempList: MutableList<Request> = ArrayList<Request>()

//                    Three kinds of changes, on add doc, remove doc, modify doc
                    for (docChanges in result.documentChanges) {
                        val currentDocument: Request =
                            docChanges.document.toObject(Request::class.java)

                        when (docChanges.type) {
                            DocumentChange.Type.ADDED -> {
//                                  do neccessary changes to your local list of objects
                                tempList.add(currentDocument)
                            }

                            DocumentChange.Type.MODIFIED -> {}
                            DocumentChange.Type.REMOVED -> {

                            }
                        }
                    }
//                    AFTER FOR
                    allRequest.postValue(tempList)


                }
//                result will be null if nothing matches the query


            })
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "UNABLE TO GET ALL EXPENSES, ERROR:$e")
        }
    }
}