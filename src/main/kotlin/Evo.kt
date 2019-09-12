import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.lang.String.format
import java.util.Random
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

val MAX_VECTOR = 700.0
val MAX_POP = 10
val MAX_CITIES = 20
val MAX_AGE = 20

lateinit var sol: MutableList<Individual>
lateinit var drawPop: Individual

val random = Random()

val vectorRand = Random(123)
val cities = List<Vector>(MAX_CITIES) { randomVector(vectorRand) }
//val cities = List<Vector>(MAX_CITIES) { circleVector(vectorRand, 300.0) }


data class Vector(val x: Double, val y: Double) {
    fun getDistance(v: Vector) = Math.sqrt(Math.pow(abs(v.x - x), 2.0) + Math.pow(abs(v.y - y), 2.0))

    override fun toString() = "x: $x, y: $y"
}

class Individual(val p: MutableList<Vector>) {

    var age = MAX_AGE

    override fun toString() = "route length: ${length()}"

    fun verify() {
        var valid = p.size == cities.size && p.containsAll(cities) && cities.containsAll(p)
        if (!valid) {
            throw Exception("$p ${p.size}")
        }
    }

    fun length(): Double {
        var sum = p.last().getDistance(p.first())
        for (i in 0 until p.size - 1) {
            sum += p[i].getDistance(p[i + 1])
        }

        return sum
    }

    fun doAge() {
        age++
    }
}

fun recomb(sol: MutableList<Individual>, forceBest: Boolean = false) {

    var pos1 = random.nextInt(sol.size / 2)
    if (forceBest) {
        pos1 = 0
    }
    val pos2 = random.nextInt(sol.size)

    val solCopy = sol.toMutableList()
    solCopy.sortBy { it -> it.length() }
    val pop1 = solCopy[pos1]
    val pop2 = sol[pos2]
    var pos = random.nextInt(pop1.p.size)
    while (pos == 0 || pos == pop1.p.size) {
        pos = random.nextInt(pop1.p.size)
    }

    var cop1 = pop1.p.toMutableList()
    var cop2 = pop2.p.toMutableList()
    cop1 = cop1.subList(0, pos)

    cop2.removeAll { cop1.contains(it) }

    cop2.addAll(cop1)

    val pop = Individual(cop2)
    pop.verify()
    sol.add(pop)
}

fun kill(sol: MutableList<Individual>) {
    sol.sortBy { it.length() }

    sol.removeIf { it.age == 0 }

    while (sol.size > MAX_POP) {
        sol.removeAt(sol.size - 1)
    }
}

fun evoSim(sol: MutableList<Individual>): MutableList<Individual> {
    //mutate
    for (i in 0 until 2) {
        sol[random.nextInt(sol.size)].p.swap()
    }

    //recombinate
    for (i in 0 until 8) {
        recomb(sol)
    }
    //reoombinate best
    for (i in 0 until 1) {
        recomb(sol, true)
    }

    //kill
    kill(sol)

    return sol
}

class Draw : JFrame() {
    init {
        val panel = JPanel()
        contentPane.add(panel)
        setSize(800, 800)
    }

    override fun paint(g: Graphics) {
        super.paint(g)  // fixes the immediate problem.
        val g2 = g as Graphics2D
        g2.translate(50, 50)

        //draw path
        val pop = drawPop.p
        val lin = Line2D.Float(
            pop.last().x.toFloat(),
            pop.last().y.toFloat(),
            pop.first().x.toFloat(),
            pop.first().y.toFloat()
        )
        g2.draw(lin)

        for (i in 0 until pop.size - 1) {
            val lin =
                Line2D.Float(pop[i].x.toFloat(), pop[i].y.toFloat(), pop[i + 1].x.toFloat(), pop[i + 1].y.toFloat())
            g2.draw(lin)
            g2.drawOval(pop[i].x.toInt() - 10, pop[i].y.toInt() - 10, 20, 20)
        }
    }
}

fun main(args: Array<String>) {

    cities.forEach { println(it.x) }
    println(Individual(cities.toMutableList()).length())

    sol = MutableList<Individual>(MAX_POP) { Individual(cities.shuffled().toMutableList()) }
    drawPop = sol[0]

    //set up draw
    val s = Draw()
    s.isVisible = true
    val timer = Timer(40) { s.repaint() }
    timer.start()

    //evo loop
    for (i in 0 until 10000000) {
        //show current population
        if (i % 100 == 0) {
            print(format("%09d", i))
            drawPop = sol.minBy { it -> it.length() }!!
            println("  average: ${sol.map { it -> it.length() }.average()}, size: ${sol.size}, best: ${drawPop.length()}")
        }

        Thread.sleep(30)
        sol = evoSim(sol)
    }

    timer.stop()

    println("X ************************")
    sol.minBy { it -> it.length() }!!.p.forEach { println(it.x) }

    println("Y ************************")
    sol.minBy { it -> it.length() }!!.p.forEach { println(it.y) }
}

//****************** HELPER *************
fun MutableList<Vector>.swap() {
    val i1 = random.nextInt(this.size)
    val i2 = random.nextInt(this.size)
    val tmp = this[i1] // 'this' corresponds to the list
    this[i1] = this[i2]
    this[i2] = tmp
}

fun randomVector(rand: Random): Vector {
    return Vector(rand.nextDouble() * MAX_VECTOR, rand.nextDouble() * MAX_VECTOR)
}

fun circleVector(rand: Random, length: Double): Vector {
    val a = rand.nextDouble() * 2 * PI
    return Vector(length * cos(a), length * sin(a))
}