package com.ancient.ancient_handcraft.app.PojoObj.Category

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CategoryListPayload(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("name") var name: String? = "",
    @SerializedName("slug") var slug: String? = "",
    @SerializedName("parentId") var parentId: Int? = 0
) : Serializable