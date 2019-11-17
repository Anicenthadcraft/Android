package com.ancient.ancient_handcraft.app.CategoryWiseProduct.InputParams

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PaginationObj(
    @SerializedName("page") var page: String? = "",
    @SerializedName("perPage") var perPage: String? = ""
) : Serializable
