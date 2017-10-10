package se.chimps.js.vdom.hypertext

object Explicits {

	implicit def explicits(text:String):ExplicitH = new ExplicitH(text)

}
