

object Overseer{

    def main(args: Array[String]){
        val agent = new Q_Agent(discount=0.9, learning_rate=0.1)
        var env : Environment = new TicTacToe(agent)
        agent.num_of_action = env.numberOfPossibleActions

        var state : String = env.reset()
        var old_state : String = ""
        var done : Boolean = false
        var action : Int = 0
        val learning_iter = 100000
        for (i <- 1 to learning_iter){ // learning; may need more iterations
            while(!done){
                old_state = state
                action = agent.action(state, env.possibleActions())
                var (state_tmp, reward, done_tmp) = env.step(action)
                state = state_tmp  // *sad scala noises*
                done = done_tmp
                agent.update(old_state, state, action, reward, done)
            }
            done=false
            state = env.reset()
        }

        val human = new HumanAgent()
        var env2 = new TicTacToe(human)
        human.env = env2

        for (i <- 1 to 3){ // visualization
            while(!done){
                old_state = state
                action = agent.action(state, env2.possibleActions())
                var (state_tmp, _, done_tmp) = env2.step(action)
                state = state_tmp  // *sad scala noises*
                done = done_tmp
                println(env2.visualise)
            }
            done=false
            state = env2.reset()
        }

        agent.show()
    }
}