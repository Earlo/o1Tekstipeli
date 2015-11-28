package o1.adventure


import world.{ World, RNGesus }
import scala.collection.mutable.Buffer


/**
 * @author pollarv1
 */
class ChatPC {
  
  val log = Buffer[String]() // Keeps track of stuff the player has talk about
  
  def greet() = {
    val options = List[String]("Hello.","Hi!")
    "You: " + options( RNGesus.roll( max = options.size ) ) +" \n"
  }
  
  def introduce() = {
    this.log += "introduce"
    "You: I am " + World.player.name + "\n"
  }
  // no time for flavour texts :-l
  def chitchat() = {
    this.log += "chitchat"
    "You: did yo hear about *the interesting thing* or *something* *something* \n"
  }
  
  def join() = {
  this.log += "join"
  "You: We are pals right? come hang out with me \n"
  }

}

class ChatNPC( val parent:Character ) {
  val log = Buffer[String]() // keeps track of the things NPC has talked about
  var change = false
  var ChatCount = 0 
  
  def greet() = {
    val options = Map(-5 -> "...", 0-> "hi?", 5 -> "hi", 10 -> "Hello!")
    this.parent.socRoll()

    this.addToLog(  Buffer("greet") ) 
    
    this.parent.name + ": " +options( this.chooseKey(options) )
  }
  
  def introduce() = {
    val options = Map(-10 -> "Please leave", 0-> ("uh, I'm " + this.parent.name), 10 -> ("My name is " + this.parent.name), 20 -> ("Nice to meet you, I'm " + this.parent.name) )
    this.parent.socRoll()

    this.addToLog(  Buffer("introduce") )
    if (!this.log.contains("introduce")){
      this.parent.name + ": " +options( this.chooseKey(options) )
      }
    else{
      this.parent.name + ": I already know that."
    }
  }
  
  def chitchat() = {
    this.addToLog(  Buffer("intro") )
    if ( this.ChatCount < 3 ) {
      this.parent.socRoll()
      this.ChatCount += 1
    }
    this.parent.name + ": Oh wow so interesting \n"   
  }
  
  def join() = {
    if (World.player.Party.contains(this.parent)){
      this.parent.name + ": I already have \n"
    }
    else{
      this.log += "join"
      World.player.addToParty(this.parent)
      this.parent.name + ": Sure"
      }
    }

  
  def chooseKey( o:Map[Int,String] ) = {
    o.keys.toList.minBy(v => math.abs(v - this.parent.relationToPC ))
    }
  
  def addToLog( additions:Buffer[String] ) = {
    if (!additions.forall( this.log.contains(_) ) ){
      val oldOptions = this.parent.chatOptions()
      this.log ++= additions.filterNot( this.log.contains(_) )
      val newOptions = this.parent.chatOptions()
      if (!(oldOptions == newOptions)){
        this.change = true
      true
      }
    }
    false
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

