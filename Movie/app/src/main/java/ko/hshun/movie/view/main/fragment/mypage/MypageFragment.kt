package ko.hshun.movie.view.main.fragment.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ko.hshun.movie.R
import ko.hshun.movie.databinding.FragmentMypageBinding
import ko.hshun.movie.model.NaverAndOpenAPIRepository.firebase.FBAuth
import ko.hshun.movie.view.auth.LoginActivity

class MypageFragment : Fragment() {

    private lateinit var binding: FragmentMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage ,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutBtn.setOnClickListener {
            Toast.makeText(context, "로그아웃 하셨습니다.", Toast.LENGTH_SHORT).show()
            FBAuth.auth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}