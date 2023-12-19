package com.de.carshare.models

import java.util.Date
import java.util.GregorianCalendar
import java.util.UUID

enum class STATUS {PENDING,POSTED,FILLED,ENROUTE,COMPLETED}

class Request(val departCity:String ="",
              val arrivalCity: String ="",
              val departAddress:String = "",
              val arrivalAddress:String = "",
              val creator:String ="",
              var departDate: String ="",
              var arrivalDate: String ="",
              val id: String = UUID.randomUUID().toString(),
              val stops:MutableList<String> = mutableListOf(),
              val status:STATUS =  STATUS.PENDING,
              val riders:MutableList<String> = mutableListOf()
)