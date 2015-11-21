package world

import o1.adventure.Player
import scala.collection.mutable.Buffer
import o1.adventure.NPC
import o1.adventure.ui.AdventureGUI

/**
 * @author pollarv1
 *
 * 
 * The world Object for the text adventure
 */
object World {
  
  val time = new Time()
  println (this.time)
  var dTime = Buffer[Int](0,0) //delta time
  var Areas = Buffer[Area]()
  var NPCs = Buffer[NPC]()
  
  
  // TODO maybe something that would make the code more neat when there is a lot of areas
  val Home = new Area("Home", "Your home, condition is absolutely abyssmal, but hey, at least the rent is low", List("HOME","COMP","FOOD"))
  val Outside = new Area("Outside", "You are outside, the sunlight burns your skin", List("OPEN"))
  
  Areas += Home
  Areas += Outside

  
  //TODO maybe get rid of northsoutheastwest type movement, but instead move by destination name, eg. "go home", "go out"
  Home.setNeighbors("north" -> Outside )
  Outside.setNeighbors( "south" -> Home  )
  
//   Check all of the areas and do something if there is something that needs to be done
  for (A <- Areas){
    A.act()
  }
  
  
  val player = new Player(Home)
  
  def startChat( player:Player, other:NPC){
    AdventureGUI.StartChat( other )
  }
  def passTime(){
    
    this.time.increment( m = this.dTime(0), h = this.dTime(1))
    this.dTime = Buffer[Int](0,0)
  }
}



