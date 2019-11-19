package gen

import kotlin.math.abs

fun ArrayList<Int>.newgen(){

}

class GenAlgorithm(val size: Int) {
    val count_of_genes = 20
    var count_of_generations = 0
    var not_found = true
    var generation = arrayListOf<ArrayList<Int>>()
    init {
        for (i in 0..count_of_genes){
            generation.add(arrayListOf())
            for (j in 0..size-1)
                generation[i].add(j)
            generation[i].shuffle()

        }
    }

    private fun hits(gen: ArrayList<Int>): Int{
        var sum = 0
        for (i in 0..gen.size-1)
            for (j in i+1..gen.size-1)
                if (abs(i - j)== abs(gen[i]-gen[j]))
                    sum++
        return sum
    }

    private fun mutation(gen: ArrayList<Int>){
        val i = (0..size-1).random()
        var j = (0..size-1).random()
        while(i==j)
            j = (0..size-1).random()
        val temp = gen[i]
        gen[i] = gen[j]
        gen[j] = temp
    }

    private fun print_desk(gen: ArrayList<Int>){
        if (size>15){
            println("Founded")
            println("Count of generations: " + count_of_generations.toString())
            return
        }
        for (i in 0..gen.size-1) {
            for (j in 0..gen.size - 1)
                if (gen[i] != j)
                    print(" 0 ")
                else print(" 1 ")
            println()
        }
        println("Count of generations: " + count_of_generations.toString())
    }

    private fun next_gene(par1: ArrayList<Int>, par2: ArrayList<Int>): ArrayList<Int>{
        val next = arrayListOf<Int>()
        val dif = arrayListOf<Int>()
        for (i in 0..size-1)
            if (par1[i] == par2[i])
               next.add(par1[i])
            else{
                next.add(-1)
                dif.add(par1[i])
            }
        dif.shuffle()
        for (i in 0..size-1)
            if (next[i] == -1) {
                next[i] = dif[0]
                dif.removeAt(0)
            }
        return next
    }

    private fun new_generation() {
        val best = arrayListOf<Pair<Int, Int>>()
        for (i in 0..count_of_genes-1) {
            val dif = hits(generation[i])
            if (dif != 0)
                best.add(Pair(i, dif))
            else{
                not_found = false
                print_desk(generation[i])
                return
            }
        }

        best.sortBy { it.second  }
        best.take(count_of_genes/3 )
        val parents = arrayListOf<ArrayList<Int>>()
        val next_generation = arrayListOf<ArrayList<Int>>()
        for ( i in 0..count_of_genes/3-1) {
            parents.add (generation[best[i].first])
            next_generation.add(generation[best[i].first])
        }
        val mut_id1 = (count_of_genes/3 ..count_of_genes - 1).random()
        val mut_id2 = (count_of_genes/3 ..count_of_genes - 1).random()
        val mut_id3 = (count_of_genes/3 ..count_of_genes - 1).random()
        var count = 0
        for (i in 0..count_of_genes/3 - 1){
            for (j in i..count_of_genes/3 - 1){
                val kid = next_gene(parents[i],parents[j])
                if (count == mut_id1)
                    mutation(kid)
                else if (count == mut_id2)
                    mutation(kid)
                else if (count == mut_id3)
                    mutation(kid)
                next_generation.add(kid)
                count++
                if (count == count_of_genes - count_of_genes/3 )
                    break
            }
            if (count == count_of_genes - count_of_genes/3 )
                break
        }
        next_generation.reverse()
        generation = next_generation
    }

    fun find_answer(){
        while(not_found) {
            count_of_generations++
            new_generation()
        }
    }
}