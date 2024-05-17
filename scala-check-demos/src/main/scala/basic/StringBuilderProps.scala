package basic

import org.scalacheck.Gen.alphaStr
import org.scalacheck.Properties
import org.scalacheck.Prop.{AnyOperators, propBoolean, forAll, all}

object StringBuilderProps extends Properties("StringBuilder") {
  property("appending") =
    forAll { (s1: String, s2: String) =>
      val sb = new StringBuilder()
      sb.append(s1).append(s2)
      sb.toString == (s1 + s2)
    }
  property("inserting") =
    forAll { (s1: String, s2: String) =>
      val sb = new StringBuilder()
      sb.insert(0, s1).insert(0, s2)
      all(
        sb.toString =? (s2 + s1),
        sb.toString.length =? (s2.length + s1.length)
      )
    }
  property("reversal") =
    forAll(alphaStr) { s: String =>
      (s.length > 1) ==> {
        val sb = new StringBuilder()
        sb.append(s)
        sb.toString != sb.reverse.toString
      }
    }
}
