package o1.adventure

import world.Area


import scala.collection.mutable.Map



/**
 * @author pollarv1
 * 
 * 
 * A base of NPC and Player classes
 */

abstract class Character( var location:Area, val name:String, var flags:List[String] ) {
  
  val items = Map[String, Item]() 
  
}