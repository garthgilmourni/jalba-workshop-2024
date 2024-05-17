package poker.hands.unit

class StraightFlushSpec extends PokerSpec {
  "A straight flush " should "be recognised" in {
    handWorks("8S 7S 6S 5S 4S", _.isStraightFlush)
  }
  it should "be recognised regardless of order" in {
    val cards = List("8S", "7S", "6S", "5S", "4S")
    for(cards <- cards.permutations.map(_.mkString(" "))) {
      handWorks(cards, _.isStraightFlush)
    }
  }
  it should "be recognised regardless of suit" in {
    val suits = List("H","S","C","D")
    val ranks = List("8", "7", "6", "5", "4")
    
    for(suit <- suits) {
      val cards = ranks.map(_ + suit).mkString(" ")
      handWorks(cards, _.isStraightFlush)
    }
  }
}
