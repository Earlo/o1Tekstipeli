package o1.adventure

import world.Area
import world.RNGesus.rollD

import scala.collection.mutable.Map



/**
 * @author pollarv1
 * 
 * 
 * A base of NPC and Player classes
 */

abstract class Character( var location:Area, val name:String, var stats: Map[String, Int], var flags:List[String] ) {
  
  val items = Map[String, Item]() 
  
  def setHitPoints(amount: Int) = this.stats("hitpoints") += amount
  
  def setPrecision(amount: Int) = this.stats("precision") += amount
  
  def setStrength(amount: Int) = this.stats("strength") += amount
  
  def hitPoints = this.stats("hitpoints")
  
  def precision = this.stats("precision")
  
  def strength = this.stats("strength")
  
  def attack(target: Character): Boolean = {
    if (rollD() < this.precision) {
      target.setHitPoints(-this.strength) 
      true
    } else false
  }
  
  
  
}