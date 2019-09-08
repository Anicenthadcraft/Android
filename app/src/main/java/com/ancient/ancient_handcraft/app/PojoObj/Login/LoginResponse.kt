package com.ancient.ancient_handcraft.app.PojoObj.Login

import com.ancient.ancient_handcraft.app.PojoObj.SignUp.UserPayloadPojo
import com.ancient.ancient_handcraft.webhelper.api.BaseResponse
import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("payload") var payload: UserPayloadPojo? = null
) : BaseResponse()