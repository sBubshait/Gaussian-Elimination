package gaussianelimination

import kotlin.test.Test
import kotlin.test.assertEquals

class AugmentedMatrixTests {

    private val m1 = Matrix(
        listOf(
            Vector(listOf(1.0, 2.0, 3.0, 0.5, 1.0)),
            Vector(listOf(0.0, 1.0, 0.0, 2.0, 3.0)),
            Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)),
            Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
        ),
    )

    @Test
    fun `print augmented matrix`() {

        assertEquals("""
                        [ 1.0 2.0 3.0 0.5 1.0  |  1.0 ]
                        [ 0.0 1.0 0.0 2.0 3.0  |  2.0 ]
                        [ 1.0 0.0 1.0 2.0 4.0  |  3.0 ]
                        [ 2.0 0.0 1.0 1.0 1.0  |  4.0 ]""".trimIndent()
        , AugmentedMatrix(m1, Vector(1.0, 2.0, 3.0, 4.0)).toString())

    }
}