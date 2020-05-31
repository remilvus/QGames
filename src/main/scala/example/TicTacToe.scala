package Environment

import scala.collection.immutable.Vector
import Agent.Agent

abstract class Environment (player: Agent) {
    var state = Array(0,0,0,0,0,0,0,0,0)
    var move = 0

    def step(action: Int) : (String, Float, Boolean) // returns new state, reward, is_done
    def reset(): String
    def possibleActions() : Vector[Int] // vector with possible actions
    def numberOfPossibleActions() : Int 
    def stateToString() : String
    def isCompleted() : Boolean
    def visualise() : String // shows environment's state in readable format
}

class TicTacToe(player: Agent) extends Environment(player){

    def stateToString () = { //przeksztalcenie state na string
        var res = ""
        for (i <- 0 to 8) if(state(i) == 1) res = res + "x" else if(state(i) == -1) res = res + "o" else res = res + "-"
        res
    }

    def isCompleted () : Boolean = { // check czy partia sie skonczyla
        for(i <- 0 to 2) {
            if (state(3*i) != 0 && state(3*i) == state(3*i+1) && state(3*i+1)== state(3*i+2)) return true
            if (state(i) != 0 && state(i) == state(3+i) && state(3+i) == state(6+i)) return true
        }
        if (state(0) != 0 && state(0) == state(4) && state(8) == state(4)) return true
        if (state(2) != 0 && state(2) == state(4) && state(6) == state(4)) return true
        if (move == 9) return true
        return false
    }

    def playerWon () : Boolean = { // check czy partia sie skonczyla
        for(i <- 0 to 2) {
            if (state(3*i) == 1 && state(3*i+1) == 1 && state(3*i+2) == 1) return true
            if (state(i) == 1 && state(3+i) == 1 && state(6+i) == 1) return true
        }
        if (state(0) == 1 && state(4) == 1 && state(8) == 1) return true
        if (state(2) == 1 && state(4) == 1 && state(6) == 1) return true
        if (move == 9) return true
        return false
    }

    def getReward() : Float = {
        if(this.isCompleted()){
            if(this.playerWon){
                return 2
            }
            for(i <- 0 to 8) this.state(i) = this.state(i) * (-1)
            if(this.playerWon){
                return -8
            }
            2
         } else 0
    }


    def step(action: Int) =  { //przemnozenie tablicy *-1, wykonanie kroku, zwrocenie res
     //   state = state.map(_ * -1)
        state(action) = 1
        move += 1
   //     state = state.map(_ * -1)
        if(!this.isCompleted()){
            var inside_action = player.action(this.stateToString(), this.possibleActions())
            state(inside_action) = -1
            move += 1
        }

        (stateToString(), getReward, isCompleted) // todo: return positive reward on win/draw & negative reward on loss
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

    def visualise() = {
        val board = new StringBuilder(12)
        for (i <- 0 to 2){
            for (j <- 0 to 2) board.append(TicTacToe.tileToString(state(3*i+j)))
            board.append("\n")
        }
        board.toString()
    }

}

object TicTacToe{
    def tileToString(x: Int) : String = {
        x match{
            case 1 => "x"
            case -1 => "o"
            case _ => "-"
        }
    }
}