package com.example.hotelmascotas.model

data class User(
    val uid: String,
    val name: String,
    val lastName: String,
    val email: String
) {
    constructor() : this("", "", "", "")
}