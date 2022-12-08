package ko.hshun.movie.view.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ko.hshun.movie.R
import ko.hshun.movie.databinding.ActivityMainBinding
import ko.hshun.movie.view.main.fragment.home.HomeFragment
import ko.hshun.movie.view.main.fragment.key.KeyFragment
import ko.hshun.movie.view.main.fragment.mypage.MypageFragment
import ko.hshun.movie.view.main.fragment.search.SearchFragment
import ko.hshun.movie.view.main.fragment.zzim.ZzimFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 홈, 키워드, 찜, 검색, 마이페이지 프래그먼트 메뉴 -> 프레임레이아웃 표시
        getNavItems()
    }

    fun getNavItems() {
        binding.navItems.setOnItemSelectedListener {
            val ft = supportFragmentManager.beginTransaction()

            when(it.itemId) {
                // 홈 화면
                R.id.menu_home -> {
                    ft.replace(R.id.frame, HomeFragment())
                    supportActionBar?.setTitle(R.string.string_home)
                }
                // 키워드 화면
                R.id.menu_key -> {
                    ft.replace(R.id.frame, KeyFragment())
                    supportActionBar?.setTitle(R.string.string_key)
                }
                // 찜 화면
                R.id.menu_zzim -> {
                    ft.replace(R.id.frame, ZzimFragment())
                    supportActionBar?.setTitle(R.string.string_zzim)
                }
                // 검색 화면
                R.id.menu_search -> {
                    ft.replace(R.id.frame, SearchFragment())
                    supportActionBar?.setTitle(R.string.string_search)
                }
                // 마이페이지 화면
                R.id.menu_mypage -> {
                    ft.replace(R.id.frame, MypageFragment())
                    supportActionBar?.setTitle(R.string.string_mypage)
                }

            }

            ft.commit()
            true
        }
        // 디폴트 값 -> 홈 화면
        binding.navItems.selectedItemId = R.id.menu_home

    }
}