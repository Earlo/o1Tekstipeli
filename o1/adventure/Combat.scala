package o1.adventure

import world.RNGesus

import scala.collection.mutable.Map
/**
 * @author pollarv1
 */
class Combat( H:List[Character], Z:List[Character] ) {
  
  for (h <- H){
    h.enemies = Z
  }
  
  for (z <- Z){
    z.enemies = H
  }
  
  val battleLog = List[String]()
  
  val actOrder = rollInitative()
  
  def rollInitative() = {
//    var rolls = Map[Int, Character]()
//    for (char <- H){
//      var r = RNGesus.roll(20)
//      if ( !rolls.keys.toList.contains(r) ){
//          rolls(r) = char
//      }
//    }
//    for (z <- Z){
//      rolls(z) = RNGesus.roll(20) 
//    }
    val dice = scala.util.Random
    dice.shuffle((H++Z))
  }
  
  def playTrun() = {
    
    val actor = this.actOrder(0)
    val target = actor.chooseTarget()
    actor.attack(target)
    
  }
  
  
}