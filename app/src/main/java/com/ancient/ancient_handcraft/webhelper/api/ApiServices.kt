package com.ancient.ancient_handcraft.webhelper.api

import com.ancient.ancient_handcraft.app.PojoObj.SignUp.RegisterResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServices {

    @FormUrlEncoded
    @POST("v1/register")
    fun registerUser(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("mobileNo") mobileNo: String,
        @Field("email") email: String,
        @Field("isAdmin") isAdmin: Boolean,
        @Field("password") password: String
    ): Observable<RegisterResponse>


}


