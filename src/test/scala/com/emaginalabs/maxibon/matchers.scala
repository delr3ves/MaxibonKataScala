package com.emaginalabs.maxibon

import com.emaginalabs.maxibon.developer.Developer
import org.scalatest.matchers.{MatchResult, Matcher}

object matchers {

  def haveUpToMaxibons(expectedMaxibons: Int) = new Matcher[KarumiHQs] {
    override def apply(left: KarumiHQs): MatchResult = {
      MatchResult(
        matches = left.maxibonsLeft == expectedMaxibons,
        s"Expected office to have $expectedMaxibons but had ${left.maxibonsLeft}",
        "")
    }
  }

  def haveUpToMaxibons(developer: Developer) = KarumiHQsMatcher(Seq(developer))

  def haveUpToMaxibons(developers: Seq[Developer]) =
    KarumiHQsMatcher(developers)

  case class KarumiHQsMatcher(developers: Seq[Developer])
      extends Matcher[KarumiHQs] {
    override def apply(left: KarumiHQs): MatchResult = {
      val expectedMaxibons = developers.foldLeft(10) { (acc, dev) =>
        var maxibonsLeft = Math.max(0, acc - dev.numberOfMaxibonsToGrab)
        if (maxibonsLeft <= 2) {
          maxibonsLeft += 10
        }
        maxibonsLeft
      }
      haveUpToMaxibons(expectedMaxibons)(left)
    }
  }
}
