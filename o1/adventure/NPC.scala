package o1.adventure

import world._
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map


/**
 * @author pollarv1
 */


object NPC{
  def generateRandom( loc: Area) = {
    
    val newGuy = new NPC( loc, RNGesus.baptise(),  RNGesus.getStats() )
    World.NPCs += newGuy
    newGuy
  }
  val relationToString:Map[Int,String] = Map( 0 -> "stranger", 1 -> "aquintance", 2-> "friend" )
  val chatMap:Map[Int,List[List[String]]] = Map(0 -> List( List("greet"), List("introduce", "greet"), List("chitchat", "greet", "introduce") )  )
  //val chatMap:Map[Int,List[List[String]]] = Map(0 -> List( List("greet"), List("introduce") )  )
  //val chatMap:Map[Int,List[List[String]]] = Map(0 -> List( List("introduce") )  )
}

class NPC(loc: Area, name: String, stats: Map[String, String] = Map[String, String]() ,flags: List[String] = List("NORM")) extends Character( loc, name, stats, flags) {

  
  def has( name: String ) = {
    this.items.contains( name )
  }
  
  override def chatOptions() = {// dunno what I was thinking
    val baseOptions = NPC.chatMap( 0 )
    var r = Buffer[String]()
    for (o <- baseOptions){
      if (o.tail.isEmpty || o.tail.forall( this.chatNPC.log.contains(_) ) ){
        r += o.head
      }
    }
    //if (this.relationToPC > 50){
    if (this.relationToPC > 0){
      r += "join"
    }
    r
  }
  
  val chatNPC = new ChatNPC( this )

  def chooseTarget() = {
    this.enemies(0)
  }

  /** Attempts to move the player in the given direction. This is successful if there 
    * is an exit from the player's current location towards the direction name. 
    * Returns a description of the results of the attempt. */
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.location = destination.getOrElse(this.location) 
    if (destination.isDefined) this.name+" goes to " + direction + "." else ""
  }
  

  override def act() = {
    //TODO
  }
  
  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = this.name   

  
  def die() = {
    World.NPCs -= this
    this.location.inhabitants -= this
    for (e <- this.enemies){
      e.enemies -= this
    }
    //this.enemies = Buffer[Character]()
    var Dead = true
    World.player.Party -= this
    World.combat.get.actOrder -= this

  }
  
  def toZombie() = {
    new Zombie( this.loc, this.name, this.stats, this.flags )
    
  }
}