package ko.hshun.movie.utils

import java.util.*

object Util {}

// BASE_URL
object OPENAPI_URL {
    const val OPENAPI_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/" // 영화진흥위원회 OPEN API
}

object NAVER_BASE_URL {
    const val NAVER_URL = "https://openapi.naver.com" // 네이버 OPEN API
}

// NAVER AccessToken
object NaverAccessToken {
    const val id = "" // naverAPI ID 발급
    const val secret = "" // naverAPI 시크립 번호 발급
}

// BoxOffice AccessToken and DATE
object OfficeBox {
    val today = GregorianCalendar()
    val year = today.get(Calendar.YEAR)
    val month = today.get(Calendar.MONTH) + 1
    val day = if (today.get(Calendar.DATE) < 10) "0${today.get(Calendar.DATE)-1}" else today.get(Calendar.DATE)-1 // 한자리 일수 => 앞자리 0X AND 두자리 일수 => XX

    const val key = "" // 영화진흥위원회 OPEN API 키 발급
    val targetDt = "$year${month}${day}" // yyyyMMdd 형식
}