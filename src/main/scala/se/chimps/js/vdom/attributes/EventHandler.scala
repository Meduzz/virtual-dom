package se.chimps.js.vdom.attributes

import org.scalajs.dom.raw.Event

trait EventHandler[T <: Event] {
	def :=(func:Function1[T, Unit]):Attribute
}
