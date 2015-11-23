package o1.adventure


/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect, 
  * parsers for such commands. An action object is immutable after creation.
  *
  * @param input  a textual in-game command such as "go east" or "rest"
  */
class Action(input: String) {
  
  private val unNeededWords = List("TO","WITH","TOWARDS","AGAINST","FOR")
  
  private val commandText = input.trim.toUpperCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val givenModifiers   = commandText.drop(verb.length).trim.split(" ").map(_.replace(" ", "")).filter( !this.unNeededWords.contains(_) )
 

  /** Causes the given player to take the action represented by this object, assuming 
    * that the command was understood. Returns a description of what happened as a result 
    * of the action (such as "You go west."). The description is returned in an `Option` 
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player): Option[String] = {        
    
    val Skey = Player.commands.keys.toList.find( k => k.exists( _ == this.verb) )
    if ( !Skey.isEmpty ){
      val key = Skey.get
      val paramNum = Player.commands(key).last.toInt
      val funk = Player.commands(key).head
      val modifiers = this.givenModifiers.take(paramNum)
      
      if ( modifiers.isEmpty && paramNum == 0 ){
          Some( actor.getClass.getMethod( funk ).invoke( actor ).toString() )      
        }
      else if ( modifiers.length == paramNum ){
          Some( actor.getClass.getMethod(funk, modifiers.map(_.getClass):_* ).invoke( actor, modifiers.map(_.toLowerCase()):_* ).toString() )    
        }
      else{
        None
      }

    }
    else {
      None
      }
  }

  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.givenModifiers + ")"  
  
} 

