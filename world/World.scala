package world

import o1.adventure.Player
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import o1.adventure.NPC
import o1.adventure.ChocolateHat
import o1.adventure.ui.AdventureGUI
import scala.collection.mutable.Map
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
  

//    // TODO maybe something that would make the code more neat when there is a lot of areas
//    val Home = new Area("Home", "Your home, condition is absolutely abyssmal, but hey, at least the rent is low", List("HOME","COMP","FOOD"))
//    val Outside = new Area("Outside", "You are outside, the sunlight burns your skin", List("OPEN"))
//    
//    World.Areas += Home
//    World.Areas += Outside
//    
//    Home.addItems( "chocohat" -> new ChocolateHat("chocho", "a hat.") )
//    
//    //TODO maybe get rid of northsoutheastwest type movement, but instead move by destination name, eg. "go home", "go out"
//    Home.setNeighbors("north" -> Outside )
//    Outside.setNeighbors( "south" -> Home  )  

  
  // TODO maybe something that would make the code more neat when there is a lot of areas
  val Home = new Area("Home", "Your home, condition is absolutely abyssmal, but hey, at least the rent is low", List("HOME","COMP","FOOD"))
  val Outside = new Area("Outside", "You are outside, the sunlight burns your skin", List("OPEN"))
  val TF = new Area("TF", "There's food and stuff", List("OPEN", "FOOD"))
  val Rantsu = new Area("Rantsu", "Is it really time for this right now?", List())
  val Alvari = new Area("Alvari", "Not much to see here.", List("OPEN"))
  val Alepa = new Area("Alepa", "Hope there's beer left...", List("OPEN", "FOOD", "BEER"))
  val MainBuilding = new Area("Main building", "I hate this place", List("OPEN"))
  val ChemistryBuilding = new Area("Chemistry building", "What could this place be?", List("OPEN"))
  val CSBuilding = new Area("CS building", "You feel at home.", List("OPEN"))
  
  Areas += Home
  Areas += Outside
  Areas += TF
  Areas += Alvari
  Areas += Alepa
  Areas += MainBuilding
  Areas += ChemistryBuilding
  Areas += CSBuilding
  
  //TODO maybe get rid of northsoutheastwest type movement, but instead move by destination name, eg. "go home", "go out"
  Home.setNeighbors("south" -> Outside )
  Outside.setNeighbors( "north" -> Home, "west" -> Rantsu, "east" -> TF, "south" -> Alvari)
  Alvari.setNeighbors("north" -> Outside, "west" -> Alepa, "south" -> MainBuilding)
  MainBuilding.setNeighbors("north" -> Alvari, "east" -> ChemistryBuilding, "south" -> CSBuilding)
  
    
//   Check all of the areas and do something if there is something that needs to be done
  for (A <- Areas){
    A.act()
  }
  
  val playerStats = Map("hitpoints" -> 10, "strength" -> 10, "precision" -> 10)
  val player = new Player(Home, "The Main Dude", playerStats)

  
  def startChat( player:Player, other:NPC){
    AdventureGUI.StartChat( other )
  }
  def passTime(){
    println(this.player.itemUses())
    this.time.increment( m = this.dTime(0), h = this.dTime(1))
    this.dTime = Buffer[Int](0,0)
    for (A <- Areas){
      A.act()
     }
  }
}



