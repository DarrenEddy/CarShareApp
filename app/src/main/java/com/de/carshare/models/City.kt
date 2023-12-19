package com.de.carshare.models

import java.util.UUID

class City (val name:String,val province:String, val lat:Double, val  lng:Double){
    val id: String = UUID.randomUUID().toString()
}