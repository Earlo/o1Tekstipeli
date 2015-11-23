package o1.adventure

import scala.collection.mutable.Buffer

abstract class Item(val name:String, val description: String, val owner:Option[Character] = None) {

  def uses = List("")
  
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
    "Glug glug glug"
  }
}

trait Wearable {
  def uses = List("wear")
 
  def wear() ={
    "oh wow, looks fancy"
  }
}

class ChocolateHat(name:String, desc:String, owner: Option[Character]) extends Item(name, desc, owner) with Edible with Wearable {
  override val uses = super[Edible].uses ++ super[Wearable].uses
  
  //TODO it would be so cool to get this to work. ;:-l
  //override def uses = this.getClass.getInterfaces.toList.map( super[_].uses).sum
}

class Beer(name: String = "Beer", description: String = "It's beer.", owner: Option[Character]) extends Item(name, description, owner) with Drinkable {
  override def uses = {
    this.owner.get.setPrecision(-1)
    this.owner.get.setStrength(1)
    super.uses
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