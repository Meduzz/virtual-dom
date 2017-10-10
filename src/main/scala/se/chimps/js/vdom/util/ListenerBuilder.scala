package se.chimps.js.vdom.util

import org.scalajs.dom.Event
import se.chimps.js.vdom.attributes.{EventHandler, Listener}

object ListenerBuilder {
	def apply(key:String):EventHandler = (func:Function1[Event, Unit]) => Listener(key, func)
}
