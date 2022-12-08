package ko.hshun.movie.model.NaverAndOpenAPIRepository.api.openmovie

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//레트로핏 클라이언트 생성
object OepnAPIClient {

    private var openAPIClient : Retrofit? = null

    fun getOpenClient(baseUrl: String) : Retrofit? {
        if (openAPIClient == null) {
            openAPIClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return openAPIClient
    }
}