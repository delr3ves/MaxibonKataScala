package com.emaginalabs.maxibon

object developer {
  case class Developer(name: String, private val _numberOfMaxibonsToGrab: Int) {

    lazy val numberOfMaxibonsToGrab = Math.max(0, _numberOfMaxibonsToGrab)
  }

  object Karumies {
    val Alberto = Developer("Alberto", 1)
    val Davide = Developer("Davide", 0)
    val Fran = Developer("Fran", 1)
    val Jorge = Developer("Jorge", 1)
    val Pedro = Developer("Pedro", 3)
    val Sergio = Developer("Sergio", 2)
  }

}
