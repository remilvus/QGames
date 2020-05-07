import scala.util.Random
import scala.collection.mutable.HashMap

class Agent(val discount: Double, val learning_rate: Double, val environment: Environment) {
    val Q_table : HashMap[String, Vector[Int]] = new HashMap[String, Vector[Int]]
    val rand = new Random

    def action(state: String,  possible_actions: Vector[Int],  random: Boolean = false) = { // returns new state, reward, is_done
        if (math.random < 1){
            val idx = rand.nextInt()
            rand.shuffle(possible_actions).head
        } else {
            0
        }
    }
    
    def update(state: String, action: Int) = { // updates Q dictionary

    }

    def save() = {

    }

    def load(filename: String) = {

    }
    
}

object Runner{
    
    def main(args: Array[String]){
        val agent = new Agent(0,0,null)
        for(i <- Vector(0,1,2,3)){
            println("action " ++ i.toString ++ " = "++ agent.action("a", Vector(1,2,3)).toString)
        }
    }
}