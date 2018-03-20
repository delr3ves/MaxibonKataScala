package com.emaginalabs.maxibon

import org.scalacheck.Gen
import org.scalatest.prop.{PropertyChecks, TableDrivenPropertyChecks}
import org.scalatest.{FlatSpec, Matchers}
import developer._

class DevelopersSpec
    extends FlatSpec
    with PropertyChecks
    with TableDrivenPropertyChecks
    with Matchers {

  "Developer" should "not grab a negative number of maxibons" in {
    forAll(Gen.alphaStr, Gen.choose(Int.MinValue, 0)) {
      (name, maxibonsToGrab) =>
        Developer(name, maxibonsToGrab).numberOfMaxibonsToGrab shouldBe 0
    }
  }

  it should "grab the expected positive number of maxibons" in {
    forAll(Gen.alphaStr, Gen.choose(0, Int.MaxValue)) {
      (name, maxibonsToGrab) =>
        Developer(name, maxibonsToGrab).numberOfMaxibonsToGrab shouldBe maxibonsToGrab
    }
  }

  val developers = Table(
    ("Developer", "Expected maxibons to grab"),
    (Karumies.Alberto, 1),
    (Karumies.Davide, 0),
    (Karumies.Fran, 1),
    (Karumies.Jorge, 1),
    (Karumies.Pedro, 3),
    (Karumies.Sergio, 2),
  )

  "Karumies" should "grab as much maxibons as they usually do" in {
    forAll(developers) { (developer, expectedMaxibonsToGrab) =>
      developer.numberOfMaxibonsToGrab shouldBe expectedMaxibonsToGrab
    }
  }

}
