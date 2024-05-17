package poker.hands.unit

class RoyalFlushSpec extends PokerSpec {
  "A royal flush " should "be recognised" in {
    handWorks("AS KS QS JS 10S", _.isRoyalFlush)
  }
  it should "be recognised regardless of order" in {
    val cards = List("AS", "KS", "QS", "JS", "10S")
    worksInAnyOrder(cards, _.isRoyalFlush)
  }
  it should "be recognised regardless of suit" in {
    val suits = List("H","S","C","D")
    val ranks = List("A", "K", "Q", "J", "10")
    
    for(suit <- suits) {
      val cards = ranks.map(_ + suit).mkString(" ")
      handWorks(cards, _.isRoyalFlush)
    }
  }
}