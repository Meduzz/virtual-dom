package se.chimps.js.vdom.util

import se.chimps.js.vdom.attributes.{Attribute, Property}

object PropertyBuilder {
	def apply(key:String):Property = (value:String) => Attribute(key, value)
}
