

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
                var (state_1, reward, done_1) = env.step(action)
                state = state_1
                done = done_1 // doesnt want to work without it
                if(!done) agent.update(old_state, state, action, reward)
            }
            done=false
            state = env.reset()
        }
    }
}