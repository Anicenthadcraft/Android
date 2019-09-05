package com.ancient.ancient_handcraft.webhelper.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResponse(
    @SerializedName("code") var code: Int=0,
    @SerializedName("details") var details: String = ""
) : Serializable