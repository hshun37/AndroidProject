package ko.hshun.developernews.api

data class NewsData(
    val source: NewsSite,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)
