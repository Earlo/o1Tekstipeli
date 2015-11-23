package o1.adventure

import world.RNGesus

import scala.collection.mutable.Map
/**
 * @author pollarv1
 */
class Combat(H:List[Character],Z:List[Character]) {
  
  val battleLog = List[String]()
  
  rollInitative()
  
  def rollInitative() = {
    var rolls = Map[Character, Int]()
    for (char <- H){
      rolls(char) = RNGesus.roll(20) 
    }
    for (char <- Z){
      rolls(char) = RNGesus.roll(20) 
    }
    val actOrder = List[Character]()
    //rolls.foreach( println(_) )
  }
  
}