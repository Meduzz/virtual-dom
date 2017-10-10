package se.chimps.js.vdom

import se.chimps.js.vdom.attributes.Attribute

// TODO the parent needs to be removed, it's a copy of the tree, not a reference.

trait Tag extends Item {
	def parent:Tag
}
case class RealTag(tag:String, attributes:Seq[Item] = Seq(), parent:Tag = null) extends Tag {
	def attr:Seq[Attribute] = attributes.filter({
		case a:Attribute => true
		case _ => false
	}).map(_.asInstanceOf[Attribute])

	def children:Seq[Tag] = attributes.filter({
		case t:Tag => true
		case _ => false
	}).map(_.asInstanceOf[Tag])
}
case class TextTag(text:String, parent:Tag = null) extends Tag
