import scala.io.Source

/**
  * Created by iam on 11/22/16.
  */
class DataDetails (val positiveFilePathC:String,val negativeFilePathC:String){

  var positiveFilePath = positiveFilePathC
  val negativeFilePath = negativeFilePathC
  var probability_positive = 0.0

  def fillDataDetails(): Unit ={

    def lines = Source.fromFile("resource/positive_review.txt").getLines()

    for(i<-lines){
      println(i)
    }

  }

}
