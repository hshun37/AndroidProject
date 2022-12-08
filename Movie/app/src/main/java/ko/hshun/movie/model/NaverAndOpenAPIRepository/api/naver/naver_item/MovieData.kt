package ko.hshun.movie.model.NaverAndOpenAPIRepository.api.naver.naver_item

data class MovieData(
    val display: Int?,
    val items: List<Item?>?,
    val lastBuildDate: String?,
    val start: Int?,
    val total: Int?
)