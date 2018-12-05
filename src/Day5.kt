package Day5

import Utils.input
import kotlin.math.abs
import kotlin.math.min

fun reduce(str: String): String{
    var tmp = str
    var i = 0
    while(i<tmp.length-1){
        if(abs(tmp[i]-tmp[i+1]) == abs('a'-'A')) {
            tmp = tmp.removeRange(i, i + 2)
            if(i != 0) i--
        }else
            i++
    }

    return tmp
}

fun main(args: Array<String>) {
    var polymer = input(5)

    println(reduce(polymer).length)

    var shortest = polymer
    var minLength = polymer.length
    for (c in 0..25){
        val length = reduce(polymer.replace(Regex("[${'A'+c}${'a'+c}]"), "")).length
        if(length < minLength) minLength = length
    }
    println(minLength)

}