import scala.util.Random
import scala.collection.mutable.HashMap

abstract class Agent {
    def action(state: String,  possible_actions: Vector[Int],  random: Boolean = false) : Int
}

class HumanAgent extends Agent{
    var env : Environment = null
    def action(state: String,  possible_actions: Vector[Int],  random: Boolean = false) = {
        println(env.visualise)
        val input = readLine("> ")
        input.toInt
    }
}

class Q_Agent(val discount: Double, val learning_rate: Double) extends Agent {
    val Q_table : HashMap[String, Array[Float]] = new HashMap[String, Array[Float]]
    val rand = new Random
    var num_of_action : Int = 0

    def action(state: String,  possible_actions: Vector[Int],  random: Boolean = false) = { // returns new state, reward, is_done
        if (math.random < 0.2 || !(Q_table.keySet.exists(_ == state) || random)){
            rand.shuffle(possible_actions).head
        } else {
            Q_table(state).zipWithIndex.filter(val_idx => possible_actions.contains(val_idx._2)).maxBy(_._1)._2 // gets index of action with max value
        }
    }
    
    def update(state: String, next_state: String, action: Int, reward: Float, done: Boolean) = { // updates Q dictionary
        val first_update = Q_table.keySet.exists(_ == state)

        val next_value = if(done) 0 else Q_table.getOrElse(next_state, Array[Float](0)).max
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

    def show() = {
        for (elem <- this.Q_table.view) println(elem._1.toString +"|"+ elem._2.mkString(", ")) 
    }
    
}
