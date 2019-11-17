package com.ancient.ancient_handcraft.Utils

import android.app.Activity
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.Html
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.AnyRes
import androidx.annotation.NonNull
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import com.ancient.ancient_handcraft.R
import org.json.JSONObject
import java.io.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class AppUtils {

    companion object {

        var sImagePath: String? = null
        var isChatDeleteInitiated: Boolean? = false
        public var selectedContact: ArrayList<Int> = ArrayList<Int>()

        val MY_PERMISSIONS_REQUEST_LOCATION = 99
        val TAG_CAMERA_RESULTCODE = 103
        val PERMISSION_REQUEST_CONTACT = 109


        val REQUEST_CODE_PICK_CONTACTS = 106
        val REQUEST_CODE_PHONE_STATE = 102
        var isNavigateFromMyAccount: Boolean = false
        var isDialogShowing: Boolean = false
        var isBackPressed: Boolean = false
        lateinit var tw: TextWatcher
        var isUsedFor: String = ""
        var userBitMap: Bitmap? = null
        val GALLERY = "GALLERY"
        val CAMERA = "CAMERA"


        val type = ""
        //  private val `var`: lateinit? = null bannerArray : ArrayList < BannerObject >


        fun isValidateMobileLogin(pHoneNo: String): Boolean {
            if (pHoneNo.length > 0 && pHoneNo.length < 8) {
                return false
            } else {
                return true
            }

        }

        public fun getUriToDrawable(
            @NonNull context: Context,
            @AnyRes drawableId: Int
        ): Uri {
            var imageUri: Uri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + context.getResources().getResourcePackageName(drawableId)
                        + '/' + context.getResources().getResourceTypeName(drawableId)
                        + '/' + context.getResources().getResourceEntryName(drawableId)
            );
            return imageUri;
        }

        fun mobileNumberForgotPswValidation(phoneNo: String): Boolean {

            if (phoneNo.length > 0 && phoneNo.length < 8) {
                return false
            } else if (phoneNo.length > 0 && phoneNo.length > 10) {
                return false
            } else {
                return true
            }
        }


        fun mobileNumberForgotPswExtendedValidation(phoneNo: String): Boolean {
            if (phoneNo.length > 0 && (phoneNo.length < 12 || phoneNo.length > 14)) {
                return false
            } else {
                return true
            }

        }


        fun forgotPswMobileValidate(phoneNo: String): Boolean {
            if (phoneNo.length > 0 && (phoneNo.length < 8 || phoneNo.length > 10)) {
                return false
            } else {
                return true
            }
        }


        fun restrictLeadingSpaceAppCustomEditText(edt: EditText) {
            val filter = object : InputFilter {
                internal var canEnterSpace = false

                override fun filter(
                    source: CharSequence, start: Int, end: Int,
                    dest: Spanned, dstart: Int, dend: Int
                ): CharSequence {

                    if (edt.getText().toString().equals("")) {
                        canEnterSpace = false
                    }

                    val builder = StringBuilder()

                    for (i in start until end) {
                        val currentChar = source[i]

                        if (Character.isLetter(currentChar) || currentChar == '_') {
                            builder.append(currentChar)
                            canEnterSpace = true
                        }

                        if (Character.isWhitespace(currentChar) && canEnterSpace) {
                            builder.append(currentChar)
                        }


                    }
                    return builder.toString()
                }

            }
            edt.setFilters(arrayOf(filter))
        }

        fun restrictSpaceEditText(edt: EditText) {
            var filter: InputFilter = InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    if (Character.isWhitespace(source[i])) {
                        return@InputFilter ""
                    }
                }
                null
            }
            edt.setFilters(arrayOf(filter))
        }


