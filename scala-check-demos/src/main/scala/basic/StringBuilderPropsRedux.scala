package basic

import org.scalacheck.Gen.alphaStr
import org.scalacheck.Test.Parameters
import org.scalacheck.Prop.{forAll, propBoolean}

object StringBuilderPropsRedux {
  def main(args:Array[String]): Unit = {
    val params = Parameters
      .default
      .withMinSuccessfulTests(10000)

    val prop = forAll(alphaStr) { s: String =>
      (s.length > 1) ==> {
        val sb = new StringBuilder()
        sb.append(s)
        sb.toString != sb.reverse.toString
      }
    }
    prop.check(params)
  }
}
