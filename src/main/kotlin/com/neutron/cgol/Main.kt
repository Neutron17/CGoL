// Created by Neutron17, 2023.01.05
// Project name: Conway's com.neutron.cgol.Game of Life (CGoL - seagull)
package com.neutron.cgol

const val isDebug = true;

fun main(args: Array<String>) {
	val game = Game()
	//Toad:
	//  xxx
	// xxx
	//Glider:
	// 1   x
	// 2 x x
	// 3  xx
	game.addCells(
		// Box
		Cell(Pos(-2,0), true),
		Cell(Pos(-2,1), true),
		Cell(Pos(-1,0), true),
		Cell(Pos(-1,1), true),
		// Glider
		Cell(Pos(4,1), true),
		Cell(Pos(4,2), true),
		Cell(Pos(4,3), true),
		Cell(Pos(3,3), true),
		Cell(Pos(2,2), true),
	)
	game.init()
	game.printBoard()

	game.nextIterations(16)
	game.printBoard()
}
