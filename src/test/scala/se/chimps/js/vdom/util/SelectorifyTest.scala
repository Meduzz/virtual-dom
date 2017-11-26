package se.chimps.js.vdom.util

import org.scalatest.FunSuite
import se.chimps.js.vdom.RealTag
import se.chimps.js.vdom.hypertext.HFunc

class SelectorifyTest extends FunSuite with HFunc {

	test("as simple as stairs") {
		val tag = h("p",
			h("p",
				h("p")("")))

		val subject = new Selectorify(tag.asInstanceOf[RealTag].children.head.asInstanceOf[RealTag].children.head)

		assert(subject.mkSelector == "p > p")
	}

	test("with an eye for details") {
		val tag = h("p",
			h("p",
				h("p.eye")("")))

		val subject = new Selectorify(tag.asInstanceOf[RealTag].children.head.asInstanceOf[RealTag].children.head)

		assert(subject.mkSelector == "p > p.eye")
	}

	test("they all look the same!?") {
		val tag = h("ul",
			h("li")("one"),
			h("li")("two"),
			h("li")("three"))

		val subject = new Selectorify(tag.asInstanceOf[RealTag].children.last)

		assert(subject.mkSelector == "ul > li:nth-child(3)")
	}

}
