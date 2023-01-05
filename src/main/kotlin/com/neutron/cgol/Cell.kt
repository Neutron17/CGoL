package com.neutron.cgol

// _skip: when true, doesn't append com.neutron.cgol.Cell to allCells, see constructor(pos)
data class Cell(val pos: Pos<Int, Int>, val isAlive: Boolean, val _skip: Boolean = false) {
	// Doesn't append to allCells
	constructor(pos: Pos<Int, Int>) : this(pos, true, true);
	init {
		if(!_skip)
			allCells.add(this)
	}
	fun countLivingNeighbours(livings: Set<Cell>): Int {
		var ret = 0
		livings.filter { it: Cell ->
			for (i in 0 until 8)
				if(it.pos == pos + offsets[i])
					return@filter true
			return@filter false
		}.forEach { _ -> ret++ }
		return ret
	}
	/* Returns X-s, where P is this
	 *  	XXX
	 *		XPX
	 *  	XXX
	 */
	fun neighbours(): Set<Cell> {
		val ret = mutableSetOf<Cell>()
		for(i in 0 until 8)
			ret.add(Cell(pos + offsets[i], true))
		return ret
	}
	fun aliveNeighbours(): Set<Cell> {
		val ret = mutableSetOf<Cell>()
		allCells.filter { it: Cell ->
			for (i in 0 until 8)
				if(it.pos == pos + offsets[i])
					return@filter true
			return@filter false
		}.forEach { ret.add(it) }
		return ret
	}
	fun die() = allCells.remove(this)

	companion object {
		// Every cell used, both alive, and updated
		val allCells = mutableSetOf<Cell>()
		// relative offsets by which neighbours can be calculated, where com.neutron.cgol.Pos(0,0) is the origin
		private val offsets = arrayListOf(
			Pos(-1, -1), Pos(0, -1), Pos(1, -1),
			Pos(-1, 0), Pos(1, 0),
			Pos(-1, 1), Pos(0, 1), Pos(1, 1)
		)
	}
}
