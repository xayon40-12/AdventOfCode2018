package Day6

import Utils.inputlines
import kotlin.math.abs

data class Coord(var x: Int = 0, var y: Int = 0, var area: Int = 0){
    constructor(str: String): this(){
        val c = str.split(", ")
        x = c[0].toInt()
        y = c[1].toInt()
    }

    fun distance(x: Int, y: Int) = abs(x-this.x)+abs(y-this.y)
}

fun nearest(x: Int, y: Int, coords: List<Coord>): Coord?{
    var dist = coords[0].distance(x,y)
    var coord: Coord? = coords[0]
    for (c in coords) {
        val tmp = c.distance(x,y)
        if (tmp < dist) {
            dist = tmp
            coord = c
        }else if(tmp == dist && coord != c){
            coord = null
        }
    }

    return coord
}

fun main(args: Array<String>) {
    val coords = inputlines(6).map { Coord(it) }
    //find left right up down coordinates considering the origin at the top left
    val l = coords.minBy { it.x }!!.x
    val r = coords.maxBy { it.x }!!.x
    val u = coords.minBy { it.y }!!.y
    val d = coords.maxBy { it.y }!!.y

    var inShortArea = 0
    for(y in u..d) {
        for (x in l..r) {
            nearest(x, y, coords)?.also {
                if (y == u || y == d || x == l || x == r) it.area = -1
                if (it.area >= 0) it.area++
            }

            if(coords.sumBy { it.distance(x,y) } < 10000) inShortArea++
        }
    }



    println(coords.maxBy { it.area }!!.area)
    println(inShortArea)
}