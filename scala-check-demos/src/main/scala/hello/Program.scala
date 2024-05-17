package hello

import org.scalacheck.Prop._
import org.scalacheck.Test.Parameters

object Program {
  def main(args: Array[String]): Unit = {
    val prop = forAll { (no1: Int, no2: Int) =>
      printf("Checking with %d and %d\n",no1,no2)
      (no1 + no2) == (no2 + no1)
    }
    val params = Parameters.default.withMinSuccessfulTests(10)
    prop.check(params)
  }
}
