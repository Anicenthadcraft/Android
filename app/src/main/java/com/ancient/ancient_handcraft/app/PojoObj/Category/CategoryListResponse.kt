package com.ancient.ancient_handcraft.app.PojoObj.Category

import com.ancient.ancient_handcraft.webhelper.api.BaseResponse
import com.google.gson.annotations.SerializedName

class CategoryListResponse(
    @SerializedName("payload") var payload: ArrayList<CategoryListPayload>? = null
) : BaseResponse()