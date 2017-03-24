import org.train.DataDetails

/**
  * Created by iam on 11/22/16.
  */
class NaiveProcessor(override val positiveFilePathC:String,override val negativeFilePathC:String)
  extends DataDetails(positiveFilePathC,negativeFilePathC){

  private var prob_positive = 0.0
  private var prob_negative = 0.0

  def predict(inputReview:String): Unit ={
      val review_word_list = inputReview.split(" ").toList
      positiveProbability(review_word_list)
  }

  def positiveProbability(words:List[String]){
    var pyForAllX = 1.0
    for(w<-words){
      pyForAllX*=probOfWordGivenPositiveTrue(w)
    }

  }

  def probOfWordGivenPositiveTrue(word:String): Double ={

  }

  def negativeProbability(words:List[String]){

  }


}
