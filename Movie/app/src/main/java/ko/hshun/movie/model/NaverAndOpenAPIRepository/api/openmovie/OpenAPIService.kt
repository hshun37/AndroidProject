package ko.hshun.movie.model.NaverAndOpenAPIRepository.api.openmovie

import ko.hshun.movie.model.NaverAndOpenAPIRepository.api.openmovie.openapi_item.BoxOfficeItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenAPIService {
    @GET("searchDailyBoxOfficeList.json")
    fun getMovie(
        @Query("key") key : String,
        @Query("targetDt") targetDt: Int
    ) : Call<BoxOfficeItem>
}