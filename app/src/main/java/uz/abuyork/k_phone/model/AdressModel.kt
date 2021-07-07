package uz.abuyork.k_phone.model

import java.io.Serializable


data class AdressModel(
    val address: String,
    val latitude: Double,
    var longtitude: Double
): Serializable