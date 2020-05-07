
class Agent(val discount: Double, val learning_rate: Double, val: Environment) {
    var Q_table : dictionary

    def action(val state: String, val possible_actions: Bool, val random=False){ // returns new state, reward, is_done

    }
    
    def update(val state: String, val action: Int){ // updates Q dictionary

    }
    
}