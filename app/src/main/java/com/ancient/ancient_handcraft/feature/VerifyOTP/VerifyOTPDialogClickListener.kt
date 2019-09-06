package com.ancient.ancient_handcraft.feature.VerifyOTP

interface VerifyOTPDialogClickListener {
    fun onSubmitClick(otp: String)
    fun showDialogLoader()
    fun hideDialogLoader()
    fun onVerifySuccess()
    fun onVerifyFailure()
}