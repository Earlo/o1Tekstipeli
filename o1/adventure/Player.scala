package o1.adventure

import world._

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer


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
                                                      List("REST") -> List("rest","0"),
                                                      List("WAIT") -> List("wait","1"),
                                                      List("EXAMINE") -> List("examine", "1"),
                                                      List("LOOK") -> List("look","0"),
                                                      List("DROP") -> List("drop", "1"),
                                                      List("GET") -> List("get", "1"),
                                                      List("HAS") -> List("has", "1"),
                                                      List("TALK","CHAT") -> List("talk", "1"),
                                                      List("INVENTORY","ITEMS") -> List("inventory","0"),
                                                      List("HELP","HALPPLOX") -> List("help","0")
                                                     )                                                   
}

class Player(loc: Area, name:String = "The PlayerCharachter", stats: Map[String, String], flags:List[String] = List("PRTG") ) extends Character( loc, name, stats, flags) {
  //newer going to be used but had do do it anyway  
  val chatNPC = new ChatNPC( this )
    
  private var quitCommandGiven = false              // one-way flag   
  private var isDead = false
  
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven
  
  val chat = new ChatPC() 
  var Party: Buffer[Character] = Buffer()
  
  var nextAttack = ""
  var nextTarget = ""

  // something better here
  def chooseTarget() = {
    this.enemies(0)
  }

  
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

  def itemUses() = {
    this.items.values.toList.flatMap( _.uses )
  }
  
  def has( name: String ) = {
    this.items.contains( name )
  }
    
  def talk( name: String) = {
    var result = name.capitalize + " is not here."
    val target = this.location.isHere( name )
    if ( !target.isEmpty ){
      if (!target.get.flags.contains("ZOMB") ){
        World.startChat( this, target.get )
        result = "you had a chat with  " + target.get.toString() +"."
      }
      else{
        "your mum told you not to talkk with the undead"
      }
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
      "You look closely at the "+ name +".\n" + this.items(name).description + " It can be used for with following commands " + this.items(name).uses.mkString(", ")
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
    if (!destination.getOrElse(this.location).flags.contains("CLSD")){
      this.location = destination.getOrElse(this.location) 
      for (p <- this.Party){
        p.location.inhabitants -= p
        p.location = this.location
        p.location.inhabitants += p
      }
      if (destination.isDefined){ 
        World.dTime(0) += 5
        "You go " + direction + "."
      } 
      else{
        "You can't go " + direction + "."
      }
    }
    else{
     "The ChemistryBuilding is closed, you can't get in"
    }
  }
  
  
  def wait( t:String ) = {
    val time = t.replace("m","")
    try{
      World.dTime(0) += time.toInt
      "You wait for a while. Better get a move on, though."
    }
    catch {
      case e:NumberFormatException => "Please specify the time you want to wait in minutes"
    }

  }
  
  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() = {
    if ( this.location.flags.contains("HOME") ){
          World.dTime(1) += 8
          "You sleep for 8 hours." 
    }
    else{
      "This is hardly a suitable place for resting"
    } 
  }
  
  
  /** Signals that the player wants to quit the game. Returns a description of what happened within 
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }
  
  def help() = {
    val genericstr = "Use command(s): "
    "This is a game where you were supposed to be able to do awfully a lot of things, but I got sick during the developement and my project partner decided that He doesn't want to do this. Here are the commands availeable to you" +{
     
      var commands = ""
      for (k <- Player.commands.keys){
        commands += genericstr + k.mkString(", ") + " to " + Player.commands(k)(0) + "\n"
      }
      commands
    } + "You can only use command 'REST' in your home, there was supposed to be more commands like that but there 'aint any. Also, examine iteams to get to know their keywords"
  }
  
  def battleOption() = {
   val additionalOptions =  this.items.values.toBuffer.filter( _.uses.contains("attack") ).map(_.name)
   println (additionalOptions)
   Buffer[String]("punch") ++ additionalOptions
  }
  
  def bOptionMap() = {
    val options = this.battleOption
    (options.indices zip options).toMap
  }
  
  def attack() = {
      val attack = 1
      if (!(this.nextAttack == "punch")){
        val attack = this.items(this.nextAttack).attack()
      }
      if (!this.enemies.filter( _.name == this.nextTarget ).isEmpty){
        val target = this.enemies.filter( _.name == this.nextTarget ).head
      if (RNGesus.rollD() < this.precision.toDouble) {
        target.setHitPoints(-this.strength.toInt)
        if ( target.hitPoints.toInt <= 0 ){
          target.die()
          "\n" + "You attaked" + target.name + " for " + this.strength + " damage, this killed " + target.name
        }
        else "\n" + "You attaked" + target.name + " for " + this.strength + " damage"
      } else "\n" + "You missed " + target.name
    }
      else{
        println("!!!!",this.nextTarget,this.enemies.map(_.name),"This should never happen")
        ""
      }
  }
  def addToParty( char:Character ){
    if (!this.Party.contains(char)){
      this.Party += char
      char.location = this.location
    }
  }
  
  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name   

  def die() = {
    println("kuolitSaatana")
    for (e <- this.enemies){
      e.enemies -= this
    }
  }
}


