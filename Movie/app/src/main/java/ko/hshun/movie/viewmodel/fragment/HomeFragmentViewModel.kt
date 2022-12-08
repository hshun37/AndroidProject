package ko.hshun.movie.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import ko.hshun.movie.databinding.FragmentHomeBinding
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver.NaverAPIService
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver.NaverClient
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver.naver_item.MovieData
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.openmovie.OepnAPIClient
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.openmovie.OpenAPIService
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.openmovie.openapi_item.BoxOfficeItem
import ko.hshun.movie.utils.NAVER_BASE_URL
import ko.hshun.movie.utils.OPENAPI_URL
import ko.hshun.movie.utils.OfficeBox
import ko.hshun.movie.viewmodel.adapter.HomeMovieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel: ViewModel() {

    private val TAG = "HomeFragmentViewModel"
    private val naverAPIName = ArrayList<String>()
    private val naverAPIImage = ArrayList<String>()
    private val naverAPILink = ArrayList<String>()

    private var count = 0
    fun openMovieAPI(binding: FragmentHomeBinding) {

        val officeBox: OpenAPIService? =
            OepnAPIClient.getOpenClient(OPENAPI_URL.OPENAPI_URL)?.create(OpenAPIService::class.java)
        val officeCall: Call<BoxOfficeItem> =
            officeBox?.getMovie(key = OfficeBox.key, targetDt = OfficeBox.targetDt.toInt())!!
        officeCall.enqueue(object : Callback<BoxOfficeItem> {
            override fun onResponse(call: Call<BoxOfficeItem>, response: Response<BoxOfficeItem>) {
                val list = response.body()?.boxOfficeResult?.dailyBoxOfficeList
                for (data in list!!) {
                    val title = data?.movieNm.toString()
                    //박스오피스 일간순위 영화제목을 naverAPI로 옮겨준다.
                    //여기서 영화진흥위원회 오픈API는 일간순위 갯수가 10로 고정값이다.
                    //여기서 받은 영화제목으로 naverAPI를 10번 호출하면서 count 값 또한 1씩 증가한다.
                    //title, image, link 값을 10번 받으며 ArrayList 값에 저장한다.
                    //count 갯수가 10번을 다 돌면 ArrayList 값들을 Adapter로 넘겨준다.
                    naverAPI(title, binding)
                }
            }

            override fun onFailure(call: Call<BoxOfficeItem>, t: Throwable) {
                Log.d(TAG, "ERROR : $t")
            }
        })
    }
    private fun naverAPI(title: String, binding: FragmentHomeBinding) {
        val instant: NaverAPIService? =
            NaverClient.getNaverClient(NAVER_BASE_URL.NAVER_URL)
                ?.create(NaverAPIService::class.java)
        val movieCall: Call<MovieData> = instant?.getPoster(query = title, display = 1)!!
        movieCall.enqueue(object : Callback<MovieData> {
            override fun onResponse(
                call: Call<MovieData>,
                response: Response<MovieData>
            ) {
                val body = response.body()
                if (body != null) {
                    val name = body.items!![0]?.title!!.replace("<b>", "").replace("</b>", "")
                    val image = body.items[0]?.image!!
                    val link = body.items[0]?.link!!
                    naverAPIName.add(name)
                    naverAPIImage.add(image)
                    naverAPILink.add(link)

                    count++
                    if (count == 10) {
                        binding.rv.layoutManager = GridLayoutManager(binding.root.context, 2)
                        binding.rv.adapter = HomeMovieAdapter(naverAPIName, naverAPIImage, naverAPILink)
                    }
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                Log.d(TAG, "error : $t")
            }
        })
    }
}