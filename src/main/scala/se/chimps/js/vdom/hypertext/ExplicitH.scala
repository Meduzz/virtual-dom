package se.chimps.js.vdom.hypertext

import se.chimps.js.vdom.{Item, Tag}
import se.chimps.js.vdom.attributes.Attribute

class ExplicitH(tagString:String) {

	val util:HFunc = new HFunc {}

	def tag:Tag = util.h(tagString)

	def text(moreText:String):Tag = util.h(tagString)(moreText)

	def h(attributes:Attribute*)(text:String):Tag = util.h(tagString, attributes : _*)(text)

	def h(attributes:Item*):Tag = util.h(tagString, attributes : _*)

}
