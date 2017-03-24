package java

/**
  * Created by iam on 11/20/16.
  */
class Points(val xc:Int,val yc:Int) extends Equal{

  var x:Int = xc
  var y:Int = yc

  def move(dx:Int,dy:Int){
    x = x+dx
    y = y+dy

    println("Points Location X:"+x)
    println("Points Location Y:"+y)

  }

  override def isEqual(obj: Any): Boolean =
    obj.isInstanceOf[Points] && obj.asInstanceOf[Points].x == x

}

trait Equal {

  def isEqual(x:Any):Boolean
  def isNotEqual(x:Any):Boolean= !isEqual(x)

}

class Location(override val xc: Int, override val yc: Int,
               val zc :Int) extends Points(xc, yc){
  var z: Int = zc
  def move(dx: Int, dy: Int, dz: Int) {
    x = x + dx
    y = y + dy
    z = z + dz
    println ("Point x location : " + x)
    println ("Point y location : " + y)
    println ("Point z location : " + z)
  }
}

object Demo{

  implicit class IntTimes(x: Int) {
    def times[A](f: => A): Unit = {
      def loop(current: Int): Unit=

        if(current>0){
          println("Hello")
          loop(current - 1)
        }

      loop(x)

    }
  }
  def main(args: Array[String]) {


    4 times println("hello")

    var a = 0;
    // for loop execution with a range
    for( a <- 1 to 10){
      println( "Value of a: " + a );
    }


    a = 0;
    var numList = List(1, 2, 3, 4, 5, 6);
    // for loop execution with a collection
    for( a <- numList
      if a!=3;if a <5){

      println( "Value of a: " + a );
    }

    numList = List(1,2,3,4,5,6,7,8,9,10);
    // for loop execution with a yield
    val retVal = for{ a <- numList
                      if a != 3; if a < 8
    }yield a
    // Now print returned values using another loop.
    for( a <- retVal){
      println( "Value of a: " + a );
    }

    /*val pt = new Points(10,15)
    pt.move(25,18)

    val loc = new Location(10, 20, 15);
    // Move to a new location
    loc.move(10, 10, 5);*/

    println(multiplier(4))
    strcat("sachin")("aryal")
    var inc = (x:Int) => x+1

    println(apply(layout,10))

    // Two dimensional list
    var dim: List[List[Int]] =
      List(
        List(1, 0, 0),
        List(0, 1, 0)
      )


    for(item<-dim){
        for(i<-item){
          println(i)
        }
    }


    // List of Strings
    val fruit = "apples" :: ("oranges" :: ("pears" :: Nil))
    // List of Integers
    val nums = 1 :: (2 :: (3 :: (4 :: Nil)))


    var concat = fruit:::nums
    concat = List.concat(fruit,nums)

    println(concat)

    // Empty List.
    val empty = Nil
    // Two dimensional list
    dim = (1 :: (0 :: (0 :: Nil))) ::
      (0 :: (1 :: (0 :: Nil))) ::
      (0 :: (0 :: (1 :: Nil))) :: Nil


    println(List.fill(10)("sachin"))

    println(List.tabulate(5)(n=>n*n))

    print(List.tabulate( 5,6 )( _ * _ ))


    var f = Set("apples", "oranges", "pears")
    var nm: Set[Int] = Set()
    println( "Head of fruit : " + f.head )
    println( "Tail of fruit : " + f.tail )
    println( "Check if fruit is empty : " + f.isEmpty )
    println( "Check if nums is empty : " + nm.isEmpty )


    var A:Map[Char,Int] = Map()
    // A map with keys and values.
    val colors = Map("red" -> "#FF0000", "azure" -> "#F0FFFF")

    println(colors.values.toList)
    println(colors.keys)

    colors.keys.foreach{ i =>
      print( "Key = " + i )
      println(" Value = " + colors(i) )}


    val t = (4,3,2,1)  //tuple
    val sum = t._1 + t._2 + t._3 + t._4
    println(sum)

    t.productIterator.foreach{ i =>println("Value = " + i )}

    println(t.toString())

    val t1 = new Tuple2(1,2)
    println(t1.swap)


    val aa:Option[Int] = Some(5)
    val b:Option[Int] = None
    println("a.getOrElse(0): " + aa.getOrElse(0) )
    println("b.getOrElse(10): " + b.getOrElse(10) )

  }
  var multiplier = (i:Int)=>i*5

  def strcat(s1: String)(s2: String) = {
    s1 + s2
  }

  def apply(f: Int => String, v: Int) = f(v)
  def layout[A](x: A) = "[" + x.toString() + "]"


  val fruit: List[String] = List("apples", "oranges", "pears")
  // List of Integers
  val nums: List[Int] = List(1, 2, 3, 4)
  // Empty List.
  val empty: List[Nothing] = List()

  val p1 = new Points(2, 3)
  val p2 = new Points(2, 4)
  val p3 = new Points(3, 3)

  println(p1.isNotEqual(p2))


}

trait Printable extends Any {
  def print(): Unit = println(this)
}
class Wrapper(val underlying: Int) extends AnyVal with Printable
