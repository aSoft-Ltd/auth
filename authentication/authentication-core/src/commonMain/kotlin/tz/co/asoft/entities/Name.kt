@file:Suppress("PackageDirectoryMismatch")

package tz.co.asoft

class Name(private val value: String = "") {
    private val parts get() = value.split(" ")

    val full get() = value

    val first get() = parts.getOrElse(0) { "" }

    val middle: String
        get() = if (parts.size > 2) {
            parts.subList(1, parts.size - 1).joinToString(" ")
        } else {
            ""
        }


    val last: String
        get() = if (parts.size > 1) {
            parts.last()
        } else {
            ""
        }

    val firstLast get() = "$first $last"

    fun formated(): String = value.toLowerCase().split(" ").joinToString(" ") { it.capitalize() }

    fun randomized() = Name(parts.shuffled().joinToString(" "))

    companion object {
        val fakeNames = arrayOf("Raiden", "Anderson", "Hanzo", "Lameck", "Hasashi", "Kenshi", "Takeda", "Jackson", "Sonya", "Tremor", "Kotal", "Khan", "Cassie", "Johnny", "Cage", "Kabal", "Enenra", "Cyrax", "Sektor", "Jean", "T'Challa", "T'Chaka", "Okoye", "Wakabi")
        val fake
            get() = if ((0..10).random() < 5) {
                "${fakeNames.random()} ${fakeNames.random()}"
            } else {
                "${fakeNames.random()} ${fakeNames.random()} ${fakeNames.random()}"
            }
    }
}