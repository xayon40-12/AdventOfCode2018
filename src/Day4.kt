package Day4

import Utils.inputlines

enum class Action{FALLSASLEEP, WAKESUP, SHIFT, NONE}

data class Event(var year: Int = 0, var month: Int = 0, var day: Int = 0, var hour: Int = 0, var minute: Int = 0,
                 var totalmin: Int = 0, var action: Action = Action.NONE, var id: Int = 0){
    constructor(str: String): this(){
        val values = Regex("""\[(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})\] ([^ #]+) ?#?(\d+)?.*""").matchEntire(str)!!.groupValues
        year = values[1].toInt()
        month = values[2].toInt()
        day = values[3].toInt()
        hour = values[4].toInt()
        minute = values[5].toInt()
        totalmin = minute + 60*(hour + 24*(day + 31*(month+ 12*year)))
        action = when(values[6]){
            "falls" -> Action.FALLSASLEEP
            "wakes" -> Action.WAKESUP
            "Guard" -> {
                id = values[7].toInt()
                Action.SHIFT
            }
            else -> Action.NONE
        }
    }

    operator fun compareTo(e: Event): Int{
        return totalmin-e.totalmin
    }
}

data class Guard(var id: Int, var records: IntArray = IntArray(60, {0})){
    var lastFallAsleepMinute = 0
    var numberOfMinutesSlept = 0
    var mostSleptMinute = 0
    var mostSleptMinuteOccurence = 0
    fun fallingAsleep(minute: Int){
        lastFallAsleepMinute = minute
    }

    fun wakingUp(minute: Int){
        for (i in lastFallAsleepMinute..minute-1)
            records[i]++

        numberOfMinutesSlept = records.reduce { acc, i ->  acc + i}
        mostSleptMinuteOccurence = records.max()!!
        mostSleptMinute = records.indexOf(mostSleptMinuteOccurence)
    }
}

fun main(args: Array<String>) {
    val events = inputlines(4).map { Event(it) }.sortedWith(compareBy({it.year},{it.month},{it.day},{it.hour},{it.minute}))

    val guardsShift = HashMap<Int, Guard>()
    var id = 0
    for(e in events)
        when(e.action){
            Action.SHIFT -> {
                id = e.id
                if(!guardsShift.containsKey(id))  guardsShift[id] = Guard(id)
            }
            Action.FALLSASLEEP -> guardsShift[id]?.fallingAsleep(e.minute)
            Action.WAKESUP -> guardsShift[id]?.wakingUp(e.minute)
        }

    val guards = guardsShift.values
    val mostSleepingGuard = guards.maxWith(compareBy{it.numberOfMinutesSlept})!!
    println(mostSleepingGuard.id * mostSleepingGuard.mostSleptMinute)
    val mostSameMinuteSleepingGuard = guards.maxWith(compareBy{it.mostSleptMinuteOccurence})!!
    println(mostSameMinuteSleepingGuard.id * mostSameMinuteSleepingGuard.mostSleptMinute)
}