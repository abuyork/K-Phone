package uz.abuyork.k_phone.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import uz.abuyork.k_phone.model.*
import uz.abuyork.k_phone.model.request.MakeOrderRequest
import uz.abuyork.k_phone.model.request.RegisterRequest
import uz.abuyork.k_phone.model.request.GetProductsByIdsRequest

interface Api {
    @GET("get_offers")
    fun getOffers(): Observable<BaseResponse<List<OfferModel>>>

    @GET("get_categories")
    fun getCategories() : Observable<BaseResponse<List<CategoryModel>>>

    @GET("get_top_products")
    fun getTopProducts() : Observable<BaseResponse<List<ProductModel>>>

    @GET("get_products/{category_id}")
    fun getCategoryProducts(@Path("category_id") categoryId: Int) : Observable<BaseResponse<List<ProductModel>>>

    @POST("get_products_by_ids")
    fun getProductsByIds(@Body request: GetProductsByIdsRequest) : Observable<BaseResponse<List<ProductModel>>>

    @GET("check_phone")
    fun checkPhone(@Query("phone") phone: String): Observable<BaseResponse<CheckPhoneResponse>>

    @GET("login")
    fun login(@Query("phone") phone: String, @Query("password") password: String): Observable<BaseResponse<LoginResponse>>

    @POST("register")
    fun register(@Body request: RegisterRequest): Observable<BaseResponse<Any>>

    @GET("confirm")
    fun confirm(@Query("phone") phone: String, @Query("sms_code") password: String): Observable<BaseResponse<LoginResponse>>

    @POST("make_order")
    fun makeOrder(@Body request: MakeOrderRequest): Observable<BaseResponse<Any>>


}