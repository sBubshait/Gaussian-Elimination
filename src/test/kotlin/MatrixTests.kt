package gaussianelimination

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class MatrixTests {

    private val m1 = Matrix(
        listOf(
            Vector(listOf(1.0, 2.0, 3.0, 0.5, 1.0)),
            Vector(listOf(0.0, 1.0, 0.0, 2.0, 3.0)),
            Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)),
            Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
        ),
    )

    @Test
    fun `get matrix dimensions`() {
        assertEquals(5, m1.numColumns)
        assertEquals(4, m1.numRows)
    }

    @Test
    fun `get column`() {
        assertEquals(Vector(listOf(1.0, 0.0, 1.0, 2.0)), m1.getColumn(0))
        assertEquals(Vector(listOf(2.0, 1.0, 0.0, 0.0)), m1.getColumn(1))
        assertEquals(Vector(listOf(3.0, 0.0, 1.0, 1.0)), m1.getColumn(2))
        assertEquals(Vector(listOf(0.5, 2.0, 2.0, 1.0)), m1.getColumn(3))
        assertEquals(Vector(listOf(1.0, 3.0, 4.0, 1.0)), m1.getColumn(4))
    }

    @Test
    fun `get rows`() {
        assertEquals(Vector(listOf(1.0, 2.0, 3.0, 0.5, 1.0)), m1[0])
        assertEquals(Vector(listOf(0.0, 1.0, 0.0, 2.0, 3.0)), m1[1])
        assertEquals(Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)), m1[2])
        assertEquals(Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)), m1[3])

        // getRow is synonymous.
        assertEquals(Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)), m1.getRow(2))
        assertEquals(Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)), m1.getRow(3))
    }

    @Test
    fun `matrix addition`() {
        val m2 = Matrix(
            listOf(
                Vector(listOf(2.0, 3.0, 0.0, 1.0, 1.0)),
                Vector(listOf(1.0, 2.0, 1.0, 2.0, 1.0)),
                Vector(listOf(0.0, 1.0, 1.0, 2.0, 2.0)),
                Vector(listOf(1.0, 2.0, 1.0, 1.0, 1.0)),
            ),
        )

        val sum = Matrix(
            listOf(
                Vector(listOf(3.0, 5.0, 3.0, 1.5, 2.0)),
                Vector(listOf(1.0, 3.0, 1.0, 4.0, 4.0)),
                Vector(listOf(1.0, 1.0, 2.0, 4.0, 6.0)),
                Vector(listOf(3.0, 2.0, 2.0, 2.0, 2.0)),
            ),
        )

        assertEquals(sum, m1 + m2)
    }

    @Test
    fun `matrix multiplication`() {
        val m2 = Matrix(
            listOf(
                Vector(listOf(2.0, 3.0)),
                Vector(listOf(1.0, 2.0)),
                Vector(listOf(4.0, 1.0)),
                Vector(listOf(0.0, 1.0)),
                Vector(listOf(1.0, 3.0)),
            ),
        )

        val product = Matrix(
            listOf(
                Vector(listOf(17.0, 13.5)),
                Vector(listOf(4.0, 13.0)),
                Vector(listOf(10.0, 18.0)),
                Vector(listOf(9.0, 11.0)),
            ),
        )

        assertEquals(product, m1 * m2)
    }

    val scaled = Matrix(
        listOf(
            Vector(listOf(.10, .20, .40, .05, .10)),
            Vector(listOf(.00, .10, .00, .20, .40)),
            Vector(listOf(.10, .00, .10, .20, .40)),
            Vector(listOf(.20, .00, .10, .10, .10)),
        ),
    )

    @Test
    fun `left multiply by scalar`() {
        val m1 = Matrix(
            listOf(
                Vector(listOf(1.0, 2.0, 4.0, 0.5, 1.0)),
                Vector(listOf(0.0, 1.0, 0.0, 2.0, 4.0)),
                Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)),
                Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
            ),
        )



        assertEquals(scaled, 0.1 * m1)
    }

    @Test
    fun `right multiply by scalar`() {
        val m1 = Matrix(
            listOf(
                Vector(listOf(1.0, 2.0, 4.0, 0.5, 1.0)),
                Vector(listOf(0.0, 1.0, 0.0, 2.0, 4.0)),
                Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)),
                Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
            ),
        )

        assertEquals(scaled, m1 * 0.1)
    }

    @Test
    fun `transpose`() {
        val m1 = Matrix(
            listOf(
                Vector(listOf(1.0, 2.0, 4.0, 0.5, 1.0)),
                Vector(listOf(0.0, 1.0, 0.0, 2.0, 4.0)),
                Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)),
                Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
            ),
        )
        val transposed = Matrix(
            listOf(
                Vector(listOf(1.0, 0.0, 1.0, 2.0)),
                Vector(listOf(2.0, 1.0, 0.0, 0.0)),
                Vector(listOf(4.0, 0.0, 1.0, 1.0)),
                Vector(listOf(0.5, 2.0, 2.0, 1.0)),
                Vector(listOf(1.0, 4.0, 4.0, 1.0)),
            ),
        )
        assertEquals(transposed, m1.transpose())
    }

    @Test
    fun `can add a multiple of a row into another`() {
        val m1 = Matrix(
            listOf(
                Vector(listOf(1.0, 2.0, 4.0, 0.5, 1.0)),
                Vector(listOf(0.0, 1.0, 0.0, 2.0, 4.0)),
                Vector(listOf(1.0, 0.0, 1.0, 2.0, 4.0)),
                Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
            ),
        )
        val m2 = Matrix(
            listOf(
                Vector(listOf(1.0, 2.0, 4.0, 0.5, 1.0)),
                Vector(listOf(0.0, 1.0, 0.0, 2.0, 4.0)),
                Vector(listOf(0.0, -2.0, -3.0, 1.5, 3.0)),
                Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
            ),
        )
        assertEquals(m2, m1.addMultipleOfRow(2, 0, -1.0))
    }

    @Test
    fun `string  representation`() {
        val m1 = Matrix(
            listOf(
                Vector(listOf(1.46, 2.0, 4.0, 0.5, 1.0)),
                Vector(listOf(0.0, 1.0, 100.0, 2.0, 4.0)),
                Vector(listOf(1.0, 0.0, 1.0, 2020.12, 4.0)),
                Vector(listOf(2.0, 0.0, 1.0, 1.0, 1.0)),
            ),
        )
        println(m1.toString())
        val stringRepresentation =
            """
                [ 1.46 2.0   4.0     0.5 1.0 ]
                [  0.0 1.0 100.0     2.0 4.0 ]
                [  1.0 0.0   1.0 2020.12 4.0 ]
                [  2.0 0.0   1.0     1.0 1.0 ]
            """.trimIndent()

        assertEquals(stringRepresentation, m1.toString())
    }

    @Test
    fun `exception - negative row index`() {
        try {
            Matrix(listOf(Vector(listOf(1.0, 1.0)), Vector(listOf(1.0, 1.0)))).getRow(-1)
            fail("IndexOutOfBoundsException was expected.")
        } catch (exception: IndexOutOfBoundsException) {
            // Good: exception was expected.
        }
    }

    @Test
    fun `exception - negative column index`() {
        try {
            Matrix(listOf(Vector(listOf(1.0, 1.0)), Vector(listOf(1.0, 1.0)))).getColumn(-1)
            fail("IndexOutOfBoundsException was expected.")
        } catch (exception: IndexOutOfBoundsException) {
            // Good: exception was expected.
        }
    }

    @Test
    fun `exception - too large column index`() {
        try {
            Matrix(listOf(Vector(listOf(1.0, 1.0)), Vector(listOf(1.0, 1.0)))).getColumn(2)
            fail("IndexOutOfBoundsException was expected.")
        } catch (exception: IndexOutOfBoundsException) {
            // Good: exception was expected.
        }
    }

    @Test
    fun `exception - too large row index`() {
        try {
            Matrix(listOf(Vector(listOf(1.0, 1.0)), Vector(listOf(1.0, 1.0)))).getRow(2)
            fail("IndexOutOfBoundsException was expected.")
        } catch (exception: IndexOutOfBoundsException) {
            // Good: exception was expected.
        }
    }

    @Test
    fun `exception - inappropriate indices for get`() {
        try {
            Matrix(listOf(Vector(listOf(1.0, 1.0)), Vector(listOf(1.0, 1.0))))[2, 2]
            fail("IndexOutOfBoundsException was expected.")
        } catch (exception: IndexOutOfBoundsException) {
            // Good: exception was expected.
        }
    }

    @Test
    fun `exception - addition of two matrices with different dimensions`() {
        try {
            val m2 = Matrix(listOf(Vector(listOf(1.0, 1.0)), Vector(listOf(1.0, 1.0))))
            m1 + m2
            fail("UnsupportedOperationException was expected.")
        } catch (exception: UnsupportedOperationException) {
            // Good: exception was expected.
        }
    }

    @Test
    fun `exception - add matrices with different row counts`() {
        val m1 = Matrix(listOf(Vector(listOf(1.0, 1.0)), Vector(listOf(1.0, 1.0))))
        val m2 = Matrix(listOf(Vector(listOf(1.0, 1.0))))
        try {
            m1 + m2
            fail("UnsupportedOperationException was expected")
        } catch (exception: UnsupportedOperationException) {
            // Good: exception was expected.
        }
    }

    @Test
    fun `exception - multiply matrices with incompatible sizes`() {
        val m1 = Matrix(listOf(Vector(listOf(1.0, 1.0)), Vector(listOf(1.0, 1.0))))
        val m2 = Matrix(
            listOf(
                Vector(listOf(1.0, 1.0, 1.0)),
                Vector(listOf(1.0, 1.0, 1.0)),
                Vector(listOf(1.0, 1.0, 1.0)),
            ),
        )
        try {
            m1 * m2
            fail("UnsupportedOperationException was expected")
        } catch (exception: UnsupportedOperationException) {
            // Good: exception was expected.
        }
    }
}
