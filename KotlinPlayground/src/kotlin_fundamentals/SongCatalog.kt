package kotlin_fundamentals


class Song(private val title: String, private val artist: String, private val yearPublished: Int, private val playCount: Int) {

    private val popularity: Boolean
        get() = playCount >= 1000

    fun printSongDescription() {
        println("$title, performed by $artist, was released in $yearPublished.")
    }

    fun getPopularity() {
        println(popularity)
    }
}

fun main() {
    val songOne = Song("Mocking bird", "Eminem", 2003, 1001)

    songOne.printSongDescription()
    songOne.getPopularity()
}