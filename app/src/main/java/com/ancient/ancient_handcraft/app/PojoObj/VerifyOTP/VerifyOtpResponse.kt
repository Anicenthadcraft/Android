package com.ancient.ancient_handcraft.app.PojoObj.VerifyOTP

import com.ancient.ancient_handcraft.webhelper.api.BaseResponse
import com.google.gson.annotations.SerializedName

class VerifyOtpResponse(
    @SerializedName("payload") var payload: VerifyOtpPayloadPojo? = null
) : BaseResponse()