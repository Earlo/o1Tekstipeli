package o1.adventure

import world.Area
import world.RNGesus._

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
  var Dead = false
  var enemies = Buffer[Character]()
  
  var relationToPC = 0
  
  def act() = {
    
  }
  
  def socRoll() = {
    this.relationToPC += roll(15, -5)
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
    if (rollD() < this.precision.toDouble && !this.Dead) {
      target.setHitPoints(-this.strength.toInt)
      println(target.hitPoints.toInt,"hp on " + target.name)
      if ( target.hitPoints.toInt <= 0 ){
        target.die()
        "\n" + this.name + " attaked " + target.name + " for " + this.strength + " damage, this killed " + target.name
      }
      else "\n" + this.name + " attaked " + target.name + " for " + this.strength + " damage"
    } else "\n" + this.name + " missed " + target.name
  }
  def die():Unit
}