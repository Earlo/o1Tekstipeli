package world

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer
import o1.adventure.Item
import o1.adventure.NPC


// Contains the stuff common to all areas
object Area {
  
  val events:Map[String, List[String] ] = Map( "OPEN" -> List("populate") )
  
}

/** The class `Area` represents locations in a text adventure game world. A game world 
  * consists of areas. In general, an "area" can be pretty much anything: a room, a building, 
  * an acre of forest, or something completely different. What different areas have in 
  * common is that players can be located in them and that they can have exits leading to 
  * other, neighboring areas. An area also has a name and a description. 
  *
  * @param name         the name of the area 
  * @param description  a basic description of the area (typically not including information about items)
  */
class Area(var name: String, var description: String, var flags: List[String] = List() ) {

  
  private val neighbors = Map[String, Area]()
  private val items     = Map[String, Item]()
  private var inhabitants = Buffer[NPC]()
  
  val eventSchedule:Map[String, String] = Map( "11200:11300" -> "funktionNimi")
  
  /** Returns the area that can be reached from this area by moving in the given direction. The result 
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = this.neighbors.get(direction)

  // Area.act makes events happen inside Areas
  def act(){
    for (f <- this.flags){
      if ( Area.events.keys.toList.contains(f) ){
         for (e <- Area.events(f) ){
           this.getClass.getMethod( e ).invoke( this )
         }
       }
    }
    for (t <- this.eventSchedule.keys){
      val interval = t.split(":")
      if (World.time.asMilitaryTime() > interval(0).toInt && World.time.asMilitaryTime() < interval(1).toInt){
          this.getClass.getMethod( this.eventSchedule(t) ).invoke( this )
       }
    }
  }
  
  def populate(){
    val pop = RNGesus.roll( 4, 1)
    for (p <- Range(0,pop)){
      this.inhabitants += NPC.generateRandom( this )
    }
  }

  

  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling 
    * the `setNeighbor` method on each of the given direction--area pairs.
    *
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]]
    */
  def setNeighbors(exits: (String, Area)*) = {
    this.neighbors ++= exits
  }
  
  def addItems( item: (String, Item)* ) = {
    this.items ++= item
  }
  
  def removeItem( name: String) = {
    this.items.remove( name )
  }  
  
  def isHere ( name:String ) = {
    this.inhabitants.find( _.toString.toUpperCase() == name.toUpperCase() )
  }
  
  def contains( name: String ) = {
    this.items.contains( name )
  }
  
  /** Returns a multi-line description of the area as a player sees it. This includes a basic 
    * description of the area as well as information about exits and items. */
  def fullDescription = {
   val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")
   var itemList = this.getItemString()
   var charList = this.getCharString()
   this.description + itemList + charList + exitList 
  }
  
  
  def getItemString() = {
   var itemList = ""
   if (this.items.size > 1){
     itemList = "\nThere are following items in here: " +  this.items.keys.tail.mkString(", ") + " and " + this.items.keys.head.toString   
   }
   else if(this.items.size == 1){
     itemList = "\nThere is "  + this.items.keys.mkString(" ") + " in here."
   }
   itemList
  }
 
  def getCharString() = {
   var namelist = ""
   if (this.inhabitants.size > 1){
     namelist = "\nThere are following people in here: " +  this.inhabitants.tail.mkString(", ") + " and " + this.inhabitants.head.toString   
   }
   else if(this.inhabitants.size == 1){
     namelist = "\nThere is "  + this.inhabitants.mkString(" ") + " in here."
   }
   namelist
  }
  
  
  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)
}
