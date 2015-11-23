package o1.adventure

import world.RNGesus

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

import world._

object Zombie{
  var num = 0
  def generateRandom( loc: Area) = {
    val newZ = new Zombie( loc, "Z"+this.num.toString, RNGesus.getStats() )
    this.num += 1
    World.NPCs += newZ
    newZ
  }
}


class Zombie (loc: Area, name: String, stats: Map[String, String] = Map[String, String]() ,flags: List[String] = List("NORM")) extends Character(loc, name, stats, flags) {
  val chatNPC = new ChatNPC( this )
  
  def chooseTarget() = {
    this.enemies(0)
  }
  
  def attack() = {
    if ( World.player.location == this.location){ 
       World.combat = Some(new Combat( World.player.Party ++ Buffer[Character](World.player), Buffer[Character](this) ))
    }
  }
  
  override def act() = {
    this.attack()
  }

  override def toString = this.name + " Murr Murr"
 
}