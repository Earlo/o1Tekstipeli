package o1.adventure

import world._

import scala.collection.mutable.Map

/** A `Player` object represents a player character controlled by the real-life user of the program. 
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player
  */
object Player{
  // keyword that trigger the method -> method name, number of arguments
  val commands:Map[List[String], List[String]] = Map( List("GO","HEAD") -> List("go","1"),
                                                      List("QUIT","EXIT") -> List("quit","0"),
                                                      List("REST","WAIT") -> List("rest","0"),
                                                      List("EXAMINE") -> List("examine", "1"),
                                                      List("LOOK") -> List("look","0"),
                                                      List("DROP") -> List("drop", "1"),
                                                      List("GET") -> List("get", "1"),
                                                      List("HAS") -> List("has", "1"),
                                                      List("TALK","CHAT") -> List("talk", "1"),
                                                      List("INVENTORY","ITEMS") -> List("inventory","0")
                                                     )
}

class Player(loc: Area, name:String = "MainDude", flags:List[String] = List("PRTG")) extends Character( loc, name, flags) {

  private var quitCommandGiven = false              // one-way flag   
  
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven
  
  def inventory ={
   var itemList = "You are empty-handed."     
   if (this.items.size >= 1){
     itemList = "There are following items in your bag: " +  this.items.keys.tail.mkString(", ") + " and " + this.items.keys.head.toString   
   }
   else if(this.items.size == 1){
     itemList = "There is"  + this.items.keys.mkString("") + "in your bag."
   }
   itemList
  }
  
  def has( name: String ) = {
    this.items.contains( name )
  }
    
  def talk( name: String) = {
    var result = name.capitalize + " is not here."
    val target = this.location.isHere( name )
    if ( !target.isEmpty ){
      World.startChat( this, target.get )
      result = "you start to chat with  " + target.get.toString() +"."
    }
    result
  }
   
  def get( name: String) = {
    var result = "There is no " + name + " here to pick up."
    if (this.location.contains( name )){
      this.items(name) = this.location.removeItem( name ).get
      result = "You pick up the  " + name +"."
    }
    result
  }
  
  def drop( name: String) = {
    var result = "You don't have that!"
    if (this.has( name )){
      this.location.addItems( (name, this.items.remove( name ).get ) )
      result = "You drop the " + name +"."
    }
    result
  }
  
  def examine ( name: String) = {
    if (this.has(name)){
      "You look closely at the "+ name +".\n" + this.items(name).description
    }
    else{
      "If you want to examine something, you need to pick it up first."
    }    
  }
  
  def look ( ) = {
    this.location.fullDescription  
  }
  
  /** Attempts to move the player in the given direction. This is successful if there 
    * is an exit from the player's current location towards the direction name. 
    * Returns a description of the results of the attempt. */
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.location = destination.getOrElse(this.location) 
    if (destination.isDefined) "You go " + direction + "." else "You can't go " + direction + "."
  }

  
  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() = {
    World.extraTime(0) += 50
    "You rest for a while. Better get a move on, though." 
  }
  
  
  /** Signals that the player wants to quit the game. Returns a description of what happened within 
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }

  
  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name   


}

