package o1.adventure

import world._


/** The class `Adventure` represents text adventure games. An adventure consists of a player and 
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of "hard-coded" information which pertain to a very 
  * specific adventure game that involves a small trip through a twisted forest. All newly created 
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure 
  * games, you will need to modify or replace the source code of this class. 
  */
class Adventure {
  //TODO REPLACE FUCKING EVERYTHING
  /** The title of the adventure game. */
  val title = "The Game"
    
  val world = World

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */ 
  def isOver = World.player.hasQuit || this.isComplete
//
//  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = " This is 'Otaniemi-game'."

    
  /** Returns a message that is to be displayed to the player at the end of the game. The message 
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete) {
      "Yuo're Winner"
//    } 
      //else if (this.turnCount == this.timeLimit) {
//      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
    } else { // game over due to player quitting
      "Good bye" 
    }
  }
  
  
  def isComplete = World.player.has("antidote") 
  
  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual 
    * report of what happened, or an error message if the command was unknown. In the latter 
    * case, no turns elapse. */
  def playTurn(command: String) = {
    World.passTime() 
    val action = new Action(command)
    if (World.combat.isEmpty){
      val outcomeReport = action.execute(World.player)
      if (outcomeReport.isDefined) { 
      }
      outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
    }
    else{
      val action = new Action(command)
      if (action.BattleExecute(World.player)){
        var report = ""
        do{
          report += World.combat.get.playTrun()
        }while (!(World.combat.get.getNext() == world.player) && !World.combat.get.checkIfOver() )
        
        report + World.combat.get.end()
        
      }
      else{
        World.combat.get.info()
      }
    }
  }
  if (this.isComplete){
    
  }
}

