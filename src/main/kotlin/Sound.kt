class Sound (public var vol:Int, private val name: String) {

    init {
        println("Sound initialized with $vol and $name")
    }

    fun makeSound(name: String) {
        println("this is Sound ${this.name} callin' with vol $vol! this does probably fit the $name")
    }

    public override fun toString(): String {
        return this.name + ": " + this.vol
    }
}