package ko.hshun.movie.model.NaverAndOpenAPIRepository.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FBAuth {
    companion object {
        val auth: FirebaseAuth = Firebase.auth
    }
}