
abstract class Environment () {
    //var State
    def step(action: Int) : (String, Float, Boolean) // returns new state, reward, is_done
    def reset(): String
    def possibleActions() : Vector[Int] // vector with possible actions
    def numberOfPossibleActions() : Int 

}

class TicTacToe() extends Environment{
    def step(action: Int)=  {("x", 1.0f, true)}
    def reset() = "x"
    def possibleActions() = {Vector[Int](0)}
    def numberOfPossibleActions() =  {0}
}