package com.emaginalabs.maxibon

import com.emaginalabs.maxibon.developer.Developer
import org.scalacheck.Gen

object generators {

  val arbitraryDeveloper: Gen[Developer] = for {
    name <- arbitraryNonEmptyString
    maxibonsToGrab <- Gen.choose(2, 10)
  } yield Developer(name = name, _numberOfMaxibonsToGrab = maxibonsToGrab)

  val arbitraryHungryDeveloper: Gen[Developer] = for {
    name <- arbitraryNonEmptyString
    maxibonsToGrab <- Gen.choose(8, Int.MaxValue)
  } yield Developer(name = name, _numberOfMaxibonsToGrab = maxibonsToGrab)

  val arbitraryNotSoHungryDeveloper: Gen[Developer] = for {
    name <- arbitraryNonEmptyString
    maxibonsToGrab <- Gen.choose(0, 7)
  } yield Developer(name = name, _numberOfMaxibonsToGrab = maxibonsToGrab)

  def arbitraryDeveloperWhoGrab(maxibonsToGrab: Int): Gen[Developer] =
    arbitraryDeveloper.map(_.copy(_numberOfMaxibonsToGrab = maxibonsToGrab))

  def listOf[T](min: Int, max: Int, generator: Gen[T]): Gen[List[T]] =
    for {
      numberOfElements <- Gen.choose(min, max)
      list <- Gen.listOfN(numberOfElements, generator)
    } yield (list)

  private def arbitraryNonEmptyString = {
    Gen.alphaStr.filter(!_.isEmpty)
  }

}
