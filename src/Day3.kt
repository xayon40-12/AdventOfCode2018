package Day3

import Utils.inputlines

data class Claim(var id: Int = 0,var x: Int = 0, var y: Int = 0, var w: Int = 0, var h: Int = 0, var overlap: Boolean = false){
    constructor(str: String): this(){
        val values = str.substring(1).replace(Regex("[^0-9]+"), " ").split(" ").map { it.toInt() }
        id = values[0]
        x = values[1]
        y = values[2]
        w = values[3]
        h = values[4]
    }
}

fun main(args: Array<String>) {
    val claims = inputlines(3).map { Claim(it) }
    val fabric = Array(1000, {Array(1000, {0})})
    for(c in claims)
        for(y in c.y..(c.y+c.h-1))
            for(x in c.x..(c.x+c.w-1))
                if(fabric[y][x] == 0){
                    fabric[y][x] = c.id
                }else{
                    c.overlap = true;
                    if(fabric[y][x] > 0) claims[fabric[y][x]-1].overlap = true;
                    fabric[y][x] = -1;//it is important to put a special value to know that an overlap happened
                }

    var sum = 0
    for(y in 0..999)
        for(x in 0..999)
            if(fabric[y][x] == -1) sum++

    println(sum)

    for(c in claims)
        if(!c.overlap) println(c)
}