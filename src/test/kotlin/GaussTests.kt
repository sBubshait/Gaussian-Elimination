package gaussianelimination

import kotlin.test.Test
import kotlin.test.assertEquals
class GaussTests {

    @Test
    fun `can reduce a system of linear equations to Reduced REF`() {
        val m = Matrix(
            listOf(
                Vector(listOf(0.0, 2.0, 3.0, 4.0)),
                Vector(listOf(2.0, -1.0, 1.0, 0.0)),
                Vector(listOf(3.0, -2.0, 1.0, 2.0)),
            )
        )
        val v = Vector(listOf(13.0, 8.0, 13.0))
        val augmented = AugmentedMatrix(m, v)
        val gauss = Gauss(augmented)
        val steps = gauss.reduceToRREF()
        assertEquals(9, steps.size)
        assertEquals("R1 <--> R2", steps[0].step)
        assertEquals("R1 --> 0.5 * R1", steps[1].step)
        assertEquals("R3 --> R3 - 3.0 * R1", steps[2].step)
        assertEquals("R2 --> 0.5 * R2", steps[3].step)
        assertEquals("R1 --> R1 + 0.5 * R2", steps[4].step)
        assertEquals("R3 --> R3 + 0.5 * R2", steps[5].step)
        assertEquals("R3 --> 4.0 * R3", steps[6].step)
        assertEquals("R1 --> R1 - 1.25 * R3", steps[7].step)
        assertEquals("R2 --> R2 - 1.5 * R3", steps[8].step)

        val expectedMatrix = AugmentedMatrix(
            Matrix(
                listOf(
                    Vector(listOf(1.0, 0.0, 0.0, -14.0)),
                    Vector(listOf(0.0, 1.0, 0.0, -16.0)),
                    Vector(listOf(0.0, 0.0, 1.0, 12.0)),
                )
            ),
            Vector(-14.0, -19.0, 17.0)
        )
    }
}