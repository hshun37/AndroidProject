package ko.hshun.developernews.api

data class NewsAPI(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsData>
)
