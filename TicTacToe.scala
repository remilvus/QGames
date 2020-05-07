
abstract class Environment () {
    var State

    def step(val action: Int) // returns new state, reward, is_done
    def reset()
    def possibleActions() // return bool vector
    def numberOfPossibleActions() : Int 

}

class TicTacToe() extends Environment