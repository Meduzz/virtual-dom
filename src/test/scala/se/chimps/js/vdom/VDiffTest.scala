package se.chimps.js.vdom

import org.scalatest.FunSuite
import se.chimps.js.vdom.hypertext.Explicits

class VDiffTest extends FunSuite with VDiff {

	test("what's the difference between 2 divs?") {
		import Explicits.explicits

		val div1 = "div".tag
		val div2 = "div".tag

		val d = diff(div1, div2)

		assert(d.isEmpty)
	}

	test("when childcount differs, blow the tag clean and insert the new ones") {
		import Explicits.explicits

		val tag1 = "ul".h(
			"li".text("One"),
			"li".text("Two")
		)

		val tag2 = "ul".h(
			"li".text("Three")
		)

		val d = diff(tag1, tag2)

		assert(d.size == 2)

		val vanish = d.head

		assert(vanish.isInstanceOf[EmptyTag])
		val vanishReal = vanish.asInstanceOf[EmptyTag]

		assert(vanishReal.tag.isInstanceOf[RealTag])

		val cleanTag = vanishReal.tag.asInstanceOf[RealTag]

		assert(cleanTag.tag == "ul")

		val insert = d.last

		assert(insert.isInstanceOf[InsertNode])

		val insertReal = insert.asInstanceOf[InsertNode]

		assert(insertReal.parentTag.isInstanceOf[RealTag])

		val insertUl = insertReal.parentTag.asInstanceOf[RealTag]

		assert(insertUl.tag == "ul")

		assert(insertReal.tag.isInstanceOf[RealTag])

		val insertLi = insertReal.tag.asInstanceOf[RealTag]

		assert(insertLi.tag == "li")
	}

	test("replacing text is cake walk") {
		import Explicits.explicits

		val tag1 = ".one".h(
			".two".h(
				".three".text("old")
			)
		)

		val tag2 = ".one".h(
			".two".h(
				".three".text("new")
			)
		)

		val d = diff(tag1, tag2)

		assert(d.size == 1)

		val replace = d.head

		assert(replace.isInstanceOf[ReplaceText])

		val replaceReal = replace.asInstanceOf[ReplaceText]

		assert(replaceReal.text == "new")
		assert(replaceReal.tag.isInstanceOf[TextTag])

		val replaceTag = replaceReal.tag.asInstanceOf[TextTag]

		assert(replaceTag.text == "old")
	}

	test("replace tags are a cake walk") {
		import Explicits.explicits

		val tag1 = "div".h(
			"div".text("old")
		)

		val tag2 = "div".h(
			"span".text("new")
		)

		val d = diff(tag1, tag2)

		assert(d.size == 1)

		val replace = d.head

		assert(replace.isInstanceOf[ReplaceNode])

		val replaceNode = replace.asInstanceOf[ReplaceNode]

		assert(replaceNode.oldTag.isInstanceOf[RealTag])

		val oldTag = replaceNode.oldTag.asInstanceOf[RealTag]

		assert(oldTag.tag == "div")
		assert(replaceNode.tag.isInstanceOf[RealTag])

		val newTag = replaceNode.tag.asInstanceOf[RealTag]

		assert(newTag.tag == "span")
	}
}
