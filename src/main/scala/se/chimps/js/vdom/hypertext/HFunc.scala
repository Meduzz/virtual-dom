package se.chimps.js.vdom.hypertext

import se.chimps.js.vdom.{Item, RealTag, Tag, TextTag}
import se.chimps.js.vdom.attributes.{Attribute, Attributes}

trait HFunc {
	def h(hyperscript:String, attributes:Item*):Tag = {
		val (tagName, attr) = parseHyperscript(hyperscript)
		val tag = RealTag(tagName, attr ++ attributes)
		val c = tag.children.map({
			case c:RealTag => c.copy(parent = tag)
			case t:TextTag => t.copy(parent = tag)
		})
		tag.copy(attributes = tag.attr ++ c)
	}
	def h(hyperscript:String, attributes:Attribute*)(text:String):Tag = {
		val (tagName, attr) = parseHyperscript(hyperscript)
		val textTag = if (text.nonEmpty) {
			Seq(TextTag(text))
		} else {
			Seq()
		}
		val tag = RealTag(tagName, attr ++ attributes ++ textTag)
		val c = tag.children.map({
			case c:RealTag => c.copy(parent = tag)
			case t:TextTag => t.copy(parent = tag)
		})
		tag.copy(attributes = tag.attr ++ c)
	}
	def text(text:String):Tag = TextTag(text)

	private def parseHyperscript(script:String):(String, Seq[Attribute]) = {
		var content = script
		var tag = "div"
		var id = ""
		var cls = ""
		var hasId = false

		if (content.contains("#")) {
			val idsplit = content.split("#")
			tag = idsplit(0)
			content = idsplit(1)
			hasId = true
		}

		if (content.contains('.')) {
			var clsSplit = content.split('.')

			if (hasId) {
				id = clsSplit(0)
				cls = clsSplit.tail.mkString(" ")
			} else {
				if (clsSplit(0).nonEmpty) {
					tag = clsSplit(0)
					clsSplit = clsSplit.tail
				} else {
					clsSplit = clsSplit.tail
				}

				cls = clsSplit.mkString(" ")
			}
		} else {
			tag = content
		}

		var attr:Seq[Attribute] = Seq()

		if (id.nonEmpty) {
			attr = attr ++ Seq(Attributes.id := id)
		}

		if (cls.nonEmpty) {
			attr = attr ++ Seq(Attributes.cls := cls)
		}

		(tag, attr)
	}
}
