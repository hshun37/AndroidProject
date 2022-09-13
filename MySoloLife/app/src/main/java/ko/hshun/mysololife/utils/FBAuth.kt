package ko.hshun.mysololife.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class FBAuth {
    companion object{
        private lateinit var auth: FirebaseAuth

        fun getUid() : String {
            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString()
        }

        fun getTime(): String {

            val currentDataTime = Calendar.getInstance().time
            val dataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR")).format(currentDataTime)
            return dataFormat
        }
    }
}