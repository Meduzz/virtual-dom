# virtual-dom
A different kind of dom, a scala.js based virtual-dom.

## Intro

To render tags you use h-functions

    h("ul#anid.anclass", h("li", h("span")("Hello")))

defined in hypertext.HFunc trait.

OR

You can import ```hypertext.Explicits._``` and party with code like

    "a.random".h(href := "#/spam")("Spam")

## The VDom part

Once you've designed your lovely html, you use the ```renderTag``` function of VDom trait.
That bugger will return a node, that scala.js can handle. Remember to store a copy, 
because YOU are responsible for the shadow part of the virtual-dom.

Once you've done some changes to your state or simply feels like it's time to update the dom.
Call the ```diff``` function of the VDiff trait with your new tag. This time
you will get a list of patches back, you apply these patches on the
original node, returned by ```renderTag``` previously by calling ```applyPatches```.

And like that your node's updated with the latest tags and data.

## Examples

Lets look at some code shall we?

    import org.scalajs
    import org.scalajs.dom.Event
    import se.chimps.js.vdom.attributes.Attributes._
    import se.chimps.js.vdom.hypertext.HFunc

    object Sandbox extends HFunc with VDom with VDiff {
    
    	def main(args:Array[String]):Unit = {
    		val button1 = h("button#b1.c1", `type` := "button", h("span")("Click"))
		    val content1 = h(".c1",
    			h("div",
		    		h("h1")("Header")),
    			h("div",
		    		h("ul",
    					h("li")("Signin"),
		    			h("li")("About"))),
    			h("div",
		    		h("form",
				    	h("div", h("label")("Username")),
    					h("div#id.class1.class2", h("input", `type` := "text", name := "username", value := "")("")), // TODO <- hmmm
		    			h("div", h("label")("Password")),
				    	h("div", h("input", `type` := "password", name:="password")("")) // TODO <- igen..
    				)
		    	)
    		)
    
    		val content2 = h(".c1",
		    	h("div",
				    h("h1")("Welcome stranger")),
    			h("div",
		    		h("ul",
				    	h("li")("About"),
    					h("li")("Sign out"))),
		    	h("div",
				    h("div.content")("Long, long text.")
    			)
		    )
    		val button = renderTag(button1)
		    val node = renderTag(content1)
    
    		button.addEventListener("click", (e:Event) => {
    			val patches = diff(content1, content2)
		    	applyPatches(node, patches)
    		})
    
    		scalajs.dom.document.querySelector("#knapp").appendChild(button)
		    scalajs.dom.document.querySelector("#content").appendChild(node)
    	}
    }
