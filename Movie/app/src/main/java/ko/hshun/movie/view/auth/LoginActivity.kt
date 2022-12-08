package ko.hshun.movie.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ko.hshun.movie.R
import ko.hshun.movie.databinding.ActivityLoginBinding
import ko.hshun.movie.model.NaverAndOpenAPIRepository.firebase.FBAuth
import ko.hshun.movie.view.main.activity.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        //비회원 로그인
        getAnonymous()
        //회원가입 이동
        getSignUp()
        //로그인
        getLogin()


    }

    private fun getAnonymous() {
        binding.btnAnonymous.setOnClickListener {
            FBAuth.auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "비회원 로그인 하셨습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "비회원 로그인 실패하셨습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun getSignUp() {
        binding.signupTV.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
    private fun getLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.tvEmail.text.toString()
            val password = binding.tvPassword.text.toString()

            FBAuth.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "로그인을 하셨습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}