package ko.hshun.movie.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ko.hshun.movie.R
import ko.hshun.movie.databinding.ActivitySignupBinding
import ko.hshun.movie.model.NaverAndOpenAPIRepository.firebase.FBAuth
import ko.hshun.movie.view.main.activity.MainActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val TAG = "SignupActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        //로그인 이동
        getGoLogin()
        //비회원 로그인
        getAnonymous()
        //회원가입
        getSingUp()
    }

    private fun getGoLogin() {
        binding.btnGoLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
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
    private fun getSingUp() {
        binding.btnSignup.setOnClickListener {
            val email = binding.tvEmail.text.toString()
            val password = binding.tvPassword.text.toString()
            val password2 = binding.tvPassword2.text.toString()

            if (password == password2) {
                FBAuth.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "회원가입을 하셨습니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "로그인 실패하셨습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}