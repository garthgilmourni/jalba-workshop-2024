package game.of.life

import org.scalacheck.Gen._
import org.scalacheck.Prop.{classify, collect, forAllNoShrink, propBoolean}
import org.scalacheck.{Prop, Properties}

import scala.jdk.CollectionConverters._
import scala.collection.Seq
import scala.collection.mutable.ListBuffer


object CellPropsAdvanced extends Properties("Cell") {
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

  private val numNineToHundred = choose(9, 100)

  private val threeGen = pick(3, 0 to 7)

  private val lessThanTwoGen = for {
    num <- choose(0, 1)
    values <- pick(num, 0 to 7)
  } yield values

  private val moreThanThreeGen = for {
    num <- choose(4, 8)
    values <- pick(num, 0 to 7)
  } yield values

  private val upToEightGen = for {
    num <- choose(1, 8)
    values <- pick(num, 0 to 7)
  } yield values

  property("throws with too many neighbours") = forAllNoShrink(numNineToHundred) { num =>
    val emptyList = List[Cell]().asJava
    val excessiveNeighbours = (1 to num).map(_ => new Cell(emptyList)).asJava

    lazy val cell = new Cell(excessiveNeighbours)
    Prop.throws(classOf[IllegalStateException]) {
      cell
    }
  }

  property("becomes alive with three live neighbours") = forAllNoShrink(threeGen) { values: Seq[Int] =>
    val (cell, neighbours) = buildData()

    values.foreach(neighbours(_).makeAlive())

    cell.changeState()

    classify(values.contains(7), "includes last neighbour") {
      classify(values.contains(0), "includes first neighbour") {
        cell.isAlive
      }
    }
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
    collect(values.length) {
      cell.isDead
    }
  }

  property("stays dead without three live neighbours") = forAllNoShrink(upToEightGen) { values: Seq[Int] =>
    val (cell, neighbours) = buildData()

    values.foreach(neighbours(_).makeAlive())
    cell.changeState()

    (values.length != 3) ==> cell.isDead
  }
}
