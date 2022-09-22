package ko.hshun.developernews.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ko.hshun.developernews.MainActivity
import ko.hshun.developernews.R
import ko.hshun.developernews.api.NewsAPI
import ko.hshun.developernews.api.NewsData
import ko.hshun.developernews.api.NewsService
import ko.hshun.developernews.databinding.ActivityNewMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class NewMainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dataBinding: ActivityNewMainBinding
    private lateinit var news: NewsAPI
//    private lateinit var newsData: MutableList<NewsData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_main)

        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("news")

        myRef.setValue("Hello, World!")

        auth = Firebase.auth
        val logout = dataBinding.logout

        //HTTP API
        val today = GregorianCalendar()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DATE)

        val BASE_URL = "https://newsapi.org/v2/"
        val q = "개발"
        val from = "${year}-${month}-${day}"
        val sortBy = "publishedAt"
        val apiKey = "2877eb398044448fa38c7fe1fe98ef0a"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: NewsService = retrofit
            .create<NewsService>(NewsService::class.java)

        val listCall: Call<NewsAPI> = service.getNews(
            q, from, sortBy, apiKey
        )

        listCall.enqueue(object : Callback<NewsAPI> {
            lateinit var newsData: List<NewsData>

            override fun onResponse(call: Call<NewsAPI>, response: Response<NewsAPI>) {
                news = response.body()!!
//                for(i: Int in 1..10) {
//                    Log.d("List10", "${news.articles[i]}")
//                }
                //        RecyclerView
                dataBinding.recyclerview.layoutManager = LinearLayoutManager(baseContext)
                dataBinding.recyclerview.adapter = NewsAdpater(baseContext, news.articles)
                dataBinding.recyclerview.addItemDecoration(
                    DividerItemDecoration(
                        baseContext,
                        LinearLayoutManager.VERTICAL
                    )
                )


            }

            override fun onFailure(call: Call<NewsAPI>, t: Throwable) {
                Log.e("Error", t!!.message.toString())
            }
        })

//        Log.d("newsData", "${newsData}")


//        listCall.enqueue(object : Callback<NewsAPI> {
//            override fun onResponse(call: Call<NewsAPI>, response: Response<NewsAPI>) {
//                if (response.isSuccessful) {
//                    val newsAPI: NewsAPI? = response.body()
//                    Log.i("ResponsResult", "${newsAPI}")
//                } else {
//                    val rc = response.code()
//                    when (rc) {
//                        400 -> {
//                            Log.e("Error 400", "Bad Connection")
//                        }
//                        404 -> {
//                            Log.e("Error 404", "Not Found")
//                        }
//                        else -> {
//                            Log.e("Error", "Generic Error")
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<NewsAPI>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })

        //로그아웃
        logout.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this, "로그아웃 하셨습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}