package com.emaginalabs.maxibon

import com.emaginalabs.maxibon.generators._
import com.emaginalabs.maxibon.matchers._
import org.scalacheck.Gen.{listOf, nonEmptyListOf}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class KarumiHQsSpec extends FlatSpec with PropertyChecks with Matchers {

  "KarumiHQs" should "start the day with the maximum number of maxibons" in new Fixtures {
    givenTheOfficeHasAllMaxibons()

    office.maxibonsLeft shouldBe MaxMaxibons
  }

  it should "always has more than the minimum expected maxibons in the fridge" in new Fixtures {
    forAll(arbitraryDeveloper) { developer =>
      givenTheOfficeHasAllMaxibons()

      office.openFridge(developer)

      office.maxibonsLeft > MinMaxibons
    }
  }

  it should "ensure up to maximum maxibons are bought if there are less than the minimum in the fridge" in new Fixtures {
    forAll(arbitraryHungryDeveloper) { developer =>
      givenTheOfficeHasAllMaxibons()

      office.openFridge(developer)

      office should haveUpToMaxibons(MaxMaxibons)
    }
  }

  it should "request for more maxibons using the chat if there are less than the minimum in the fridge after a single developer grabs maxibons" in new Fixtures {
    forAll(arbitraryHungryDeveloper) { developer =>
      givenTheOfficeHasAllMaxibons()

      office.openFridge(developer)

      dummyChat.lastMessageSent shouldBe Some(
        s"Hi guys, I'm ${developer.name}. We need more maxibons!")
    }
  }

  it should "never request more maxibons to the team using the chat if there are more than 2 in the fridge" in new Fixtures {
    forAll(arbitraryNotSoHungryDeveloper) { developer =>
      givenTheOfficeHasAllMaxibons()

      office.openFridge(developer)

      dummyChat.lastMessageSent shouldBe 'empty
    }
  }

  it should "always has more than two maxibons in the fridge even if some karumies grab maxibons in group" in new Fixtures {
    forAll(listOf(arbitraryDeveloper)) { developers =>
      givenTheOfficeHasAllMaxibons()

      office.openFridge(developers)

      office.maxibonsLeft > MinMaxibons
    }
  }

  it should "ensure up to maximum maxibons are bought if there are less than the minimum in the fridge after a group of developers grab maxibons" in new Fixtures {
    forAll(listOf(arbitraryDeveloper)) { developers =>
      givenTheOfficeHasAllMaxibons()

      office.openFridge(developers)

      office should haveUpToMaxibons(developers)
    }
  }

  it should "request 10 more maxibons using the chat if there are less than 3 in the fridge when grabbing maxibons in group" in new Fixtures {
    forAll(nonEmptyListOf(arbitraryHungryDeveloper)) { developers =>
      givenTheOfficeHasAllMaxibons()

      office.openFridge(developers)

      dummyChat.lastMessageSent.get should not be 'empty
    }
  }

  it should "never request more maxibons to the team using the chat if there are more than 2 in the fridge when grabbing maxibons in group" in new Fixtures {
    forAll(
      generators.listOf(0,
                        MaxAmountOfDevelopersToNotBuyMoreMaxibons,
                        arbitraryDeveloperWhoGrab(1))) { developers =>
      givenTheOfficeHasAllMaxibons()

      office.openFridge(developers)

      dummyChat.lastMessageSent shouldBe 'empty
    }
  }

  class Fixtures {
    // I could use the values defined in KarumiHQs.scala but if I change them by mistake, my tests won't detect the error :(
    protected val MinMaxibons = 2
    protected val MaxMaxibons = 10
    protected val MaxAmountOfDevelopersToNotBuyMoreMaxibons = MaxMaxibons - MinMaxibons - 1

    protected var dummyChat = new DummyChat()
    protected var office = new KarumiHQs(dummyChat)

    protected def givenTheOfficeHasAllMaxibons() = {
      dummyChat = new DummyChat()
      office = new KarumiHQs(dummyChat)
    }

    protected class DummyChat extends Chat {
      var lastMessageSent: Option[String] = None

      override def sendMessage(message: String): String = {
        lastMessageSent = Some(message)
        message
      }
    }
  }
}
