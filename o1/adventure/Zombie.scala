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
    World.Zombies += newZ
    newZ
  }
}

class Zombie (loc: Area, name: String, stats: Map[String, String] = Map[String, String]() ,flags: List[String] = List("NORM")) extends Character(loc, name, stats, flags) {
//class Zombie (val location: Area, val name: String, val stats: Map[String, String] = Map[String, String]() ,val flags: List[String] = List("ZOMB")) {
  //newer going to be used but had do do it anyway  I wish I was writing Python 
  val chatNPC = new ChatNPC( this )
      
 def chooseTarget() = {
    this.enemies(0)
  }
  
  def die() = {
    println("???")
    World.Zombies -= this
    this.location.zombies -= this
    for (e <- this.enemies){
      e.enemies -= this
    }
    //this.enemies = Buffer[Character]()
    var Dead = true
    World.combat.get.actOrder -= this

  }

  override def toString = this.name + " Murr Murr"
 
}