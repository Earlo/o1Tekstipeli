package world

import scala.util.Random
import scala.math._

/**
 * @author pollarv1
 */


object RNGesus {
    //TODO make it better 
  var Names = """Aapeli, Elmer, Elmo, Ruut, Lea, Leea, Harri, Aku, August, Aukusti, Hilppa, Titta, Veijo, Veikko, Veli, Nyyrikki, Kari, Karri, Toini, Nuutti, Sakari, Saku, Solja, Ilmari, Ilmo, Anton, Antto, Anttoni, Toni, Laura, Heikki, Henri, Henrik, Henrikki, Aune, Auni, Oona""".replaceAll(" ", "").split(",").toBuffer
  val dice = scala.util.Random
  
  def roll( max:Int = 0, min:Int = 0) = {
    if (max-min > 0){
      min + this.dice.nextInt( max - min )
    }
    else if( min >= max){
      min
    }
    else{
      min + abs(this.dice.nextInt())
    }
  }

  def rollD( max:Int = 1, min:Int = 0) = {
    val mult = max - min
    if (max-min > 0){
      min + this.dice.nextDouble( )*mult
    }
    else if( min >= max){
      min.toDouble
    }
    else{
      min + abs(this.dice.nextInt())*mult
    }
  }
  //remove to make sure no one gets the same name. Poses the problem of having limited amount of NPCs TODO fix
  def baptise( ) = {
   Names.remove(dice.nextInt(Names.length))
  }
  
}