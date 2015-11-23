package o1.adventure

import world.Area
import world.RNGesus.rollD

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer




/**
 * @author pollarv1
 * 
 * 
 * A base of NPC and Player classes
 */


abstract class Character( var location:Area, val name:String, var stats: Map[String, String], var flags:List[String] ) {
  
  def chatOptions():Buffer[String] = Buffer[String]()
  
  val chatNPC:ChatNPC
  
  var enemies = Buffer[Character]()
  
  def act() = {
    
  }
  
  
  val items = Map[String, Item]() 
  
  def chooseTarget():Character
  
  def setHitPoints(amount: Int) = this.stats("hitpoints") = (this.stats("hitpoints").toInt + amount).toString()
  
  def setPrecision(amount: Int) = this.stats("precision") = (this.stats("precision").toDouble + amount).toString()
  
  def setStrength(amount: Int) = this.stats("strength") = (this.stats("strength").toInt + amount).toString()
  
  def hitPoints = this.stats("hitpoints")
  
  def precision = this.stats("precision")
  
  def strength = this.stats("strength")
  
  def attack(target: Character): String = {
    //println( target.stats, this.stats )
    if (rollD() < this.precision.toDouble) {
      target.setHitPoints(-this.strength.toInt) 
      this.name + "attaked" + target.name + " for " + this.strength + " damage"
    } else this.name + " missed " + target.name
  }
}