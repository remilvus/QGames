

object Overseer{

    def main(args: Array[String]){
        val agent = new Agent(discount=0.9, learning_rate=0.1)
        val env : Environment = new TicTacToe(agent)

        var state : String = env.reset()
        var old_state : String = ""
        var done : Boolean = false
        var action : Int = 0
        val learning_iter = 1000
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

        for (i <- 1 to 3){ // visualization
            while(!done){
                old_state = state
                action = agent.action(state, env.possibleActions())
                var (state_tmp, _, done_tmp) = env.step(action)
                state = state_tmp  // *sad scala noises*
                done = done_tmp
                println(env.visualise)
            }
            done=false
            state = env.reset()
        }
    }
}