//        fun validatePassword(pswd: String): Boolean {
//            //check that there are letters
//            if(!pswd.matches("[A-Z]+".toRegex()))
//                return false
//            if (!pswd.matches("[a-z]+".toRegex()))
//                return false  //nope no letters, stop checking and fail!
//            //check if there are any numbers
//            if (!pswd.matches("[0-9]+".toRegex()))
//                return false  //nope no numbers, stop checking and fail!
//            //check any valid special characters
//            if (!pswd.matches("[.!#*()?,]+".toRegex()))
//                return false
//            else return true  //nope no special chars, fail!
//
//            //everything has passed so far, lets return valid
//        }

        fun parseInt(input: String): Int {
            try {
                return if (isParsableInt(input))
                    Integer.parseInt(input)
                else
                    0

            } catch (e: NumberFormatException) {
                return 0
            }

        }

        fun getTotalNoOfPages(totItems: Int, itemsPerPage: Int): Int {
            var noOfPages = 1
            if (totItems == 0 || itemsPerPage == 0) {
                return noOfPages
            }

            if (totItems % itemsPerPage != 0) {
                noOfPages = totItems / itemsPerPage + 1
            } else {
                noOfPages = totItems / itemsPerPage
            }
            return noOfPages
        }

        fun isParsableInt(input: String): Boolean {
            var parsable = true
            try {
                Integer.parseInt(input)
            } catch (e: NumberFormatException) {
                parsable = false
            }

            return parsable
        }


        fun convertBigDecimalToString(amountval: Double): String {
            var df: DecimalFormat = DecimalFormat("#");
            df.setMaximumFractionDigits(8);
            var bigDecimal = BigDecimal.valueOf(amountval)
            var amount = df.format(bigDecimal)
            return amount
        }


        fun removeLeadingZero(value: String): String {
            var StrValue: String = ""
            if (!value.equals("")) {
                var intVal = value.toInt()
                StrValue = intVal.toString()
            }
            return StrValue
        }

        fun trimLeadingZeros(source: String): String {
            for (i in 0 until source.length) {
                val c = source[i]
                if (c != '0') {
                    return source.substring(i)
                }
            }
            return ""
        }

        fun convertDoubleToString(amountval: Double): String {
            var df: DecimalFormat = DecimalFormat("#");
            df.setMaximumFractionDigits(8);
            var amount = df.format(amountval)
            return amount
        }

        fun convertDoubleToStringUpToTwoDecimal(amountval: Double): String {
            try {
                return String.format("%.2f", amountval)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        fun convertFloatToDoubleUpToTwoDecimal(amountval: Float): Double {
            var toDouble: Double = 0.0
            try {
                toDouble = String.format("%.2f", amountval).toDouble()
                return toDouble
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return toDouble
        }


        fun getDate(createdDate: String): String {
            val format1 = SimpleDateFormat("yyyy-MM-dd")
            val format2 = SimpleDateFormat("dd MMM yyyy")
            var date: Date? = null
            try {
                date = format1.parse(createdDate)
            } catch (e: ParseException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            return format2.format(date)
        }

        fun getDateWithComa(createdDate: String): String {
            val format1 = SimpleDateFormat("yyyy-MM-dd")
            val format2 = SimpleDateFormat("dd MMM, yyyy")
            var day: String = ""
            var date: Date? = null
            try {
                date = format1.parse(createdDate)
                day = DateFormat.format("dd", date) as String
            } catch (e: ParseException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            if (day.toInt() >= 11 && day.toInt() <= 13) {
                return SimpleDateFormat("dd'th' MMM, yyyy").format(date);
            }
            when (day.toInt() % 10) {
                1 -> {
                    return SimpleDateFormat("dd'st' MMM, yyyy").format(date);
                }
                2 -> {
                    return SimpleDateFormat("dd'nd' MMM, yyyy").format(date);
                }
                3 -> {
                    return SimpleDateFormat("dd'rd' MMM, yyyy").format(date);
                }
                else -> {
                    return SimpleDateFormat("dd'th' MMM, yyyy").format(date);
                }

            }
        }


        public fun getTime(createdDate: String): String {
            val format1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val format2 = SimpleDateFormat("h:mm a")
            var date: Date? = null
            try {
                date = format1.parse(createdDate)
            } catch (e: ParseException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            return format2.format(date)
        }

        fun validatePassword(pswd: String): Boolean {
            //check that there are letters
            if (!pswd.matches("[a-zA-Z]+".toRegex()))
                return false  //nope no letters, stop checking and fail!
            //check if there are any numbers
            if (!pswd.matches("[0-9]+".toRegex()))
                return false  //nope no numbers, stop checking and fail!
            //check any valid special characters
            if (!pswd.matches("[.!#*()?,]+".toRegex()))
                return false  //nope no special chars, fail!
            else
                return true

            //everything has passed so far, lets return valid
        }

        fun isValidateMobile(pHoneNo: String): Boolean {
            return pHoneNo.length == 10
        }

        fun isValidateMobileForgotPassword(pHoneNo: String): Boolean {

            return pHoneNo.length == 13
        }

        /*fun base64Encode(token: String): String {
            val converted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getEncoder().encode(token.toString().getBytes(), Base64.DEFAULT)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val encodedBytes = Base64.getEncoder.encode(token.toByteArray())
            return String(encodedBytes, Charset.forName("UTF-8"))
        }


        fun base64Decode(token: String): String {
            val decodedBytes = Base64.decode(token.toByteArray())
            return String(decodedBytes, Charset.forName("UTF-8"))
        }*/

        fun isValidEmail(emailId: String): Boolean {
            return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
            ).matcher(emailId).matches()
        }


        fun hideSoftKeyboard(activity: Activity) {
            try {
                if (activity != null) {
                    val inputMethodManager =
                        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
                }

//                val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//                //Find the currently focused view, so we can grab the correct window token from it.
//                var view = activity.currentFocus
//                //If no view currently has focus, create a new one, just so we can grab a window token from it
//                if (view == null) {
//                    view = View(activity)
//                }
//                imm.hideSoftInputFromWindow(view!!.windowToken, 0)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun visibleSoftKeyboard(activity: Activity) {
            try {
                if (activity != null) {
                    val inputMethodManager =
                        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.toggleSoftInput(
                        InputMethodManager.SHOW_FORCED,
                        InputMethodManager.SHOW_IMPLICIT
                    );
                }

//                val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//                //Find the currently focused view, so we can grab the correct window token from it.
//                var view = activity.currentFocus
//                //If no view currently has focus, create a new one, just so we can grab a window token from it
//                if (view == null) {
//                    view = View(activity)
//                }
//                imm.hideSoftInputFromWindow(view!!.windowToken, 0)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun showKeyboard(context: Context) {
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }

        fun showSoftKeyboard(context: Context, view: View) {
            view.requestFocus()
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }

        fun ShareImage(context: Context, sharePath: String) {
            val uri = Uri.parse("content://" + sharePath)
            val share = Intent(Intent.ACTION_SEND)
            share.type = "audio/*"
            share.putExtra(Intent.EXTRA_STREAM, uri)
            context.startActivity(Intent.createChooser(share, "ShareQR"))
        }

        /*fun isOnline(mContext: Context): Boolean {
            val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var info: NetworkInfo? = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            // test for connection for WIFI
            if (info != null && info.isAvailable && info.isConnected) {
                return true
            }
            info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            // test for connection for Mobile
            return if (info != null && info.isAvailable && info.isConnected) {
                true
            } else false
        }*/


        fun isOnline(mContext: Context): Boolean {
            val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    return true
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    return true
                }
            } else {
                return false
            }


            return false
        }


        fun expand(v: View) {
            v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            val targetHeight = v.measuredHeight

            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.layoutParams.height = 1
            v.visibility = View.VISIBLE
            val a = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    v.layoutParams.height = if (interpolatedTime == 1f)
                        WindowManager.LayoutParams.WRAP_CONTENT
                    else
                        (targetHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            // 1dp/ms
            a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
            v.startAnimation(a)
        }

        fun collapse(v: View) {
            val initialHeight = v.measuredHeight

            val a = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    if (interpolatedTime == 1f) {
                        v.visibility = View.GONE
                    } else {
                        v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                        v.requestLayout()
                    }
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            // 1dp/ms
            a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
            v.startAnimation(a)
        }

        fun deleteAllFiles(context: Context) {
            val philipFolder = File(getHiddenAppFolder(context))
            philipFolder.deleteRecursively()
        }

        fun decodeImage(mContext: Context, filePath: String, isCreateCopy: Boolean): String {
            // Decode image size
            var destination: File? = null;
            var bm: Bitmap? = null
            val o = BitmapFactory.Options()
            /* o.inJustDecodeBounds = true
             BitmapFactory.decodeFile(filePath, o)

             // The new size we want to scale to
             // final int REQUIRED_SIZE = 1024;
             val REQUIRED_SIZE = 1024

             // Find the correct scale value. It should be the power of 2.
             var width_tmp = o.outWidth
             var height_tmp = o.outHeight
             var scale = 1
             while (true) {
                 if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                     break
                 width_tmp /= 2
                 height_tmp /= 2
                 scale *= 2
             }

             // Decode with inSampleSize
             val o2 = BitmapFactory.Options()
             o2.inSampleSize = scale*/
            bm = scaleImage(File(filePath))

            if (bm != null) {
//                rotateImage(filePath, bm)
                val bytes = ByteArrayOutputStream()
                bm!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

                if (isCreateCopy) {
                    val destinationFolder = getHiddenAppFolder(mContext)
                    val destinationFolderFile = File(destinationFolder)
                    destination = File(
                        destinationFolderFile,
                        "Image_" + System.currentTimeMillis().toString() + ".jpg"
                    )
                } else destination = File(filePath)

                val fo: FileOutputStream
                try {
                    if (isCreateCopy)
                        destination.createNewFile()
                    fo = FileOutputStream(destination)
                    fo.write(bytes.toByteArray())
                    fo.close()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: Exception) {

                } finally {
                    if (null != bm)
                        bm.recycle()
                }
            }
            if (destination != null)
                return destination!!.path
            else
                return ""
        }


        @Throws(IOException::class)
        fun scaleImage(file: File): Bitmap? {

            var bitmap: Bitmap?

            try {
                val MAX_IMAGE_DIMENSION = 1024
                var fis = FileInputStream(file)
                val dbo = BitmapFactory.Options()
                dbo.inJustDecodeBounds = true
                BitmapFactory.decodeStream(fis, null, dbo)
                fis.close()

                val rotatedWidth: Int
                val rotatedHeight: Int
                val mCurrentPhotoPath = file.absolutePath
                val exif = ExifInterface(mCurrentPhotoPath)
                var orientation = Integer.parseInt(exif.getAttribute(ExifInterface.TAG_ORIENTATION))
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_270 -> orientation = 270
                    ExifInterface.ORIENTATION_ROTATE_180 -> orientation = 180
                    ExifInterface.ORIENTATION_ROTATE_90 -> orientation = 90
                    ExifInterface.ORIENTATION_NORMAL -> orientation = 0
                }
                if (orientation == 90 || orientation == 270) {
                    rotatedWidth = dbo.outHeight
                    rotatedHeight = dbo.outWidth
                } else {
                    rotatedWidth = dbo.outWidth
                    rotatedHeight = dbo.outHeight
                }

                var srcBitmap: Bitmap
                fis = FileInputStream(file)
                if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
                    val widthRatio = rotatedWidth.toFloat() / MAX_IMAGE_DIMENSION.toFloat()
                    val heightRatio = rotatedHeight.toFloat() / MAX_IMAGE_DIMENSION.toFloat()
                    val maxRatio = Math.max(widthRatio, heightRatio)

                    // Create the bitmap from file
                    val options = BitmapFactory.Options()
                    options.inSampleSize = maxRatio.toInt()
                    srcBitmap = BitmapFactory.decodeStream(fis, null, options)
                } else {
                    srcBitmap = BitmapFactory.decodeStream(fis)
                }
                fis.close()

                if (orientation > 0) {
                    val matrix = Matrix()
                    matrix.postRotate(orientation.toFloat())

                    srcBitmap = Bitmap.createBitmap(
                        srcBitmap, 0, 0, srcBitmap.width,
                        srcBitmap.height, matrix, true
                    )
                }

                val baos = ByteArrayOutputStream()
                srcBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
                val bMapArray = baos.toByteArray()
                baos.close()
                val fos = FileOutputStream(file)
                fos.write(bMapArray)
                fos.flush()
                fos.close()
                bitmap = BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.size)
                return bitmap
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        fun rotateImage(photoPath: String, bitmap: Bitmap): Bitmap {
            var angle: Float;
            val ei = ExifInterface(photoPath)
            val orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            var rotatedBitmap: Bitmap? = null
            when (orientation) {

                ExifInterface.ORIENTATION_ROTATE_90 -> angle = 90f

                ExifInterface.ORIENTATION_ROTATE_180 -> angle = 180f

                ExifInterface.ORIENTATION_ROTATE_270 -> angle = 270f

                ExifInterface.ORIENTATION_NORMAL -> angle = 0f
                else -> angle = 0f
            }


            if (angle != 0f) {
                val matrix = Matrix()
                matrix.postRotate(angle)
                return Bitmap.createBitmap(
                    bitmap, 0, 0, bitmap.width, bitmap.height,
                    matrix, true
                )
            }
            return bitmap
        }

        fun getHiddenAppFolder(context: Context): String {
            val destinationFolder =
                Environment.getExternalStorageDirectory().path + File.separator + context.resources.getString(
                    R.string.app_name
                )
            val destinationFolderFile = File(destinationFolder)
            if (!destinationFolderFile.exists())
                destinationFolderFile.mkdir()
            else if (!destinationFolderFile.isDirectory())
                destinationFolderFile.mkdir()
            return destinationFolder;
        }

        fun getPhotoFilePath(context: Context, fileName: String): String {
            val filePath = getHiddenAppFolder(context) + File.separator + fileName + ".jpg"
            val destinationFolderFile = File(filePath)
//            if (!destinationFolderFile.exists())
//                destinationFolderFile.createNewFile()

            return filePath;
        }

        fun getDayNumberSuffix(day: Int): String {
            if (day >= 11 && day <= 13) {
                return "th "
            }
            when (day % 10) {
                1 -> return "st "
                2 -> return "nd "
                3 -> return "rd "
                else -> return "th "
            }
        }

        fun formatMonth(month: String): String {

            try {
                val monthParse = SimpleDateFormat("MM")
                val monthDisplay = SimpleDateFormat("MMM")
                return monthDisplay.format(monthParse.parse(month))
            } catch (e: ParseException) {
                e.printStackTrace()
                return " "
            }

        }

        fun setHtmlTextToTextView(textView: TextView, htmlText: String) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
                textView.text = trimNewLine(Html.fromHtml(htmlText))
            else
                textView.text = trimNewLine(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT))
        }

        private fun trimNewLine(text: CharSequence): CharSequence {
            var text = text

            while (text[text.length - 1] == '\n') {
                text = text.subSequence(0, text.length - 1)
            }
            return text
        }


        fun getDecimalSuffixValue(mValue: Double): Int {
            val d = mValue
            var bd = BigDecimal((d - Math.floor(d)) * 100)
            bd = bd.setScale(4, RoundingMode.HALF_DOWN)
            println(bd.toInt())
            return bd.toInt()
        }

        fun RoundTo2Decimals(value: Double): Double {
            val df2 = DecimalFormat("###.##")
            return java.lang.Double.valueOf(df2.format(value))!!
        }

        fun round(value: Float, places: Int): Double {
            var value = value
            if (places < 0) throw IllegalArgumentException()

            val factor = Math.pow(10.0, places.toDouble()).toLong()
            value = value * factor
            val tmp = Math.round(value)
            return tmp.toDouble() / factor
        }


        fun toDp(context: Context, value: Int): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }

        fun isRTL(): Boolean {
            return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL
        }

        fun showToastMsg(context: Context, msg: String) {
            StyleableToast.Builder(context)
                .text(msg)
                .backgroundColor(context.resources.getColor(R.color.wallet_amount_txt_color))
                .show()
        }

    }


}