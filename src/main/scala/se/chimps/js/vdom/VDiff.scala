package se.chimps.js.vdom

trait VDiff {
	def diff(a:Tag, b:Tag):Seq[Patch] = {
		if (a.equals(b)) {
			Seq()
		} else {
			a match {
				case aReal:RealTag => {
					b match {
						case bReal:RealTag => {
							if (aReal.tag != bReal.tag || aReal.attr != bReal.attr) {
								Seq(ReplaceNode(aReal, bReal))
							} else {
								if (aReal.children.size != bReal.children.size) {
									Seq(EmptyTag(aReal)) ++ bReal.children.map(t => InsertNode(aReal, t))
								} else {
									aReal.children.zip(bReal.children)
										.flatMap(c => {
											val (ac, bc) = c
											if (!ac.equals(bc)) {
												diff(ac, bc)
											} else {
												Seq()
											}
										})
								}
							}
						}
						case bText:TextTag => {
							Seq(ReplaceNode(aReal, b))
						}
					}
				}
				case aText:TextTag => {
					b match {
						case bReal:RealTag => {
							Seq(EmptyTag(aText.parent), InsertNode(aText.parent, bReal))
						}
						case bText:TextTag => {
							Seq(ReplaceText(aText, bText.text))
						}
					}
				}
			}
		}
	}
}
