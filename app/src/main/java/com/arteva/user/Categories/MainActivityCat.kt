package com.arteva.user.Categories

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arteva.user.Constant
import com.arteva.user.Home.ui.HomeActivity
import com.arteva.user.R
import com.arteva.user.databinding.ActivityMainBinding
import com.arteva.user.helper.ApiConfig
import com.arteva.user.helper.Session
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONException
import org.json.JSONObject

class MainActivityCat : AppCompatActivity(), PaymentResultWithDataListener, ExternalWalletListener{

    private var fragment: CategoriesFrag? = null
    val TAG: String = MainActivityCat::class.toString()
    private lateinit var alertDialogBuilder: AlertDialog.Builder

    private lateinit var mBinding: ActivityMainBinding

    private var mCurrentBackStackCount = 1
    private var mDrawerSlideListener: DrawerSlideListener? = null

    interface DrawerSlideListener {
        fun onDrawerSlide(corner: Float)
    }

    fun setOnDrawerSlideListener(listener: DrawerSlideListener?) {
        mDrawerSlideListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        Checkout.preload(applicationContext)
        alertDialogBuilder = AlertDialog.Builder(this@MainActivityCat)
        alertDialogBuilder.setTitle("Payment Result")
        alertDialogBuilder.setCancelable(true)
        init()
    }

    private fun init() {
        setApiServiceActivity()
        initCategories()
        mCurrentBackStackCount = supportFragmentManager.backStackEntryCount
    }

    private fun setApiServiceActivity() {
        ApiService.activity = this@MainActivityCat
    }

    private fun initCategories() {
        fragment = CategoriesFrag()
        transact(fragment!!)
    }

    private fun transact(
        frag: Fragment,
    ) {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, frag).commitNow()
    }

    fun startPayment(total: Int) {
        val progressDialog = ProgressDialog(this@MainActivityCat)
        progressDialog.setTitle("Loading...")
        progressDialog.setMessage("Please Wait...")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        val ApiParams: MutableMap<String, String> = HashMap()
        try {
            ApiParams[Constant.getApiKey] = "1"
            ApiConfig.RequestToVolley({ result: Boolean, response: String? ->
                if (result) {
                    try {
                        val jsonObject = JSONObject(response)
                        val error = jsonObject.getString(Constant.ERROR)
                        if (error.equals("false", ignoreCase = true)) {
                            val api_key = jsonObject.getString(Constant.API_KEY)
                            val custom_options = jsonObject.getString(Constant.CUSTOM_OPTIONS)
                            doPayment(total, api_key, custom_options)
                            progressDialog.dismiss()
                        } else {
                            Toast.makeText(this@MainActivityCat,
                                "There was an error getting Payment info...",
                                Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(this@MainActivityCat,
                            "There was an error getting Payment info.. $e.",
                            Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                        progressDialog.dismiss()
                    }
                }
            }, ApiParams)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            progressDialog.dismiss()
        }
    }

    private fun doPayment(total: Int, apiKey: String, customOptions: String) {
        val activity: Activity = this@MainActivityCat
        val co = Checkout()
        if (!TextUtils.isEmpty(apiKey)) {
            co.setKeyID(apiKey)
        } else {
            Toast.makeText(activity,
                "There was an issue.. Please try again later",
                Toast.LENGTH_LONG).show()
            return
        }
        try {
            var options = JSONObject()
                options.put("name", "Arteva Edu Tech")
                options.put("description", "Demoing Charges")
                //You can omit the image option to fetch the image from dashboard
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                options.put("currency", "INR")
                options.put("amount", total * 100)
                options.put("send_sms_hash", true)

                val prefill = JSONObject()
                prefill.put("email", Session.getUserData(Session.EMAIL, activity))
                prefill.put("contact", Session.getUserData(Session.MOBILE, activity))

                options.put("prefill", prefill)

            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(payment_id: String?, p1: PaymentData?) {
        try {
            userSubscribed(payment_id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun userSubscribed(paymentId: String?) {
        val progressDialog = ProgressDialog(this@MainActivityCat)
        progressDialog.setTitle("Payment Successfull... ")
        progressDialog.setMessage("Payment ID :  $paymentId \nSaving Data...")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        val ApiParams: MutableMap<String, String> = HashMap()
        try {
            ApiParams[Constant.setSubscription] = "1"
            ApiParams[Constant.userId] = Session.getUserData(Session.USER_ID, this@MainActivityCat)
            ApiConfig.RequestToVolley({ result: Boolean, response: String? ->
                if (result) {
                    try {
                        val jsonObject = JSONObject(response)
                        val error = jsonObject.getString(Constant.ERROR)
                        if (error.equals("false", ignoreCase = true)) {
                            Session.setUserData(Session.IS_SUBSCRIBED, "1", this@MainActivityCat)
                            progressDialog.dismiss()
                            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                            val dialog: AlertDialog = builder.setTitle("Success!")
                                .setMessage("Payment ID : $paymentId \n Thanks for buying our course")
                                .setCancelable(false)
                                .setPositiveButton("OK") { dialog: DialogInterface?,_: Int ->
                                        openHomeActivity()
                                        dialog?.dismiss()
                                }
                                .create()
                            dialog.setCancelable(false)
                            dialog.setCanceledOnTouchOutside(false)
                            dialog.show()
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)
                        } else {
                            Toast.makeText(this@MainActivityCat,
                                "There was an error getting education List...",
                                Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        progressDialog.dismiss()
                    }
                }
            }, ApiParams)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            progressDialog.dismiss()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        try {
            alertDialogBuilder.setTitle("Payment Failed")
            alertDialogBuilder.setMessage("Info: : ${p2?.data?.getString("description")}")
            alertDialogBuilder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        try {
            alertDialogBuilder.setMessage("External wallet was selected : Payment Data: ${p1?.data}")
            alertDialogBuilder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openHomeActivity() {
        intent = Intent(this@MainActivityCat, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("type", "default")
        startActivity(intent)
        finish()
    }
}