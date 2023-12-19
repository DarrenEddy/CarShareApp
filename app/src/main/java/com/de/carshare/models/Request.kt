package com.de.carshare.models

import java.util.GregorianCalendar
import java.util.UUID

enum class STATUS {PENDING,POSTED,FILLED,ENROUTE,COMPLETED}

class Request(val departCity:String, val arrivalCity: String, val creator:String, var departDate: GregorianCalendar, var arrivalDate: GregorianCalendar){
    val id: String = UUID.randomUUID().toString()
    val stops:MutableList<String> = mutableListOf()
    val status:STATUS =  STATUS.PENDING
    val riders:MutableList<String> = mutableListOf()

}