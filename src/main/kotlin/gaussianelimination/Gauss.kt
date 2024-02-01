package gaussianelimination

import kotlin.math.abs

data class GaussStep(val step: String, val matrix: AugmentedMatrix)
class Gauss(var matrix: AugmentedMatrix) {

    private val m
        get() = matrix.matrix1
    private val v
        get() = matrix.matrix2

    fun solve(): Vector {
        return solveWithSteps().last().matrix.matrix2[0]
    }

    fun solveWithSteps(): List<GaussStep> = reduceToRREF() // TODO: Actually solve the system :)

    fun reduceToRREF(): List<GaussStep> {
        val steps = mutableListOf<GaussStep>()
        for (i in 0..< m.numRows) {
            val pivotStep = makePivot(i)
            if (pivotStep != null) {
                steps += pivotStep
            }

            if (m[i][i] != 0.0 && m[i][i] != 1.0) {
                val scalar = 1 / m[i][i]
                matrix = matrix.multiplyRow(i, scalar)
                steps += GaussStep("R${i + 1} --> $scalar * R${i + 1}", matrix)
            }

            for (j in 0..< m.numRows) {
                if (j == i) continue
                if (m[j][i] == 0.0) continue
                val scalar = m[j][i] / m[i][i]
                matrix = matrix.addMultipleOfRow(j, i, -scalar)
                steps += GaussStep(printRowMultiplication(i, j, scalar), matrix)
            }

        }
        return steps
    }

    private fun makePivot(i: Int): GaussStep? {
        if (m[i][i] == 0.0) {
            for (j in i + 1..< m.numRows) {
                if (m[j][i] != 0.0) {
                    matrix = matrix.swapRows(i, j)
                    return GaussStep("R${i + 1} <--> R${j + 1}", matrix)
                }
            }
        }
        return null
    }

    private fun printRowMultiplication(i: Int, j: Int, scalar: Double): String {
        val sb = StringBuilder()
        sb.append("R${j + 1} --> R${j + 1} ")
        if (scalar > 0) {
            sb.append("- ")
        } else { // Pre: scalar != 0
            sb.append("+ ")
        }
        if (abs(scalar) != 1.0) {
            sb.append("${abs(scalar)} * ")
        }
        sb.append("R${i + 1}")

        return sb.toString()
    }
}