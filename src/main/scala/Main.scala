
import Environment._
import Agent._

package Main{

object Controller{

    def teach(agent: Q_Agent, env: Environment, iter: Int){
        // traing agent for `iter` iterations
        println("training agent...")
        var state = env.reset()
        var old_state = ""
        var done = false
        var action = 0
        var first = false
        for (i <- 1 to iter){ // training loop
            while(!done){
                old_state = state
                action = agent.action(state, env.possibleActions())
                var (state_tmp, reward, done_tmp) = env.step(action)
                state = state_tmp 
                done = done_tmp
                agent.update(old_state, state, action, reward, done)
            }
            done=false
            state = env.reset()
            first = !first
            if(first){
                state = env.start()
            }

        }
        println("training ended")
    }

    def play(current_env: Environment){
        
        var old_state : String = ""
        var done : Boolean = false
        var action : Int = 0
        val human = new HumanAgent()
        val env = current_env match{
            case _: ConnectFour => new ConnectFour(human)
            case _: TicTacToe => new TicTacToe(human)
        }

        var state : String = env.reset()
        human.env = env
        var reward: Float = 0

         while(!done){
            old_state = state
            action = agent.action(state, env.possibleActions(), optimal=true)
            val (state_tmp, reward_tmp, done_tmp) = env.step(action)
            state = state_tmp 
            done = done_tmp
            reward = reward_tmp
        }
        println(env.visualise())

        reward match{ // reward is given from perspective of the agent
            case env.lose_reward => println("You won!!!")
            case env.win_reward => println("You lost")
            case env.draw_reward => println("Draw")
            case _ => // should never happen
        }

        done=false
        state = env.reset()

    }

    def test(){
        println("training agent...")
        var state : String = env.reset()
        var old_state : String = ""
        var done : Boolean = false
        var action : Int = 0
        var lost = 0
        var draw = 0
        for (i <- 1 to 1000){ // learning; may need more iterations
            while(!done){
                old_state = state
                action = agent.action(state, env.possibleActions(), optimal=true)
                var (_, reward, done_tmp) = env.step(action)
                done = done_tmp
                if(env.lose_reward == reward) lost += 1
                if(env.draw_reward == reward) draw += 1
            }
            done = false
            state = env.reset()

        }
        println("losses: " + lost.toString + "| draws: " + draw.toInt + " (out of 1000)")
    }

    def select(name: String) = {
        name match {
            case TicTacToe.name => {
                    agent = new Q_Agent(discount=0.6, learning_rate=0.03)
                    env = new TicTacToe(agent)
                    agent.num_of_action = env.numberOfPossibleActions
                }
            case ConnectFour.name => {
                    agent = new Q_Agent(discount=0.6, learning_rate=0.03)
                    env = new ConnectFour(agent)
                    agent.num_of_action = env.numberOfPossibleActions
                }
            case _ => println("Incorrect name")

        }
    }

    val training_pattern = raw"train (\d\d*)".r
    val select_pattern = raw"select (\w\w*)".r
    var agent = new Q_Agent(discount=0.6, learning_rate=0.03)
    var env : Environment = new TicTacToe(agent)
    agent.num_of_action = env.numberOfPossibleActions

    def main(args: Array[String]){

        while(true){
            val input = readLine("> ")
            input match {
                case training_pattern(v) if v.toInt > 0 => teach(agent, env, v.toInt)
                case select_pattern(name) => select(name)
                case "play" => play(env)
                case "test" => test()
                case "exit" => sys.exit(0)
                case _ => println("Bad command")
            }
        }

        // var state : String = env.reset()
        // var old_state : String = ""
        // var done : Boolean = false
        // var action : Int = 0
        // val learning_iter = 100 //000
        // for (i <- 1 to learning_iter){ // learning; may need more iterations
        //     while(!done){
        //         old_state = state
        //         action = agent.action(state, env.possibleActions())
        //         var (state_tmp, reward, done_tmp) = env.step(action)
        //         state = state_tmp  // *sad scala noises*
        //         done = done_tmp
        //         agent.update(old_state, state, action, reward, done)
        //     }
        //     done=false
        //     state = env.reset()
        // }


        // agent.show()
    }
}

}