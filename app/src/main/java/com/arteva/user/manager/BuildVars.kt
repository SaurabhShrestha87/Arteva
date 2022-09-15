package com.arteva.user.manager

import com.arteva.user.Constant.QUIZ_URL
import com.arteva.user.R


object BuildVars {
    @JvmField
    var LOGS_ENABLED = true
    const val API_KEY = "1234"
//    const val API_KEY = "base64:12DLMxEkIDn7qEehOsFAG/znsORqYO82eUYM4idB+f0="
//    const val API_KEY = "12DLMxEkIDn7qEehOsFAG/znsORqYO82eUYM4idB+f0="
    const val BASE_URL = "https://credinopos.in/arteva/api/"
    const val MEDIA_URL = "https://credinopos.in/arteva/public"
    const val CERT_DOWNLOAD_URL = "${BASE_URL}panel/quizzes/results/{}/download"
    const val PAYMENT_GATEWAY_URL = "${BASE_URL}panel/payments/request"

    class DefaultLang(val value: String) {
        companion object {
            const val CODE = "en"
            const val NAME = "English"
        }
    }

    var LNG_FLAG: HashMap<String, Int> =
        object : HashMap<String, Int>() {
            init {
                put("ENGLISH", R.drawable.flag_united_kingdom)
                put("PERSIAN", R.drawable.flag_iran)
                put("ARABIC", R.drawable.flag_iraq)
                put("FRENCH", R.drawable.flag_france)
                put("GERMAN", R.drawable.flag_germany)
                put("SPANISH", R.drawable.flag_spain)
                put("RUSSIAN", R.drawable.flag_russian_federation)
                put("TURKISH", R.drawable.flag_turkey)
                put("CHINESE", R.drawable.flag_china)
                put("HINDI", R.drawable.flag_india)
                put("BENGALI", R.drawable.flag_bangladesh)
                put("PORTUGUESE", R.drawable.flag_portugal)
                put("INDONESIAN", R.drawable.flag_indonesia)
                put("JAPANESE", R.drawable.flag_japan)
                put("KOREAN", R.drawable.flag_south_korea)
                put("URDU", R.drawable.flag_pakistan)
                put("ITALIAN", R.drawable.flag_italy)
                put("POLISH", R.drawable.flag_poland)
                put("YORUBA", R.drawable.flag_nigeria)
                put("DUTCH", R.drawable.flag_netherlands)
                put("MALAYSIAN", R.drawable.flag_malaysia)
                put("GREEK", R.drawable.flag_greece)
                put("CZECH", R.drawable.flag_czech_republic)
                put("HEBREW", R.drawable.flag_israel)
            }
        }

}