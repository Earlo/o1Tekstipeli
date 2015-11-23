package o1.adventure

import world._

import scala.collection.mutable.Map

import scala.collection.mutable.Buffer

/**
 * @author pollarv1
 */
class Combat(var H:Buffer[Character],var Z:Buffer[Character] ) {
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
    val list = dice.shuffle((H++Z))
    list -= World.player
    (list += World.player).reverse
  }
  
  def getNext() = {
    this.actOrder.head
  }
  
  def playTrun() = {
    
    val actor = this.actOrder.head
    var report = "Attack didn't happen"
    
    if (actor == World.player){
      report = World.player.attack()
    }
    else{
       var target = actor.chooseTarget() 
       report = actor.attack( target )
    }
    
    this.actOrder.remove(0)
    this.actOrder += actor
    report
    }
  
  
  def info() = {
    "You are in battle against " + World.player.enemies.map( _.name ).mkString(", ") + ". Commands availeable to you are " + World.player.battleOption().toString()
  }
//  
//  def runCombat() = {
//    while ( this.actOrder(0).enemies.size > 0 ){
//      this.playTrun()
//      }
//    }
    
  override def toString() = {
    "you are figthing against " + this.Z.size.toString + " Zombies" + " \n" + " Z " * this.Z.size + "\n\n" + " H " * this.H.size
    }
  
}