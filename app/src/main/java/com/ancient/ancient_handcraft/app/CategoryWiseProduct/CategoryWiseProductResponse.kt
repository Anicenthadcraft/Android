package com.ancient.ancient_handcraft.app.CategoryWiseProduct

import com.ancient.ancient_handcraft.app.PojoObj.Category.CategoryListPayload
import com.ancient.ancient_handcraft.webhelper.api.BaseResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CategoryWiseProductResponse(
    @SerializedName("payload") var payload: CategoryWiseProductPayload? = null
) : BaseResponse()