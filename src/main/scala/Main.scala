package Main

import Agent._
import Environment._

object Controller{

    def teach(agent: Q_Agent, env: Environment, iter: Int){
        println("training agent...")
        var state : String = env.reset()
        var old_state : String = ""
        var done : Boolean = false
        var action : Int = 0
        for (i <- 1 to iter){ // learning; may need more iterations
            while(!done){
                old_state = state
                action = agent.action(state, env.possibleActions())
                
                println("styp")
                var (state_tmp, reward, done_tmp) = env.step(action)
                 println("stasdadwdyp")
                state = state_tmp 
                done = done_tmp
                agent.update(old_state, state, action, reward, done)
            }
            done=false
            state = env.reset()
        }
        println("training ended")
    }

    def play(){
        
        var old_state : String = ""
        var done : Boolean = false
        var action : Int = 0
        val human = new HumanAgent()
        val env = new ConnectFour(human)
        var state : String = env.reset()
        human.env = env


         while(!done){
            old_state = state
            action = agent.action(state, env.possibleActions(), optimal=true)
            var (state_tmp, _, done_tmp) = env.step(action)
            state = state_tmp 
            done = done_tmp
            println(env.visualise)
        }
            done=false
            state = env.reset()

    }

    def test(){
        println("training agent...")
        var state : String = env.reset()
        println("reset not failed")
        var old_state : String = ""
        var done : Boolean = false
        var action : Int = 0
        var lost = 0
        for (i <- 1 to 1000){ // learning; may need more iterations
            while(!done){
                println("episode")
                old_state = state
                println("actt")
                action = agent.action(state, env.possibleActions(), optimal=true)
                 println("sepp")
                var (_, reward, done_tmp) = env.step(action)
                done = done_tmp
                if (reward < 0) lost += 1
            }
            done=false
            state = env.reset()
        }
        println("agent lost " + lost.toString + " out of 1000 games")
    }

    val training_pattern = raw"train (\d\d*)".r
    val agent = new Q_Agent(discount=0.6, learning_rate=0.03)
    var env : Environment = new ConnectFour(agent)

    def main(args: Array[String]){
        
        agent.num_of_action = env.numberOfPossibleActions

        while(true){
            val input = readLine("> ")
            input match {
                case training_pattern(v) if v.toInt > 0 => teach(agent, env, v.toInt)
                case "play" => play()
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