package com.ancient.ancient_handcraft.webhelper.api

import com.google.gson.annotations.SerializedName

data class ErrorResponsePojo(
    @SerializedName("message") var message: String = "",
    @SerializedName("field") var success: String = "",
    @SerializedName("validation") var code: String = ""
)