package com.neutron.cgol

data class Pos<X, Y>(val x: X, val y: Y) {
	operator fun Pos<Int, Int>.plus(pair: Pos<Int, Int>) = Pos(this.x + pair.x, this.y + pair.y)
}
