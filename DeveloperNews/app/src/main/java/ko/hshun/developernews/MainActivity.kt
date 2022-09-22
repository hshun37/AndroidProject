package ko.hshun.developernews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ko.hshun.developernews.auth.SignupActivity
import ko.hshun.developernews.databinding.ActivityMainBinding
import ko.hshun.developernews.main.NewMainActivity

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding
    private var emaileBo: Boolean = false
    private var passwordBo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        auth = Firebase.auth

        var isToLogion = true

        val signupTV = binding.signupTV
        val btnLogin = binding.btnLogin
        val btnAnonymous = binding.btnAnonymous

        signupTV.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {

            val tvEmail = binding.tvEmail.text.toString()
            val tvPassword = binding.tvPassword.text.toString()

            //이메 공백 여부
            if (tvEmail.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                isToLogion = false
            } else {
                //패스워드 공백 여부
                if (tvPassword.isEmpty()) {
                    Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    isToLogion = false
                } else {
                    //비밀번호 8자리 이상 입력
                    if (tvPassword.length < 7) {
                        Toast.makeText(this, "8자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                        isToLogion = false
                    } else {
                        //로그인 로직
                        auth.signInWithEmailAndPassword(tvEmail, tvPassword)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "로그인 하셨습니다.", Toast.LENGTH_SHORT).show()

//                                    val intent = Intent(this, NewMainActivity::class.java)
//                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                    startActivity(intent)
                                    moveMainPage(task.result?.user)
                                } else {
                                    Toast.makeText(this, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            }



        }

        btnAnonymous.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "비회원으로 로그인하셨습니다.", Toast.LENGTH_SHORT).show();

                        moveMainPage(task.result?.user)
                    } else {
                        Toast.makeText(baseContext, "비회원 로그인을 실패하셨습니다..", Toast.LENGTH_SHORT).show();
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
