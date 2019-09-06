package com.ancient.ancient_handcraft.webhelper.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResponse(
    @SerializedName("status") var status: Int = 0,
    @SerializedName("success") var success: Boolean = false,
    @SerializedName("code") var code: String = "",
    @SerializedName("message") var message: ArrayList<ErrorResponsePojo>? = null
) : Serializable