package com.de.carshare.models

import java.util.UUID

class User(
    val name: String ="",
    val email: String = "",
    val phone: String= "",
    private var password: String ="",
    var dlProv: String = "",
    var dlNum: String = "",
    val requests: MutableList<String> = mutableListOf(),
    val bookings: MutableList<String> = mutableListOf()
) {
    fun getPassword(): String {
        return this.password
    }
}