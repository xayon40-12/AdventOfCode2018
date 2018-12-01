package Utils

import java.io.FileReader

//return the whole file stored in ressources corresponding to day number "day"
fun input(day: Int): String{
    return FileReader("ressources/Day"+day+"input.txt").use { it.readText() }
}

//return a list of each lines in the file corresponding of the day number "day"
fun inputlines(day: Int): List<String>{
    return input(day).split("\n");
}