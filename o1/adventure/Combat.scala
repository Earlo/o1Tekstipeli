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
    if (!actor.enemies.isEmpty){
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
    else{
     ""
      //this.end() 
    } 
  }

  def checkIfOver() = {
    Z.size == 0 || H.size == 0
  }
  def end() = {
    if (this.checkIfOver()){
      World.combat = None
      "\nThe battle ended in the victory of " + this.getNext().name
    }
    else ""
  }
  
  def info() = {
    "You are in battle against " + World.player.enemies.map( _.name ).mkString(", ") + (if (!(World.player.Party.size == 0)) " With your team of " + World.player.Party.mkString(", ")  )+". Commands availeable to you are: " + World.player.battleOption().mkString(", ")
  }
//  
//  def runCombat() = {
//    while ( this.actOrder(0).enemies.size > 0 ){
//      this.playTrun()
//      }
//    }
    
  override def toString() = {
    "You are figthing against " + this.Z.size.toString + " Zombies" + " \n" + " Z " * this.Z.size + "\n\n" + " H " * this.H.size
    }
  
}