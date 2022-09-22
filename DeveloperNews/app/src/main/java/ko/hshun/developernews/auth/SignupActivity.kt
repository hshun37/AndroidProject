package ko.hshun.developernews.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ko.hshun.developernews.MainActivity
import ko.hshun.developernews.R
import ko.hshun.developernews.databinding.ActivitySignupBinding
import ko.hshun.developernews.main.NewMainActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        auth = Firebase.auth
        val btnGoLogin = binding.btnGoLogin
        val btnSignup = binding.btnSignup
        val btnAnonymous = binding.btnAnonymous


        btnGoLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSignup.setOnClickListener {

            val tvEmail = binding.tvEmail.text.toString()
            val tvPassword = binding.tvPassword.text.toString()
            val tvPassword2 = binding.tvPassword2.text.toString()

            //이메일 공백 여부
            if (tvEmail.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                //패스워드 공백 여부
                if (tvPassword.isEmpty()) {
                    Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    //패스워드 8자리 이상
                    if (tvPassword.length < 7) {
                        Toast.makeText(this, "비밀번호 8자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        //패스워드 공백 여부 및 패스워드 동일 여부
                        if (tvPassword2.isEmpty() || !tvPassword2.equals(tvPassword)) {
                            Toast.makeText(this, "비밀번호를 동일하게 입력해주세요.", Toast.LENGTH_SHORT).show()
                        } else {
                            //회원가입 로직
                            auth.createUserWithEmailAndPassword(tvEmail, tvPassword)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "환영합니다!", Toast.LENGTH_SHORT).show()
                                        moveMainPage(task.result?.user)
                                    } else {
                                        Toast.makeText(this, "이메일 형식 또는 이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                    }

                }
            }


        }

        //비회원으로 로그인하기
        btnAnonymous.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "비회원으로 로그인하셨습니다.", Toast.LENGTH_SHORT).show();

                        moveMainPage(task.result?.user)
                    } else {
                        Toast.makeText(this, "비회원 로그인을 실패하셨습니다..", Toast.LENGTH_SHORT).show();
                    }
                }
        }


    }

    public override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    // 유저정보 넘겨주고 메인 액티비티 호출
    fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(this, NewMainActivity::class.java))
            finish()
        }
    }
}