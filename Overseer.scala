

object Overseer{

    // endless loop
    // training or game based on user input
    // during training one agent may be learning or not
     // when both agents are learning there may only be one instance playing as both
    def main(args: Array[String]){
        val env : Environment = new TicTacToe()
        val agent = new Agent(discount=0.9, learning_rate=0.1, environment=env)
        var state : String = env.reset()
        var old_state : String = ""
        var reward : Float = 0.0f
        var done : Boolean = false
        var action : Int = 0
        for (i <- 1 to 10){
            while(!done){
                old_state = state
                action = agent.action(state, env.possibleActions())
                var (state_tmp, reward, done_tmp) = env.step(action)
                state = state_tmp  // *sad scala noises*
                done = done_tmp

                println(env.visualise)
                agent.update(old_state, state, action, reward, done)


                // old_state_1 = state_1
                // action_1 = agent_1.action(state_1, env.possibleActions())
                // var (state_tmp, reward_2, done_tmp) = env.step(action_1)
                // state_2 = state_tmp  // *sad scala noises*
                // done_2 = done_tmp
                // println(env.visualise)
                // action_2 = agent_2.action(state_2, env.possibleActions())
                // var (state_tmp, reward_1, done_tmp) = env.step(action_1)
                // state_1 = state_tmp  // *sad scala noises*
                // done_1 = done_tmp

                // println(env.visualise)
                // agent_1.update(old_state_1, state_1, action_1, reward_1, done_1)
                // agent_1.update(old_state, state, action, reward, done)


            }
            done=false
            state = env.reset()
        }
    }
}