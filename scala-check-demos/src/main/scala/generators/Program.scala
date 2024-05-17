package generators

import org.scalacheck.Gen.{choose, const, frequency, listOf, listOfN, sequence}
import org.scalacheck.Prop.forAll
import org.scalacheck.Test.Parameters
import org.scalacheck.{Gen, Test}

object Program {
  def main(args: Array[String]): Unit = {
    demoFrequency()
    demoListOf()
    demoSequence()
    demoConst()
  }

  def demoFrequency(): Unit = {
    val gen = frequency(
      (9, choose(1, 99)),
      (1, choose(100, 500))
    )
    runDemo("The Frequency Generator", gen, 100)
  }

  def demoListOf(): Unit = {
    val gen1 = listOf(choose(1, 100))
    val gen2 = listOfN(5, choose(10, 99))
    runDemo("The List Of Generator", gen1, 10)
    runDemo("The List Of N Generator", gen2, 10)
  }

  def demoSequence(): Unit = {
    val gen = sequence(List(choose(10, 99), choose(100, 999), const("Fred")))
    runDemo("The Sequence Generator", gen, 10)
  }

  def demoConst(): Unit = {
    val gen = const(7)
    runDemo("The Const Generator", gen, 10)
  }

  def runDemo(title: String, generator: Gen[Any], numTests: Int): Unit = {
    println(title)
    val prop = forAll(generator) { input: Any =>
      printf("\t%s\n", input)
      true
    }
    Test.check(Parameters.default.withMinSuccessfulTests(numTests), prop)
  }
}
