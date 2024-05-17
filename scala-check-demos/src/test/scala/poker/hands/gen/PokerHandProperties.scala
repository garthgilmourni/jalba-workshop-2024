package poker.hands.gen

import org.scalacheck.Prop._
import org.scalacheck.{Gen, Properties }
import org.scalacheck.Test.Parameters
import poker.hands._

object PokerHandProperties extends Properties("PokerHand") {

  override def overrideParameters(p: Parameters): Parameters = {
    p.withMinSuccessfulTests(200000)
  }

  private def buildHandGen(): Gen[PokerHand] = {
    val rankGen = Gen.oneOf(List("A", "Q", "K", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"))
    val suitGen = Gen.oneOf(List("C", "H", "S", "D"))
    val cardGen = for {
      rank <- rankGen
      suit <- suitGen
    } yield rank + suit + " "

    Gen.listOfN(5, cardGen)           //create a list of 5 cards
      .suchThat(_.distinct.size == 5) //remove hands with duplicated cards
      .map(_.mkString)                //convert list to String
      .map(_.trim)                    //remove trailing space
      .map(PokerHand.buildHand)       //convert cards to poker hands
  }

  private val handGen = buildHandGen()

  property("Pair exists")            = exists(handGen)(_.isPair)
  property("Two Pair exists")        = exists(handGen)(_.isTwoPair)
  property("Three of a Kind exists") = exists(handGen)(_.isThreeOfAKind)
  property("Four of a Kind exists")  = exists(handGen)(_.isFourOfAKind)
  property("Straight exists")        = exists(handGen)(_.isStraight)
  property("Flush exists")           = exists(handGen)(_.isFlush)
  property("Full House exists")      = exists(handGen)(_.isFullHouse)
  property("Royal Flush exists")     = exists(handGen)(_.isRoyalFlush)

  property("All Full Houses are Pairs and Three Of A Kind")
    = forAll(handGen)(h => if (h.isFullHouse) h.isPair && h.isThreeOfAKind else true)

  property("All 2 Pairs are Pairs")
    = forAll(handGen)(h => if (h.isTwoPair) h.isPair else true)

  property("All Three Of A Kind are Pairs")
    = forAll(handGen)(h => if (h.isThreeOfAKind) h.isPair else true)

  property("All Four Of A Kind are Pairs and Three Of A Kind")
    = forAll(handGen)(h => if (h.isFourOfAKind) h.isPair && h.isThreeOfAKind else true)

  property("All Royal Flushes are Flushes")
    = forAll(handGen)(h => if (h.isRoyalFlush) h.isFlush else true)
}

