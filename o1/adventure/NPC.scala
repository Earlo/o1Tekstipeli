package o1.adventure

import world.{Area, RNGesus}



/**
 * @author pollarv1
 */


object NPC{
  
  def generateRandom( loc: Area) = {
    
    new NPC( loc, RNGesus.baptise() )
    
  }
}
class NPC(loc: Area, name: String, flags: List[String] = List("NORM")) extends Character( loc, name, flags) {
  
  def has( name: String ) = {
    this.items.contains( name )
  }

  /** Attempts to move the player in the given direction. This is successful if there 
    * is an exit from the player's current location towards the direction name. 
    * Returns a description of the results of the attempt. */
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.location = destination.getOrElse(this.location) 
    if (destination.isDefined) this.name+" goes to " + direction + "." else ""
  }  
  
  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = this.name   


}