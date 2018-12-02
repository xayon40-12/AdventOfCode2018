package Day2

import Utils.inputlines

fun checksum(lines: List<String>): Int{
    var sum2 = 0
    var sum3 = 0

    for (line in lines){
        var characters = IntArray(27, {0})
        for (c in line){
            characters[c-'a']++
        }
        for(i in characters) if(i == 2) {sum2++; break;}
        for(i in characters) if(i == 3) {sum3++; break;}
    }
    return sum2*sum3
}

fun countDifferences(l1: String, l2: String): Int{
    var sum = 0
    for (i in 0..(l1.length-1)){
        if(l1[i] != l2[i]) sum++
    }

    return sum
}

fun similarPart(l1: String, l2: String): String{
    var id = 0
    for (i in 0..(l1.length-1)){
        if(l1[i] != l2[i]) {
            id = i
            break;
        }
    }

    return l1.removeRange(id, id+1);
}

fun findSimilar(lines: List<String>): String{
    var similar = ""
    loop@ for(l1 in lines){
        for(l2 in lines){
            if(countDifferences(l1,l2) == 1) {
                similar = similarPart(l1, l2)
                break@loop
            }
        }
    }

    return similar
}

fun main(args: Array<String>) {
    val lines = inputlines(2)
    println(checksum(lines))
    println(findSimilar(lines))
}