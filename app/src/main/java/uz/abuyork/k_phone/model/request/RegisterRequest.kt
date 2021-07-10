package uz.abuyork.k_phone.model.request

data class RegisterRequest(
    val fullname: String,
    val phone: String,
    val password: String
)