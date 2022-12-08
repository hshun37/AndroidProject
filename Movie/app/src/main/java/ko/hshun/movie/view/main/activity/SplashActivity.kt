package ko.hshun.movie.view.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import ko.hshun.movie.R
import ko.hshun.movie.databinding.ActivitySplashBinding
import ko.hshun.movie.model.NaverAndOpenAPIRepository.firebase.FBAuth
import ko.hshun.movie.view.auth.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        onStart()

    }

    public override fun onStart() {
        super.onStart()

        val currentUser = FBAuth.auth.currentUser
        // 로그인 인증 되어있을시
        if(currentUser != null){
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
        // 로그인 인증 되어있지않을시
        else {
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
    }
}