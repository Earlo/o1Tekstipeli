package o1.adventure


import world.{ World, RNGesus }
import scala.collection.mutable.Buffer


/**
 * @author pollarv1
 */
class ChatPC {
  
  val parent = World.player
  val log = Buffer[String]() // Keeps track of stuff the player has talk about
  
  def greet() = {
    val options = List[String]("Hello.","Hi!")
    "You: " + options( RNGesus.roll( max = options.size ) ) +" \n"
  }
  
  def introduce() = {
    this.log += "intro"
    "You: I am " + "jebu" + "\n"
  }
}

class ChatNPC( val parent:NPC ) {
  val log = Buffer[String]() // keeps track of the things NPC has talked about
  var change = false
  
  def greet() = {
    this.addToLog(  Buffer("greet") )
    this.parent.name + ": Hey:D:D\n"
  }
  
  def introduce() = {
    this.addToLog(  Buffer("intro") )
    this.parent.name + ": My name is " + this.parent.name + "\n"
  }
  
  def addToLog( additions:Buffer[String] ){
    if (!additions.forall( this.log.contains(_) ) ){
      val oldOptions = this.parent.chatOptions()
      this.log ++= additions.filterNot( this.log.contains(_) )
      val newOptions = this.parent.chatOptions()
      if (!(oldOptions == newOptions)){
        this.change = true
      }
    }
  }
  def checkChange() = {
    if (this.change){
      this.change = false
      true
    }
    else{
      this.change
    }
  }
}

