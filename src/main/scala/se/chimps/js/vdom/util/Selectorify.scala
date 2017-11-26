package se.chimps.js.vdom.util

import se.chimps.js.vdom.attributes.AttributePair
import se.chimps.js.vdom.{RealTag, Tag, TextTag}

class Selectorify(val any:Tag) {
	def mkSelector:String = {
		var tags:Seq[Tag] = Seq(any)
		var t = any
		while (t.parent != null) {
			tags = Seq(t.parent) ++ tags
			t = t.parent
		}
		tags = Seq(t) ++ tags

		tags.tail.map(t => createSelector(t)).foldLeft("")((a,b) => {
			if (b.startsWith(":")) {
				a + b
			} else {
				if (a.isEmpty) {
					b
				} else {
					a + " > " + b
				}
			}
		})
	}

	private def createSelector(any:Tag):String = {
		val index = position(any)
		val nodeIndexSelector = if (index > 0) {
			s":nth-child(${index + 1})"
		} else {
			""
		}

		any match {
			case add:RealTag => {
				val tag = add.tag
				val tagAttribs = add.attr.filter(!_.eventHandler).map(_.asInstanceOf[AttributePair])
				val cls = tagAttribs.find(a => a.key == "class").map(c => prefix(".", c.value.split(" ").mkString("."))).getOrElse(nodeIndexSelector)
				val id = tagAttribs.find(a => a.key == "id").map(t => prefix("#", t.value)).getOrElse("")

				s"$tag$id$cls"
			}
			case t:TextTag => {
				createSelector(t.parent)
			}
		}
	}

	private def prefix(pref:String, value:String):String = {
		if (value.nonEmpty) {
			s"$pref$value"
		} else {
			value
		}
	}

	private def position(any:Tag):Int = {
		if (any.parent != null) {
			any.parent.asInstanceOf[RealTag].children.takeWhile(t => {
				!roughlyEquals(t)
			}).size
		} else {
			0
		}
	}

	private def roughlyEquals(other:Tag):Boolean = {
		any match {
			case ar:RealTag => {
				other match {
					case or:RealTag => {
						ar.tag == or.tag && ar.attr == or.attr && ar.children == or.children
					}
					case _ => false
				}
			}
			case at:TextTag => {
				other match {
					case ot:TextTag => {
						at.text == ot.text
					}
					case _ => false
				}
			}
		}
	}
}
