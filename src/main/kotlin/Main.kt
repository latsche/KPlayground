import java.io.File
import java.lang.StringBuilder
import java.util.Base64

fun plainString() {
    var s = ""
    for(i in 1..100000) {
        s = "bla" + "blub"
    }
}

fun buildString() {
    var s = StringBuilder()
    for(i in 1..100000) {
        s = s.append("bla").append("blub")
    }
}


fun giveBlub(b:IBlub):IBlub = b

fun main(args: Array<String>) {

    var stringList = listOf("bla", "blub", "blo")
    //println(stringList)

    var now = System.currentTimeMillis()
    plainString()
    println(System.currentTimeMillis() - now)

    now = System.currentTimeMillis()
    buildString()
    println(System.currentTimeMillis() - now)


    val subBlub = SubBlub(size = 3, name = "subBlub")
    val otherSub = OtherSubBlub(size = 0, name = "otherSubBlub")

    val res =

    return


    var numList = listOf(2, 6, 7, 4, 9, 11, 12, 14)
    var groups = numList.groupBy {
        if (it > 10) "bla" else
            if (it and 1 == 0) "even" else "odd"

    }

    println(groups)

    val (evens, odds) = numList.partition {
        it and 1 == 0
    }

    var list = ArrayList<Sound>()
    //println("$evens, $odds")
    with(list) {
        add(callStuff(30))
        add(callStuff(60, "sixties"))
        add(callStuff(45, "fortyFive"))
    }

    println(list)

    val (up, low) = list.partition {
        it.vol > 30
    }

    println("$up, $low")

    val sum: (Int, Int) -> Int = { x, y -> x + y }
    println(sum(1, 3))


    var orig = "blablub"

    orig = orig.also { it.reversed() }
//            .also { println(" ${it.replace("b", "d")} ${it.length} ${it.reversed()}") }
            .let { it.reversed() }
            .also { it.reversed() }
    println(orig)


    val list1 = listOf<List<String>>(listOf("a", "b", "c"), listOf("a", "b", "c"), listOf("a", "b", "c"), listOf("a", "b", "c"), listOf("a", "b", "c"), listOf("a", "b", "c"))

    val bla = list1.mapIndexed { idx, entry -> entry [idx % 3]}


    println(bla)

    val path = "/home/thomas/java1.log" // already downloaded to current directory
    val bytes = File(path).readBytes()
    val content = File(path).readText()
    println(content)


    val base64 = Base64.getEncoder().encodeToString(bytes)
    println(base64)
    bytes.forEach { print(it) }
    println()

    Base64.getDecoder().decode(base64).forEach { print(it) }
}

val lazyValue:String by lazy {
    println("Heyho ")
    "ended"
}

fun callStuff(vol: Int, name: String = "default"): Sound {
    return Sound(vol, "möp")
}