package ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NaverClient {

    var naverClient: Retrofit? = null

    fun getNaverClient(baseUrl: String): Retrofit? {
        if (naverClient == null) {
            naverClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return naverClient
    }
}