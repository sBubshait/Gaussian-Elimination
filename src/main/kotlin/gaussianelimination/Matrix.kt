package gaussianelimination

data class Matrix(val elements: List<Vector>) {
    constructor(vararg elems: Vector) : this(elems.asList())

    init {
        require(elements.isNotEmpty()) { "Matrix should contain at least one vector" }
        require(elements.none { it.length != numColumns }) { "Row vectors must all be of the same length" }
    }

    val numRows
        get() = elements.size

    val numColumns
        get() = elements[0].length

    operator fun get(index: Int): Vector = elements.getOrNull(index) ?: throw IndexOutOfBoundsException("Cannot get row $index in a matrix with $numRows rows")

    fun getRow(index: Int): Vector = this[index]

    fun getColumn(index: Int): Vector = if (index >= numColumns) {
        throw IndexOutOfBoundsException("Cannot get col $index in a matrix with $numColumns cols")
    } else {
        Vector(elements.map { it[index] })
    }

    private val columns
        get() = (0..<numColumns).map { getColumn(it) }

    operator fun get(i: Int, j: Int) = this[i][j]

    operator fun plus(other: Matrix): Matrix = if (this.numRows != other.numRows || this.numColumns != other.numColumns) {
        throw UnsupportedOperationException("Cannot add two matrices with different dimensions")
    } else {
        Matrix(this.elements.zip(other.elements) { x, y -> x + y })
    }

    operator fun times(other: Matrix): Matrix = if (this.numColumns != other.numRows) {
        throw UnsupportedOperationException("Inappropriate dimensions for matrix multiplication")
    } else {
        Matrix(this.elements.map { v -> Vector(other.columns.map { u -> v dot u }) })
    }

    operator fun times(scalar: Double): Matrix = Matrix(this.elements.map { it * scalar })

    operator fun iterator(): Iterator<Vector> = elements.iterator()

    fun transpose(): Matrix = Matrix(this.columns)

    // Elementary Row Operations:
    fun swapRows(i: Int, j: Int): Matrix {
        val newElements = elements.toMutableList()
        newElements[i] = elements[j]
        newElements[j] = elements[i]
        return Matrix(newElements)
    }

    fun multiplyRow(i: Int, scalar: Double): Matrix {
        val newElements = elements.toMutableList()
        newElements[i] = elements[i] * scalar
        return Matrix(newElements)
    }

    fun addMultipleOfRow(i: Int, j: Int, scalar: Double): Matrix {
        val newElements = elements.toMutableList()
        newElements[i] = elements[i] + (elements[j] * scalar)
        return Matrix(newElements)
    }

    override fun toString(): String {
        val sps: List<Int> =
            columns.map { c -> (0 until c.length).maxOf { c[it].toString().length } }

        return elements.map { r ->
            "[ " + (0 until r.length).map { i ->
                String.format("%${sps[i]}s", r[i].toString())
            }.joinToString(" ") + " ]"
        }.joinToString("\n")
    }
}

operator fun Double.times(m: Matrix): Matrix = m * this