package world

import o1.adventure.Player
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import o1.adventure._

import o1.adventure.ui.AdventureGUI
import scala.collection.mutable.Map
/**
 * @author pollarv1
 *
 * 
 * The world Object for the text adventure
 */
object World {
  var globalFlags = Buffer[String]()
  val time = new Time()
  var dTime = Buffer[Int](0,0) //delta time
  var Areas = Buffer[Area]()
  var NPCs = Buffer[NPC]()
  var Zombies = Buffer[Zombie]()

  var combat:Option[Combat] = None

  

    // just here for testing purposes
//    val Home = new Area("Home", "Your home, condition is absolutely abyssmal, but hey, at least the rent is low", List("HOME","COMP","FOOD"))
//    val Outside = new Area("Outside", "You are outside, the sunlight burns your skin", List("POPL"))
//    val FarOutside = new Area("FarOutside", "We aren't in kansas anymore Toto", List("ZOMB"))
//    
//    World.Areas += Home
//    World.Areas += Outside
//    World.Areas += FarOutside
//   

//    
//    //TODO maybe get rid of northsoutheastwest type movement, but instead move by destination name, eg. "go home", "go out"
//    Home.setNeighbors("north" -> Outside )
//    Outside.setNeighbors( "south" -> Home  )  
//    Outside.setNeighbors( "north" -> FarOutside  )  
//    FarOutside.setNeighbors( "south" -> Outside  )  


  
  // TODO maybe something that would make the code more neat when there is a lot of areas
  val Void = new Area("","I really hope you don't see this in game")
  
  val Home = new Area("Home", "Your home, condition is absolutely abyssmal, but hey, at least the rent is low", List("HOME"))
  val Outside = new Area("Outside", "You are outside, the sunlight burns your skin", List("OPEN","POPL"))
  val TF = new Area("TF", "There's food and stuff", List("OPEN", "POPL"))
  val Rantsu = new Area("Rantsu", "Is it really time for this right now?", List())
  val Alvari = new Area("Alvari", "Not much to see here.", List("OPEN","POPL"))
  val Alepa = new Area("Alepa", "Hope there's beer left...", List("OPEN", "POPL"))
  val MainBuilding = new Area("Main building", "I hate this place", List("OPEN","POPL"))
  val ChemistryBuilding = new Area("Chemistry building", "What could this place be?", List("CLSD"))
  //val ChemistryBuilding = new Area("Chemistry building", "What could this place be?", List(""))
  
  val CSBuilding = new Area("CS building", "You feel at home.", List("OPEN","POPL"))
  
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
  TF.setNeighbors("west" -> Outside)
  Rantsu.setNeighbors("east" -> Outside)
  Alepa.setNeighbors("east" -> Alvari)
  ChemistryBuilding.setNeighbors("west" -> MainBuilding)
  CSBuilding.setNeighbors("north" -> MainBuilding) 
  
    Home.addItems( "chocohat" -> new ChocolateHat("chocho", "a hat made of chocolate.") )
    Home.addItems( "beer" -> new Beer() )
    Home.addItems( "baseballbat" -> new Baseballbat() )
    ChemistryBuilding.addItems( "antidote" -> new Antidote() )
 
  
//   Check all of the areas and do something if there is something that needs to be done

  
  val playerStats = Map("hitpoints" -> "100", "strength" -> "100", "precision" -> "1.5")
  val player = new Player(Home, "The Main Dude", playerStats)

  for (A <- Areas){
    A.act()
  }
  
  def startChat( player:Player, other:Character){
    AdventureGUI.StartChat( other )
  }
  
  def getText() = {
    if ( this.combat.isEmpty ){
      player.location.name + "\n" + player.location.fullDescription
    }
    else{
      this.combat.get.toString()
    }
  }
  
  def passTime(){
    if (this.combat.isEmpty){
      for (A <- Areas){
        A.act()
      }
      for (C <- World.NPCs){
        C.act()
      }
  //    for (Z <- World.Zombies){
  //      Z.act()
  //    }
      this.time.increment( m = this.dTime(0) + 30 , h = this.dTime(1))
      this.dTime = Buffer[Int](0,0)
      if (this.time.d == 2 && ChemistryBuilding.flags.contains("CLSD") ){
        ChemistryBuilding.flags = ChemistryBuilding.flags.filterNot { _ == "CLSD" }
        for (A <- Areas){
          if( A.flags.contains("OPEN") ) {
              A.flags = A.flags ++ List[String]("ZOMB")
           
            }
        }
      }
    }

  }
}



