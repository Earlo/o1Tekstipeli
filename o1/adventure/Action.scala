package o1.adventure


/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect, 
  * parsers for such commands. An action object is immutable after creation.
  *
  * @param input  a textual in-game command such as "go east" or "rest"
  */
class Action(input: String) {
  
  private val unNeededWords = List("TO","WITH","TOWARDS","AGAINST","FOR")
  
  private val commandText = input.trim
  private val verb        = commandText.toUpperCase.takeWhile( _ != ' ' )
  private val givenModifiers   = commandText.toUpperCase.drop(verb.length).trim.split(" ").map(_.replace(" ", "")).filter( !this.unNeededWords.contains(_) )
 

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
    else if( actor.items.keys.toList.contains( givenModifiers(0).toLowerCase() ) ){
      if (actor.items( givenModifiers(0).toLowerCase() ).uses.contains( this.verb.toLowerCase ) ){
         Some( actor.items( givenModifiers(0).toLowerCase ).getClass.getMethod( this.verb.toLowerCase ).invoke(  actor.items( givenModifiers(0).toLowerCase() ) ).toString() )     
      }
      else{
        None
      }
    }
    else {
      None
      }
  }

  
  def BattleExecute(actor: Player): Boolean = { 
    val input = ( commandText.split(" ") ).toSet
    val attacks = actor.battleOption().toSet
    val targets = actor.enemies.map( _.name ).toSet
    if ( input.intersect(attacks).size == 1 && input.intersect(targets).size == 1 ){
    //if ( input.map( _.toLowerCase() ).intersect(attacks).size == 1 && input.intersect(targets).size == 1 ){
      actor.nextAttack = input.intersect(attacks).toList(0)
      actor.nextTarget = input.intersect(targets).toList(0)
      true
    }
//    else if( commandText.capitalize.split(" ").contains("RUN"){
//      
//    }
    
    else{
      false
    }

  }
  
  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.givenModifiers + ")"  
  
} 

