package com.neutron.cgol
class Game {
	// Set of all alive cells
	private val aliveCells = mutableSetOf<Cell>()
	// Set of all cells that will survive until the next iteration
	private val survivingCells = mutableSetOf<Cell>()
	// Set of all cells, influenced the last round
	private val updatedCells = mutableSetOf<Cell>()

	// Adds cells to the com.neutron.cgol.Game object
	fun addCells(vararg cells: Cell) {
		cells.forEach {
			if(it.isAlive) {
				aliveCells.add(it)
				updatedCells.addAll(it.neighbours())
			} else {
				it.die()
			}
		}
	}
	// Adds cell to the com.neutron.cgol.Game object
	fun addCell(cell: Cell): Boolean {
		updatedCells.addAll(cell.neighbours())
		return if(cell.isAlive)
			aliveCells.add(cell)
		else
			false
	}
	// Goes to the next nth iteration
	fun nextIterations(n: Int) {
		for(i in 1..n)
			nextIteration()
	}
	// Goes to the next iteration, enforcing the necessary rules
	fun nextIteration() {
		survivingCells.clear()
		val tmpAliveCells = mutableSetOf<Cell>()
		val tmpUpdatedCells = mutableSetOf<Cell>()
		aliveCells.forEach {
			val neighbourcount = it.countLivingNeighbours(aliveCells)
			//System.err.println("$it has $neighbourcount neighbours");
			if(neighbourcount == 2 || neighbourcount == 3)
				survivingCells.add(it)
		}
		if(isDebug)
			System.err.flush()
		for(i in updatedCells) {
			if(i.countLivingNeighbours(aliveCells) == 3) {
				tmpAliveCells.add(i)
				survivingCells.add(i)
				tmpUpdatedCells.addAll(i.neighbours())
				//	println("Created $i")
			} else {
				i.die()
			}
		}
		aliveCells.forEach {
			if(it !in survivingCells) {
				it.die()
				updatedCells.addAll(it.neighbours())
				//	println("Deleted $it")
			}
		}
		aliveCells.removeIf { it !in survivingCells }
		aliveCells.addAll(tmpAliveCells)
		updatedCells.clear()
		updatedCells.addAll(tmpUpdatedCells)
	}
	// Prints the part of the board, where there are alive cells
	// BTW, The board doesn't exist, only the cells and their position
	fun printBoard() {
		val xoff_l = aliveCells.minOf { it.pos.x }
		val xoff_r = aliveCells.maxOf { it.pos.x }
		val yoff_t = aliveCells.minOf { it.pos.y }
		val yoff_b = aliveCells.maxOf { it.pos.y }
		println("-"*(xoff_r-xoff_l+3))
		for(y in yoff_t-1..yoff_b+1) {
			print("|")
			for (x in xoff_l-1..xoff_r+1) {
				if(aliveCells.find { it.pos == Pos(x,y) } == null)
					print(" ")
				else
					print("X")
			}
			println("|")
		}
		println("-"*(xoff_r-xoff_l+3))
	}
	fun debug() {
		println("AliveCells: ")
		aliveCells.forEach { println("\t$it") }
		println("SurvivingCells: ")
		survivingCells.forEach { println("\t$it") }
		println("UpdatedCells:")
		updatedCells.forEach { println("\t$it") }
	}

	// Apply cells added by addCell(s)
	fun init() {
		aliveCells.forEach { updatedCells.addAll(it.neighbours()) }
	}
}
operator fun String.times(i: Int): String {
	var ret = this
	for(x in 0..i)
		ret = ret.plus(this)
	return ret
}
