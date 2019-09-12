class Stuff {
}

inline fun <reified T> membersOf() = T::class.members//.filter { kCallable -> kCallable.name.contains("insert") }


fun main(args: Array<String>) {
    println(membersOf<StringBuilder>().joinToString("\n"))
    println(membersOf<String>().joinToString("\n"))
}