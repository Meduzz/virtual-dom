package se.chimps.js.vdom

import org.scalajs.dom
import org.scalajs.dom.Element
import org.scalajs.dom.raw.Node
import se.chimps.js.vdom.attributes.{Attribute, AttributePair, Listener}
import se.chimps.js.vdom.util.Selectorify

trait VDom {

	def renderTag:PartialFunction[Tag, Node] = {
		case t:RealTag => {
			val element = dom.document.createElement(t.tag)
			applyAttributes(element, t.attr)
			t.children.foreach(t => {
				val child = renderTag(t)
				element.appendChild(child)
			})

			element
		}
		case TextTag(text, parent) => {
			dom.document.createTextNode(text)
		}
	}

	private def applyAttributes(elem:Element, attr:Seq[Attribute]):Unit = {
		attr.foreach({
			case AttributePair(key, value) => elem.setAttribute(key, value)
			case l:Listener[_] => elem.addEventListener(l.key, l.func)
		})
	}

	def applyPatches(node:Node, patches:Seq[Patch]):Unit = {
		patches.foreach({
			case InsertNode(parent, ny) => {
				val selector = parent.mkSelector

				if (selector.isEmpty) {
					node.appendChild(renderTag(ny))
				} else {
					val c = node.asInstanceOf[Element].querySelector(selector)

					if (c == null) {
						println(s"Selector ($selector) returned nothing!")
					} else {
						c.appendChild(renderTag(ny))
					}
				}
			}
			case ReplaceNode(old, ny) => {
				val selector = old.mkSelector

				if (selector.isEmpty) {
					val ersatt = renderTag(ny)
					val children = node.childNodes
					while (children.length > 0) {
						node.removeChild(children(0))
					}
					node.appendChild(ersatt)
				} else {
					val ersatt = renderTag(ny)
					val tag = node.asInstanceOf[Element].querySelector(selector)
					if (tag == null) {
						println(s"Selector ($selector) returned nothing!")
					} else {
						tag.parentNode.replaceChild(ersatt, tag)
					}
				}
			}
			case ReplaceText(tag, text) => {
				val selector = tag.mkSelector

				if (selector.isEmpty) {
					node.textContent = text
				} else {
					val c = node.asInstanceOf[Element].querySelector(selector)
					if (c == null) {
						println(s"Selector ($selector) returned nothing!")
					} else {
						c.textContent = text
					}
				}
			}
			case EmptyTag(tag) => {
				val selector = tag.mkSelector

				val target = if (selector.isEmpty) {
					node
				} else {
					node.asInstanceOf[Element].querySelector(selector)
				}

				if (target == null) {
					println(s"Selector ($selector) returned nothing!")
				} else {
					val children = target.childNodes
					while (children.length > 0) {
						target.removeChild(children(0))
					}

					target.textContent = ""
				}
			}
		})
	}

	implicit def selectorify(any:Tag):Selectorify = new Selectorify(any)
}
