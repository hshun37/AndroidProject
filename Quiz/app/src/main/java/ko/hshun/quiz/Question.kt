package ko.hshun.quiz

data class Question(
    val id: Int,
    val question: String,
    val image: Int,
    val potionOne: String,
    val potionTwo: String,
    val potionThree: String,
    val potionFour: String,
    val currentAnswer: Int
)
