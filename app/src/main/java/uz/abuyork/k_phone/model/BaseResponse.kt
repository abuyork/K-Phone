package uz.abuyork.k_phone.model

data class BaseResponse<T>(
    val success: Boolean,
    val data: T,
    val message: String,
    val error_code: Int
)