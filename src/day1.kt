package Day1

import Utils.inputlines
import java.io.FileReader

fun main(args: Array<String>) {
    //Part 1)
    //get the array of input lines and map it to an array og Int
    val input = inputlines(1).map { it.toInt() }
    //print the sum of each lines
    println(input.reduce { acc, i -> acc+i })

    //part 2)
    //create a set to store each reached frequency
    var reached = mutableSetOf<Int>()
    var f = 0 //variable that stores the current frequency
    var i = 0
    //continue to add the next input frequency cyclically until the current frequency already exists in the set
    while (reached.add(f)){
        f += input[i]
        i = (i+1)%input.size
    }
    println(f)
}