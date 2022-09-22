package ko.hshun.developernews.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("everything")
    fun getNews(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") appid: String,
    ): Call<NewsAPI>
}