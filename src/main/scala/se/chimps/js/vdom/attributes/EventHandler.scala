package se.chimps.js.vdom.attributes

import org.scalajs.dom.Event

trait EventHandler {
	def :=(func:Function1[Event, Unit]):Attribute
}
