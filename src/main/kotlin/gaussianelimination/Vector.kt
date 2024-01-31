package gaussianelimination

data class Vector(private val elements: List<Double>) {

    constructor(vararg elems: Double) : this(elems.asList())

    init {
        require(elements.isNotEmpty(), { "Vector must contain at least one element" })
    }

    val length
        get() = elements.size

    operator fun get(index: Int): Double = elements.getOrNull(index) ?: throw IndexOutOfBoundsException("Index $index is out of bounds for Vector of length $length")

    operator fun plus(other: Vector): Vector = if (this.length != other.length) {
        throw UnsupportedOperationException("Cannot add Vectors of different lengths")
    } else {
        Vector(this.elements.zip(other.elements).map { (x, y) -> x + y })
    }

    operator fun times(scalar: Double): Vector = Vector(this.elements.map { x -> scalar * x })

    infix fun dot(other: Vector): Double = if (this.length != other.length) {
        throw UnsupportedOperationException("Cannot dot Vectors of different lengths")
    } else this.elements.zip(other.elements) { x, y -> x * y }.sum()
    // we could have also used a fold :)

    operator fun iterator(): Iterator<Double> = elements.iterator()

    override fun toString(): String = "(${this.elements.joinToString(", ")})"
}

operator fun Double.times(v: Vector): Vector = v * this