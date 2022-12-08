package ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver

import ko.hshun.movie.utils.NaverAccessToken
import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver.naver_item.MovieData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverAPIService {

    @Headers(
        "X-Naver-Client-Id: ${NaverAccessToken.id}",
        "X-Naver-Client-Secret: ${NaverAccessToken.secret}"
    )
    @GET("/v1/search/movie.json")
    fun getPoster(
        @Query("query") query: String,
        @Query("display") display: Int
    ): Call<MovieData>
}