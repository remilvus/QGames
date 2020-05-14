import scala.collection.immutable.Vector

abstract class Environment () {
    var state = Array(0,0,0,0,0,0,0,0,0)
    var move = 0

    def step(action: Int) : (String, Float, Boolean) // returns new state, reward, is_done
    def reset(): String
    def possibleActions() : Vector[Int] // vector with possible actions
    def numberOfPossibleActions() : Int 
    def stateToString() : String
    def isCompleted() : Boolean

}

class TicTacToe() extends Environment{

    def stateToString () = { //przeksztalcenie state na string
        var res = ""
        for (i <- 0 to 8) if(state(i) == 1) res = res + "x" else if(state(i) == -1) res = res + "o" else res = res + "-"
        print(res)
        print('\n')
        res
    }

    def isCompleted () : Boolean = { // check czy partia sie skonczyla
        for(i <- 0 to 2) {
            if (state(3*i) == 1 && state(3*i+1) == 1 && state(3*i+2) == 1) return true
            if (state(i) == 1 && state(3+i) == 1 && state(6+i) == 1) return true
        }
        if (state(0) == 1 && state(4) == 1 && state(8) == 1) return true
        if (state(2) == 1 && state(4) == 1 && state(6) == 1) return true
        if (move == 9) return true
        return false
    }

    def step(action: Int) =  { //przemnozenie tablicy *-1, wykonanie kroku, zwrocenie res
        state = state.map(_ * -1)
        state(action) = 1
        move += 1
        (stateToString(), 1.0f, isCompleted)
    }

    def reset() = {
        state = Array(0,0,0,0,0,0,0,0,0)
        move = 0
        "---------"
    }

    def possibleActions() = {
        var res = Vector[Int]()
        for (i <- (0 to 8)) {
           if (state(i) == 0) res = res :+ i
        }
        //print(res)
        res
    }

    def numberOfPossibleActions() = {
        val res = {
            var tmp = 0
            for (i <- 0 to 8){
                if (state(i) == 0) tmp+=1
            }
            tmp
        }
        //print(res)
        res
    }
}