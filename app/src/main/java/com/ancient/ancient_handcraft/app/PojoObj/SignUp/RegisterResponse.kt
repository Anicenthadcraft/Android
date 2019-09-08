package com.ancient.ancient_handcraft.app.PojoObj.SignUp

import com.ancient.ancient_handcraft.webhelper.api.BaseResponse
import com.google.gson.annotations.SerializedName

class RegisterResponse(
    @SerializedName("payload") var payload: UserPayloadPojo? = null
) : BaseResponse()
