package world

/**
 * @author pollarv1
 */
class Time(var d:Int = 1, var h:Int = 0, var m:Int = 0){
  
  val Suffix:Map[Int,String] = Map( 1 -> "st", 2 -> "nd", 3->"rd")
  
  def increment( m:Int =  0, h:Int = 0) = {
    this.m += m
    while (this.m >= 60){
      this.m -= 60
      this.h += 1
    }
    this.h += h
    while (this.h >= 24){
      this.h -= 24
      this.d += 1
    }
  }
  
  def numSuffix(num:Int) = {
    val n = num.toString()
    if ( (n.size >= 2 && n(n.size - 2) == '1') || !this.Suffix.keys.toList.contains(n(0).asDigit)){
      "th"
    }
    else{
      this.Suffix(n(0).asDigit)
    }
  }
  
  def withTwoDigits( num:Int ) = {
    num.toString.reverse.padTo(2, "0").reverse.mkString("")
  }
  
  def asMilitaryTime() = {
    (this.d.toString+this.withTwoDigits(this.h)+this.withTwoDigits(this.m)).toInt
    
  }
  override def toString() = {
    "It's "+this.withTwoDigits(this.h)+":"+this.withTwoDigits(this.m)+" on the " + this.d.toString + this.numSuffix(this.d) + " day."
  }
}