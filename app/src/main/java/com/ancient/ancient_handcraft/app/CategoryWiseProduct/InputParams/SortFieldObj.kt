package com.ancient.ancient_handcraft.app.CategoryWiseProduct.InputParams

import com.ancient.ancient_handcraft.app.PojoObj.Category.CategoryListPayload
import com.ancient.ancient_handcraft.webhelper.api.BaseResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SortFieldObj(
    @SerializedName("fieldName") var fieldName: String? = "",
    @SerializedName("sort") var sort: String? = ""
) : Serializable