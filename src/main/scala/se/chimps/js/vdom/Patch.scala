package se.chimps.js.vdom

trait Patch
/*
	Append a tag into parentTag.
 */
case class InsertNode(parentTag:Tag, tag:Tag) extends Patch
/*
	Replace the old tag with the tag.
 */
case class ReplaceNode(oldTag:Tag, tag:Tag) extends Patch
/*
	Replace the text of this tag.
 */
case class ReplaceText(tag:Tag, text:String) extends Patch
/*
	Clean out a tag, so we can append a fresh batch of tags to it.
 */
case class EmptyTag(tag:Tag) extends Patch
