package se.chimps.js.vdom.hypertext

import org.scalatest.FunSuite
import se.chimps.js.vdom.attributes.AttributePair
import se.chimps.js.vdom.{RealTag, TextTag}

class HFuncTest extends FunSuite with HFunc {

	test("parses hyperscript correctly") {
		val tag = h("div#id.class")("")

		assert(tag.isInstanceOf[RealTag])
		val real = tag.asInstanceOf[RealTag]
		assert(real.tag == "div")
		assert(real.attributes.length == 2)
		val id = real.attr.filter(_.key == "id").head
		val cls = real.attr.filter(_.key == "class").head

		assert(id.isInstanceOf[AttributePair])
		assert(!id.eventHandler)

		val idProp = id.asInstanceOf[AttributePair]
		assert(idProp.value == "id")

		assert(cls.isInstanceOf[AttributePair])
		assert(!cls.eventHandler)

		val clsProp = cls.asInstanceOf[AttributePair]

		assert(clsProp.value == "class")
	}

	test("when no tag is defined, div is used") {
		val tag = h(".hello")("")

		assert(tag.isInstanceOf[RealTag])

		val realTag = tag.asInstanceOf[RealTag]

		assert(realTag.tag == "div")
		assert(realTag.children.isEmpty)
	}

	test("the tree is real") {
		val ulTag = h("ul", h("li", h("span")("Hello")))

		assert(ulTag.isInstanceOf[RealTag])

		val realUlTag = ulTag.asInstanceOf[RealTag]

		assert(realUlTag.tag == "ul")
		assert(realUlTag.children.size == 1)
		assert(realUlTag.attr.isEmpty)
		assert(realUlTag.parent == null)

		val liTag = realUlTag.children.head

		assert(liTag != null)
		assert(liTag.isInstanceOf[RealTag])

		val realLiTag = liTag.asInstanceOf[RealTag]

		assert(realLiTag.tag == "li")
		assert(realLiTag.children.size == 1)
		assert(realLiTag.attr.isEmpty)

		val spanTag = realLiTag.children.head

		assert(spanTag != null)
		assert(spanTag.isInstanceOf[RealTag])

		val realSpanTag = spanTag.asInstanceOf[RealTag]

		assert(realSpanTag.tag == "span")
		assert(realSpanTag.children.size == 1)
		assert(realSpanTag.attr.isEmpty)

		val textTag = realSpanTag.children.head

		assert(textTag != null)
		assert(textTag.isInstanceOf[TextTag])

		val realTextTag = textTag.asInstanceOf[TextTag]

		assert(realTextTag.text == "Hello")
	}

	test("explicits does their thing too") {
		import Explicits.explicits

		val tag = "div#id.class".tag

		assert(tag.isInstanceOf[RealTag])
		val real = tag.asInstanceOf[RealTag]
		assert(real.tag == "div")
		assert(real.attributes.length == 2)
		val id = real.attr.filter(_.key == "id").head
		val cls = real.attr.filter(_.key == "class").head

		assert(id.isInstanceOf[AttributePair])
		assert(!id.eventHandler)

		val idProp = id.asInstanceOf[AttributePair]
		assert(idProp.value == "id")

		assert(cls.isInstanceOf[AttributePair])
		assert(!cls.eventHandler)

		val clsProp = cls.asInstanceOf[AttributePair]

		assert(clsProp.value == "class")
	}

	test("dish out some attributes") {
		import Explicits.explicits
		import se.chimps.js.vdom.attributes.Attributes._

		val tag = "a.random".h(href := "#/spam")("Spam")

		assert(tag.isInstanceOf[RealTag])

		val aTag = tag.asInstanceOf[RealTag]

		assert(aTag.children.size == 1)
		assert(aTag.attr.size == 2)

		val hrefAttr = aTag.attr.filter(_.key == "href").head

		assert(!hrefAttr.eventHandler)
		assert(hrefAttr.isInstanceOf[AttributePair])

		val hrefPair = hrefAttr.asInstanceOf[AttributePair]

		assert(hrefPair.value == "#/spam")

		val textTag = aTag.children.head

		assert(textTag.isInstanceOf[TextTag])

		val realTextTag = textTag.asInstanceOf[TextTag]

		assert(realTextTag.text == "Spam")
	}

	test("can you hear the events firing?") {
		import Explicits._
		import se.chimps.js.vdom.attributes.Attributes.click

		val tag = "a.btn".h(click := (e => println(e)), text("Click"))

		assert(tag.isInstanceOf[RealTag])

		val realTag = tag.asInstanceOf[RealTag]
		assert(realTag.attr.size == 2)
		assert(realTag.children.size == 1)

		assert(realTag.attr(1).eventHandler)
	}

}
