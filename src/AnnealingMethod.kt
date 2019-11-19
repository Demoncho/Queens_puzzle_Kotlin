package anneal

import kotlin.math.abs
import kotlin.math.exp

class AnnealingMethod(val size: Int){
    val temp_min = 0.00001
    var temp = 1000.0
    val steps_to_low_temp = 10000
    val temp_factor = 0.9
    var status = arrayListOf<Int>()
    init{
        for (i in 0..size-1)
            status.add(i)
        status.shuffle()
    }

    private fun energy(current: ArrayList<Int>): Int{
        var sum = 0
        for (i in 0..current.size-1)
            for (j in i+1..current.size-1)
                if (abs(i - j) == abs(current[i]-current[j]))
                    sum++
        return sum
    }

    private fun new_solution(): ArrayList<Int>{
        val i = (0..size-1).random()
        var j = (0..size-1).random()
        while(i==j)
            j = (0..size-1).random()

        val result = arrayListOf<Int>()

        for (k in 0..size-1)
            result.add(status[k])

        val temp = result[i]
        result[i] = result[j]
        result[j] = temp
        return result
    }

    private fun print_desk() {
        if (size > 15) {
            println("Founded")
            //println("Count of generations: " + count_of_generations.toString())
            return
        }
        for (i in 0..size - 1) {
            for (j in 0..size - 1)
                if (status[i] != j)
                    print(" 0 ")
                else print(" 1 ")
            println()
        }
        // println("Count of generations: " + count_of_generations.toString())
    }

     fun find_answer(){
         var step = 0
         while (temp > temp_min && energy(status)!=0) {
             step++
             for (i in 0..steps_to_low_temp - 1) {
                 val solution = new_solution()
                 if (energy(solution) < energy(status))
                     status = solution
                 else {
                     val chance = Math.pow(Math.E, -(energy(solution) - energy(status)) / temp)
                     val ran = Math.random()
                     if (ran < chance)
                         status = solution
                 }
             }
             temp = temp * temp_factor / step
         }
         if (energy(status) == 0)
             print_desk()
         else
             println("Bad temperature")
    }



}