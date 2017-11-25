package se.chimps.js.vdom.util

import org.scalajs.dom.Event
import se.chimps.js.vdom.attributes.{EventHandler, Listener}

object ListenerBuilder {
	def apply[T <: Event](key:String):EventHandler[T] = (func:Function1[T, Unit]) => Listener[T](key, func)
}
