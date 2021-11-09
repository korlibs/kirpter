package sample

fun main() {
	val ORLD = "orld"
	val WORLD = "W$ORLD"
	println("Hello")
	println("Hello" == "W$ORLD")
	System.exit(if ("Hello" == "W$ORLD") 0 else -1)
}
