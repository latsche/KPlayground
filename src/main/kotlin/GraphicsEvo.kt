
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Line2D
import javax.swing.JPanel
import javax.swing.JFrame
import javax.swing.Timer

var lineStart= 20f

class Success : JFrame() {
    init {
        val panel = JPanel()
        contentPane.add(panel)
        setSize(450, 450)
    }

    override fun paint(g: Graphics) {
        super.paint(g)  // fixes the immediate problem.
        val g2 = g as Graphics2D
        val lin = Line2D.Float( lineStart, 100f, 250f, 260f)
        g2.draw(lin)
    }
}

fun main(args: Array<String>) {
    val s = Success()
    s.isVisible = true

    val timer = Timer(40) { s.repaint()}
    timer.start()

    for(i in 1 until 40) {
        Thread.sleep(50)
        lineStart += 5
    }
}