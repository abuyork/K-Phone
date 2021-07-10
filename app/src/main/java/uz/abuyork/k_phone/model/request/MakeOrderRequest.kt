package uz.abuyork.k_phone.model.request

import uz.abuyork.k_phone.model.CartModel

data class MakeOrderRequest(
    val products: List<CartModel>,
    val order_type: String,
    val adress: String,
    val lat: Double,
    val lon: Double,
    val comment: String
)