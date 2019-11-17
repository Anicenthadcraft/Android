package com.ancient.ancient_handcraft.webhelper.api

import com.ancient.ancient_handcraft.app.CategoryWiseProduct.CategoryWiseProductResponse
import com.ancient.ancient_handcraft.app.CategoryWiseProduct.InputParams.MainObj
import com.ancient.ancient_handcraft.app.PojoObj.Category.CategoryListResponse
import com.ancient.ancient_handcraft.app.PojoObj.Login.LoginResponse
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.RegisterResponse
import com.ancient.ancient_handcraft.app.PojoObj.VerifyOTP.VerifyOtpResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @FormUrlEncoded
    @POST("api/v1/register")
    fun registerUser(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("mobileNo") mobileNo: String,
        @Field("email") email: String,
        @Field("isAdmin") isAdmin: Boolean,
        @Field("password") password: String
    ): Observable<RegisterResponse>

    @FormUrlEncoded
    @POST("api/v1/login")
    fun LoginUser(
        @Field("mobileNo") mobileNo: String,
        @Field("password") password: String
    ): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("api/v1/verify-otp")
    fun VerifyOTP(
        @Field("mobileNo") mobileNo: String,
        @Field("otp") otp: String
    ): Observable<VerifyOtpResponse>


    @GET("api/v1/getUserByToken")
    suspend fun GetUserByToken(): Response<RegisterResponse>

    @POST("/api/v1/public/category/search")
    fun GetCategoryList(): Observable<CategoryListResponse>

    @POST("/api/v1/public/product/search")
    fun GetProductListByCategory(@Body payload: MainObj):Observable<CategoryWiseProductResponse>

}


