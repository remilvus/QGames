package Environment

import Agent.Agent
import scala.util.control.Breaks._
import scala.collection.immutable.Vector

abstract sealed case class Environment (player: Agent) {
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
    var state = Array(0,0,0,0,0,0,0,0,0)

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
            state = state.map(_ * -1)
            if(this.playerWon){
                state = state.map(_ * -1)
                return -8
            }
            state = state.map(_ * -1)
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
    val name = "TicTacToe"

    def tileToString(x: Int) : String = {
        x match{
            case 1 => "x"
            case -1 => "o"
            case _ => "-"
        }
    }
}




class ConnectFour(player: Agent)  extends Environment(player){
    var state = Array.ofDim[Int](6, 7)
    val name = "ConnectFour"

    def stateToString () = { //przeksztalcenie state na string
        var res = ""
        for (i <- 0 to 41) if(state(i/7)(i%7) == 1) res = res + "x" else if(state(i / 7)(i % 7) == -1) res = res + "o" else res = res + " "
        res
    }

    def isCompleted () : Boolean = { // check czy partia sie skonczyla
        if (move < 7) return false

        for(x <- 0 to 6) // sprawdzanie pionowych linii
            for(y <- 0 to 2) {
                if(state(y)(x) != 0 && state(y)(x) == state(y+1)(x) && state(y+2)(x) == state(y+3)(x) && state(y+1)(x) == state(y+3)(x)) return true
            }

        for(x <- 0 to 5) // sprawdzanie poziomych linii
            for(y <- 0 to 3) {
                if(state(x)(y) != 0 && state(x)(y) == state(x)(y+1) && state(x)(y+2) == state(x)(y+3) && state(x)(y+1) == state(x)(y+3)) return true
            }
        
        for(x <- 0 to 3) // sprawdzanie linii na ukos '/'
            for(y <- 0 to 2) {
                if(state(y)(x) != 0 && state(y)(x) == state(y+1)(x+1) && state(y+2)(x+2) == state(y+3)(x+3) && state(y+1)(x+1) == state(y+3)(x+3)) return true
            }
        
         for(x <- 0 to 3) // sprawdzanie linii na ukos '\'
            for(y <- 0 to 2) {
                if(state(5-y)(6-x) != 0 && state(5-y)(6-x) == state(5-(y+1))(6-(x+1)) && state(5-(y+2))(7-(x+2)) == state(5-(y+3))(6-(x+3)) && state(5-(y+1))(6-(x+1)) == state(y+3)(x+3)) return true
            }

        if (move == 42) return true;
        return false
    }

    def playerWon () : Boolean = { // check czy partia sie skonczyla
        if (move < 7) return false

        for(x <- 0 to 6) // sprawdzanie pionowych linii
            for(y <- 0 to 2) {
                if(state(y)(x) == 1 && state(y+1)(x) == 1 && state(y+2)(x) == 1 && state(y+3)(x) == 1) return true
            }

        for(x <- 0 to 5) // sprawdzanie poziomych linii
            for(y <- 0 to 3) {
                if(state(x)(y) == 1 && state(x)(y+1) == 1 && state(x)(y+2) == 1 && state(x)(y+3) == 1) return true
            }
        
        for(x <- 0 to 3) // sprawdzanie linii na ukos '/'
            for(y <- 0 to 2) {
                if(state(y)(x) == 1 && state(y+1)(x+1) == 1&& state(y+2)(x+2) == 1 && state(y+3)(x+3) == 1) return true
            }
        
         for(x <- 0 to 3) // sprawdzanie linii na ukos '\'
            for(y <- 0 to 2) {
                if(state(5-y)(6-x) == 1 && state(5-(y+1))(6-(x+1)) == 1 && state(5-(y+2))(6-(x+2)) == 1 && state(5-(y+3))(6-(x+3)) == 1) return true
            }
        return false;
    }

    def getReward() : Float = {
        if(this.isCompleted()){
            if(this.playerWon){
                return 2
            }
            for(i <- 0 to 5) state(i) = state(i).map(_ * -1)
            if(this.playerWon){
                for(i <- 0 to 5) state(i) = state(i).map(_ * -1)
                return -8
            }
            for(i <- 0 to 5) state(i) = state(i).map(_ * -1)
            2
         } else 0
    }


    def step(action: Int) =  { //przemnozenie tablicy *-1, wykonanie kroku, zwrocenie res
     //   state = state.map(_ * -1)

        breakable{
            for (i <- 0 to 5) {
                if (state(i)(action) == 0) {
                    state(i)(action) = 1
                    break
                }
            }
        }
        move += 1
        
        if(!this.isCompleted()){
            var inside_action = player.action(this.stateToString(), this.possibleActions())
            breakable{
                for (i <- 0 to 5) {
                if (state(i)(inside_action) == 0) {
                    state(i)(inside_action) = -1
                    break
                }
            }
        }
            move += 1
        }

        (stateToString(), getReward, isCompleted) // todo: return positive reward on win/draw & negative reward on loss
    }

    def reset() : String = {
        state = Array.ofDim[Int](6, 7)
        move = 0
        "---------"
    }

    def possibleActions() = {
        var res = Vector[Int]()
        for (i <- (0 to 6)) {
           if (state(5)(i) == 0) res = res :+ i
        }
        //print(res)
        res
    }

    def numberOfPossibleActions() = {
        val res = {
            var tmp = 0
            for (i <- 0 to 6){
                if (state(5)(i) == 0) tmp+=1
            }
            tmp
        }
        //print(res)
        res
    }

    def visualise() = {
        val board = new StringBuilder(48)
        for (i <- 0 to 5){
            for (j <- 0 to 6) board.append(TicTacToe.tileToString(state(5-i)(j)))
            board.append("\n")
        }
        board.toString()
    }

}


object ConnectFour{
    val name = "ConnectFour"
}