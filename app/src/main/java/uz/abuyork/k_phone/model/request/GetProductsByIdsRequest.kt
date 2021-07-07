package uz.abuyork.k_phone.model.request

data class GetProductsByIdsRequest(
    val products: List<Int>
)