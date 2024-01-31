package gaussianelimination

data class AugmentedMatrix(val matrix1: Matrix, val matrix2: Matrix) {

    constructor(matrix: Matrix, vector: Vector) : this(matrix, Matrix(vector).transpose())

    init {
        require(matrix1.numRows == matrix2.numRows) { "Matrices must have the same number of rows" }
    }

    fun swapRows(i: Int, j: Int): AugmentedMatrix {
        val newMatrix1 = matrix1.swapRows(i, j)
        val newMatrix2 = matrix2.swapRows(i, j)
        return AugmentedMatrix(newMatrix1, newMatrix2)
    }

    fun multiplyRow(i: Int, scalar: Double): AugmentedMatrix {
        val newMatrix1 = matrix1.multiplyRow(i, scalar)
        val newMatrix2 = matrix2.multiplyRow(i, scalar)
        return AugmentedMatrix(newMatrix1, newMatrix2)
    }

    fun addMultipleOfRow(i: Int, j: Int, scalar: Double): AugmentedMatrix {
        val newMatrix1 = matrix1.addMultipleOfRow(i, j, scalar)
        val newMatrix2 = matrix2.addMultipleOfRow(i, j, scalar)
        return AugmentedMatrix(newMatrix1, newMatrix2)
    }

    override fun toString(): String {
        val matrix1Printed = matrix1.toString().split("\n").map { it.dropLast(1) }
        val matrix2Printed = matrix2.toString().split("\n").map { it.drop(1) }
        return matrix1Printed.zip(matrix2Printed).joinToString("\n") { (row1, row2) -> "$row1 | $row2" }
    }
}

fun main() {
    val m1 = Matrix(
        listOf(
            Vector(listOf(1.0, 2.0, 3.0, 0.5, 1.0)),
            Vector(listOf(0.0, 1.0, 0.0, 2.0, 3.0)),
            Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)),
            Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
        ),
    )

    println(AugmentedMatrix(m1, Vector(1.0, 2.0, 3.0, 4.0)))
}