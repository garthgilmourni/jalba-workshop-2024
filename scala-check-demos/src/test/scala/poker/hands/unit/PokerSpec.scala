package poker.hands.unit

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import poker.hands.PokerHand

abstract class PokerSpec extends AnyFlatSpec with Matchers {
  def handWorks(cards : String, identifier: PokerHand => Boolean) {
    val hand = PokerHand.buildHand(cards)
    identifier(hand) should be (true)
  }
  def worksInAnyOrder(cards : List[String], func: PokerHand => Boolean) {
    for(cards <- cards.permutations.map(_.mkString(" "))) {
      handWorks(cards, func)
    }
  }
}
