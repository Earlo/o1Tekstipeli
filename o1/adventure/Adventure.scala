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
    
//  private val middle      = new Area("Forest", "You are somewhere in the forest. There are a lot of trees here.\nBirds are singing.")
//  private val northForest = new Area("Forest", "You are somewhere in the forest. A tangle of bushes blocks further passage north.\nBirds are singing.")
//  private val southForest = new Area("Forest", "The forest just goes on and on.")
//  private val clearing    = new Area("Forest Clearing", "You are at a small clearing in the middle of forest.\nNearly invisible, twisted paths lead in many directions.")
//  private val tangle      = new Area("Tangle of Bushes", "You are in a dense tangle of bushes. It's hard to see exactly where you're going.")
//  private val home        = new Area("Home", "Home sweet home! Now the only thing you need is a working remote control.")
//  private val northPole   = new Area("The North Pole", "You find yourself at the North Pole. BRRR!")
//
//  private val destination = home    
//
//       middle.setNeighbors("north" -> northForest, "east" -> tangle, "south" -> southForest, "west" -> clearing   )
//  northForest.setNeighbors(/*"north" -> northPole,*/   "east" -> tangle, "south" -> middle,      "west" -> clearing   )
//  southForest.setNeighbors("north" -> middle,      "east" -> tangle, "south" -> southForest, "west" -> clearing   )
//     clearing.setNeighbors("north" -> northForest, "east" -> middle, "south" -> southForest, "west" -> northForest)
//       tangle.setNeighbors("north" -> northForest, "east" -> home,   "south" -> southForest, "west" -> northForest)
//         home.setNeighbors(                                                                  "west" -> tangle     )
////    northPole.setNeighbors(                                          "south" -> northForest                       )

  // TODO: place these two items in clearing and southForest, respectively
  
//  clearing.addItems   ("battery" -> new Item("battery", "It's a small battery cell. Looks new.") )
//  southForest.addItems("remote"  -> new Item("remote", "It's the remote control for your TV.\nWhat it was doing in the forest, you have no idea.\nProblem is, there's no battery."))
//  private val keyItems = Array[String]("battery","remote")
  /** The character that the player controls in the game. */

//  /** The number of turns that have passed since the start of the game. */
//  var turnCount = 0
//  /** The maximum number of turns that this adventure game allows before time runs out. */
//  val timeLimit = 40 


  /** Determines if the adventure is complete, that is, if the player has won. */
//  def isComplete = {
//    (this.player.location == this.destination) && this.keyItems.forall( this.player.has( _ ) )
//  }

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */ 
  def isOver = World.player.hasQuit 
//
//  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "geimu-starttooo~"

    
  /** Returns a message that is to be displayed to the player at the end of the game. The message 
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
//    if (this.isComplete) {
//      "Home at last... and phew, just in time! Well done!"
//    } else if (this.turnCount == this.timeLimit) {
//      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
//    } else { // game over due to player quitting
      "Quitter!" 
//    }
  }

  
  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual 
    * report of what happened, or an error message if the command was unknown. In the latter 
    * case, no turns elapse. */
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(World.player)
    if (outcomeReport.isDefined) { 
      World.passTime() 
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }
  
  
}

