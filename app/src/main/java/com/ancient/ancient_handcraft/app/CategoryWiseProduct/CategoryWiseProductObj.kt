package com.ancient.ancient_handcraft.app.CategoryWiseProduct

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CategoryWiseProductObj(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("name") var name: String? = "",
    @SerializedName("slug") var slug: String? = "",
    @SerializedName("price") var price: Int? = 0,
    @SerializedName("discount") var discount: Int? = 0,
    @SerializedName("tax") var tax: Int? = 0,
    @SerializedName("attributes") var attributes: String? = "",
    @SerializedName("listStatus") var listStatus: Int? = 0
):Serializable