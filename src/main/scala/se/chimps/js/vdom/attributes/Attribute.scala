package se.chimps.js.vdom.attributes

import org.scalajs.dom.Event
import se.chimps.js.vdom.Item

trait Attribute extends Item {
	def key:String

	def eventHandler:Boolean = this match {
		case l:Listener[_] => true
		case _ => false
	}
}

object Attribute {
	def apply(key:String, value:String):Attribute = AttributePair(key, value)
	def apply(key:String, func:Function1[Event, Unit]) = Listener(key, func)
}

case class AttributePair(key:String, value:String) extends Attribute
case class Listener[T <: Event](key:String, func:Function1[T, Unit]) extends Attribute
