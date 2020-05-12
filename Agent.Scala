import scala.util.Random
import scala.collection.mutable.HashMap

class Agent(val discount: Double, val learning_rate: Double, val environment: Environment) {
    val Q_table : HashMap[String, Array[Float]] = new HashMap[String, Array[Float]]
    val rand = new Random
    val num_of_action = 3// environment.num_of_action // UNCOMMENT

    def action(state: String,  possible_actions: Vector[Int],  random: Boolean = false) = { // returns new state, reward, is_done
        if (math.random < 0.2 || !(Q_table.keySet.exists(_ == state))){
            rand.shuffle(possible_actions).head
        } else {
            Q_table(state).zipWithIndex.maxBy(_._1)._2 // gets index of action with max value
        }
    }
    
    def update(state: String, next_state: String, action: Int, reward: Float) = { // updates Q dictionary
        val first_update = Q_table.keySet.exists(_ == state)

        val next_value = Q_table.getOrElse(next_state, Array[Float](0)).max
        val old_value = if(first_update) Q_table(state)(action) else 0

        val update_value = old_value + learning_rate * (reward + discount * next_value - old_value)

        val values = Q_table.getOrElse(state, new Array[Float](num_of_action))
        values(action) = update_value.toFloat
        Q_table.update(state, values)
    }

    def save() = {

    }

    def load(filename: String) = {

    }
    
}

object Runner{
    
    def main(args: Array[String]){
        val agent = new Agent(0.1,0.1,null)
        for(i <- Vector(0,1,1,1,1,1,1,1,1,1,1,2)){
            println("action " ++ i.toString ++ " = "++ agent.action(i.toString, Vector(0,1,2)).toString)
            agent.update(i.toString, i.toString, i, 1)
        }
    }
}