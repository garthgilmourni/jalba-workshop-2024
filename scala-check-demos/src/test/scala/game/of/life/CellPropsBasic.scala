package game.of.life

import org.scalacheck.Properties
import org.scalacheck.Prop.forAllNoShrink
import org.scalacheck.Gen._

import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._
import scala.collection.Seq


object CellPropsBasic extends Properties("Cell") {
  private def buildData() = {
    def buildNeighbours() = {
      val neighbours = new ListBuffer[Cell]
      val emptyList = List[Cell]().asJava

      (1 to 8).foreach(_ => neighbours += new Cell(emptyList))
      neighbours.toList
    }

    val neighbours = buildNeighbours()
    val cell = new Cell(neighbours.asJava)
    (cell, neighbours)
  }

  private val threeGen = pick(3, 0 to 7)

  private val lessThanTwoGen = for {
    num <- choose(0, 1)
    values <- pick(num, 0 to 7)
  } yield values

  private val moreThanThreeGen = for {
    num <- choose(4, 8)
    values <- pick(num, 0 to 7)
  } yield values

  property("becomes alive with three live neighbours") = forAllNoShrink(threeGen) { values: Seq[Int] =>
    val (cell, neighbours) = buildData()

    values.foreach(neighbours(_).makeAlive())

    cell.changeState()
    cell.isAlive
  }

  property("starves with less than two live neighbours") = forAllNoShrink(lessThanTwoGen) { values: Seq[Int] =>
    val (cell, neighbours) = buildData()

    cell.makeAlive()
    values.foreach(neighbours(_).makeAlive())

    cell.changeState()
    cell.isDead
  }

  property("overcrowded with more than three live neighbours") = forAllNoShrink(moreThanThreeGen) { values: Seq[Int] =>
    val (cell, neighbours) = buildData()

    cell.makeAlive()
    values.foreach(neighbours(_).makeAlive())

    cell.changeState()
    cell.isDead
  }
}
