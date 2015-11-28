package o1.adventure

import scala.collection.mutable.Buffer

abstract class Item(val name:String, val description: String, val owner:Option[Character] = None) {

  def uses = List("")
  
  def attack() = {
    0
  }
  
  //override def uses = this.getClass.getInterfaces.toList.map( super[_].uses).sum
 
  override def toString = this.description
}

trait Edible {
  def uses = List("eat")
  
  def eat() ={
    "Nom nom nom"
  }
}

trait Drinkable {
  def uses = List("drink")
  
  def drink() = {
    "Glub glub glub"
  }
}

trait Wearable {
  def uses = List("wear")
 
  def wear() ={
    "oh wow, looks fancy"
  }
}

trait Weapon {
  def uses = List("attack")
 
}

trait Medicine{
  def uses = List("heal")
  
}

class ChocolateHat(name:String, desc:String, owner: Option[Character] = None) extends Item(name, desc, owner) with Edible with Wearable {
  override val uses = super[Edible].uses ++ super[Wearable].uses
  
  //TODO it would be so cool to get this to work. ;:-l
  //override def uses = this.getClass.getInterfaces.toList.map( super[_].uses).sum
}

class Antidote(name:String ="antidote", desc:String ="just use it and win", owner: Option[Character] = None) extends Item(name, desc, owner) with Medicine {
  override val uses = super[Medicine].uses
  
  def heal() = {
    "Feels healthy"
  }
  
  //TODO it would be so cool to get this to work. ;:-l
  //override def uses = this.getClass.getInterfaces.toList.map( super[_].uses).sum
}


class Beer(name: String = "beer", description: String = "It's beer.", owner: Option[Character] = None) extends Item(name, description, owner) with Drinkable {
  
  override val uses = super[Drinkable].uses
  
  override def drink() = {
//    this.owner.get.setPrecision(-1)
//    this.owner.get.setStrength(1)
    "You feel tipsy"
  }
  
}

class Baseballbat(name: String = "baseballbat", description: String = "It's a baseballbat.", owner: Option[Character] = None) extends Item(name, description, owner) with Weapon {
  
  override val uses = super[Weapon].uses
  
  override def attack() = {
    2
  }
  
} 

//testing
//object Main extends App {
//  val c = new ChocolateHat("Choco","a hat")
//
//  println(c.uses  )
//  println(c.eat())
//  println(c.wear())
//
//
//}