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
  var extraTime = Buffer[Int](0,0)
  var Areas = Buffer[Area]()
  
  
  // TODO maybe something that would make the code more neat when there is a lot of areas
  val Home = new Area("Home", "Your home, condition is absolutely abyssmal, but hey, at least the rent is low", List("HOME","COMP","FOOD"))
  val Outside = new Area("Outside", "You are outside, the sunlight burns your skin", List("OPEN"))
  
  Areas += Home
  Areas += Outside

  
  //TODO maybe get rid of northsoutheastwest type movement, but instead move by destination name, eg. "go home", "go out"
  Home.setNeighbors("north" -> Outside )
  Outside.setNeighbors( "south" -> Home  )
  
//   Check all of the areas and do something if there is something that needst to be done
  for (A <- Areas){
    A.act()
  }
  
  
  val player = new Player(Home)
  
  def startChat( player:Player, other:NPC){
    println("TESTING")
    AdventureGUI.StartChat()
  }
  def passTime(){
    //something smart will happen here later :D
    
    this.time.increment( m = 5 + this.extraTime(0), h = this.extraTime(1))
    extraTime = Buffer[Int](0,0)
  }
}



