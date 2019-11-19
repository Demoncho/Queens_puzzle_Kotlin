import gen.*
import anneal.*


fun main (args :Array<String>){
    print("Enter size: ")
    val size = readLine()!!.toInt()
    val gen = GenAlgorithm(size)
    println("Genetic algorithm:")
    var start = System.nanoTime()
    gen.find_answer()
    var finish = System.nanoTime()
    println(((finish - start)/1000000000.0).toString() + " sec")

    val anneal = AnnealingMethod(size)
    println("Annealing method:")
    start = System.nanoTime()
    anneal.find_answer()
    finish = System.nanoTime()
    println(((finish - start)/1000000000.0).toString() + " sec")
}

/*Enter size: 500
Genetic algorithm:
Founded
Count of generations: 32277
268.7608727 sec
Annealing method:
Founded
179.490672 sec*/