package com.ancient.ancient_handcraft.app.CategoryWiseProduct

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CategoryWiseProductPayload(
    @SerializedName("total") var total: Int? = 0,
    @SerializedName("perPage") var perPage: Int? = 0,
    @SerializedName("page") var page: Int? = 0,
    @SerializedName("lastPage")  var lastPage: Int? = 0,
    @SerializedName("data")  var data : ArrayList<CategoryWiseProductObj?>
) : Serializable