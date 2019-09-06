package com.ancient.ancient_handcraft.app.PojoObj.Login

import com.ancient.ancient_handcraft.app.PojoObj.SignUp.RegisterPayloadPojo
import com.ancient.ancient_handcraft.webhelper.api.BaseResponse
import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("payload") var payload: LoginPayloadpojo? = null
) : BaseResponse()