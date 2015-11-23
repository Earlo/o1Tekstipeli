package o1.adventure

import world._
import scala.collection.mutable.Buffer



/**
 * @author pollarv1
 */


object NPC{
  
  def generateRandom( loc: Area) = {
    
    val newGuy = new NPC( loc, RNGesus.baptise() )
    World.NPCs += newGuy
    newGuy
  }
  val relationToString:Map[Int,String] = Map( 0 -> "stranger", 1 -> "aquintance", 2-> "friend" )
  val chatMap:Map[Int,List[List[String]]] = Map(0 -> List( List("greet"), List("introduce", "greet") )  )
  //val chatMap:Map[Int,List[List[String]]] = Map(0 -> List( List("greet"), List("introduce") )  )
  //val chatMap:Map[Int,List[List[String]]] = Map(0 -> List( List("introduce") )  )


}

class NPC(loc: Area, name: String, flags: List[String] = List("NORM")) extends Character( loc, name, flags) {
    
  var HP = 10
  
  def has( name: String ) = {
    this.items.contains( name )
  }
  var relationToPC = 0
  
  def chatOptions() = {
    val baseOptions = NPC.chatMap( this.relationToPC )
    var r = Buffer[String]()
    for (o <- baseOptions){
      if (o.tail.isEmpty || o.tail.forall( this.chat.log.contains(_) ) ){
        r += o.head
      }
    }
    r
  }
  
  val chat = new ChatNPC( this )


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