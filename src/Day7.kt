package Day7

import Utils.inputlines

data class Step(var letter: Char = ' ', var next: MutableList<Step> = mutableListOf(), var pred: MutableList<Step> = mutableListOf()){

}

fun extract(str: String): Pair<Char, Char> {
    val list = str.split(" ")
    return Pair(list[1][0], list[7][0])
}


fun main(args: Array<String>) {
    val instructions = inputlines(7).map { extract(it) }
    val mapOfSteps = mutableMapOf<Char, Step>()
    for(p in instructions){
        if(!mapOfSteps.containsKey(p.first)) mapOfSteps[p.first] = Step(p.first)
        if(!mapOfSteps.containsKey(p.second)) mapOfSteps[p.second] = Step(p.second)
        mapOfSteps[p.first]!!.next.add(mapOfSteps[p.second]!!)
        mapOfSteps[p.second]!!.pred.add(mapOfSteps[p.first]!!)
    }
    var steps = mapOfSteps.values

    var available = mutableListOf<Step>()
    var done = mutableListOf<Step>()
    for(s in steps) if(s.pred.isEmpty()) available.add(s)

    var res = ""

    while(done.size != steps.size){
        val tmp = available.minBy { it.letter }!!
        res += tmp.letter
        done.add(tmp)
        available.remove(tmp)
        for(d in done){
            for(n in d.next)
                if(done.containsAll(n.pred) && !done.contains(n) && !available.contains(n)) available.add(n)
        }
    }

    println(res)

    available = mutableListOf<Step>()
    done = mutableListOf<Step>()
    var doing = mutableListOf<Step>()
    for(s in steps) if(s.pred.isEmpty()) available.add(s)

    data class Worker(var s: Int = 0, var step: Step = Step())
    val workers = Array(5,{Worker()})
    var time = 0
    while(done.size != steps.size){
        for(w in workers){
            if(w.s <= 0){
                if(available.size > 0){
                    val tmp = available.minBy { it.letter }!!
                    w.s =  tmp.letter.minus('A')+1 + 60
                    w.step = tmp
                    available.remove(tmp)
                    doing.add(tmp)
                }
            }
            w.s--
            if(w.s == 0) if(!done.contains(w.step)){
                done.add(w.step)
                doing.remove(w.step)
            }
        }

        for(d in done){
            for(n in d.next)
                if(done.containsAll(n.pred) && !done.contains(n) && !available.contains(n) && !doing.contains(n)) available.add(n)
        }
        time++
    }

    println(time)
}