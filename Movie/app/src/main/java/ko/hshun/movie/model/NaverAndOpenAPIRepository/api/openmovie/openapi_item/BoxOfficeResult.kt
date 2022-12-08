package ko.hshun.movie.model.NaverAndOpenAPIRepository.api.openmovie.openapi_item

data class BoxOfficeResult(
    val boxofficeType: String?,
    val dailyBoxOfficeList: List<DailyBoxOffice?>?,
    val showRange: String?
)