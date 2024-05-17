package poker.hands.unit

class FourOfAKindSpec extends PokerSpec {
  "Four of a kind " should "be recognised" in {
    handWorks("8S 8D 8C 8H 4S", _.isFourOfAKind)
  }
  it should "be recognised regardless of order" in {
    val cards = List("8S", "8D", "8C", "8H", "4S")
    worksInAnyOrder(cards, _.isFourOfAKind)
  }  
}