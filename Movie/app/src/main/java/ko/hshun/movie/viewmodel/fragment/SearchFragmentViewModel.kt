package ko.hshun.movie.viewmodel.fragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import ko.hshun.movie.databinding.FragmentSearchBinding
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver.NaverAPIService
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver.NaverClient
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver.naver_item.MovieData
import ko.hshun.movie.utils.NAVER_BASE_URL
import ko.hshun.movie.viewmodel.adapter.SearchMovieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragmentViewModel: ViewModel() {

    private val TAG = "SearchFragmentViewModel"
    fun getMovieSearch(binding: FragmentSearchBinding) {
        binding.searchBtn.setOnClickListener {
            val title = binding.searchName.text.toString()
            val context = binding.root.context

            if (title == "") {
                Toast.makeText(context, "영화제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val instant : NaverAPIService? = NaverClient.getNaverClient(NAVER_BASE_URL.NAVER_URL)?.create(
                    NaverAPIService::class.java)
                val instantCall : Call<MovieData> = instant!!.getPoster(query = title, display = 100)
                instantCall.enqueue(object : Callback<MovieData> {
                    override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                        val body = response.body()
                        if (body != null) {
                            val items = body.items
                            binding.rv.layoutManager = GridLayoutManager(context, 2)
                            binding.rv.adapter = SearchMovieAdapter(items)
                        } else {
                            Toast.makeText(context, "검색어를 받아오지 못했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<MovieData>, t: Throwable) {
                        Log.d(TAG, "error : $t")
                    }
                })
                binding.searchName.setText("")
            }
        }
    }
}