package com.eran.undo

import com.eran.undo.service.UndoRedoService

class UndoApplication

fun main(args: Array<String>) {
	val undoRedoService = UndoRedoService()

	undoRedoService.add("ben")
	undoRedoService.add("222")
	undoRedoService.bold(0, 2)
	undoRedoService.underline(4, 10)
	undoRedoService.undo()
	undoRedoService.redo()
	undoRedoService.remove(9, 14)
//	undoRedoService.undo()
	println(undoRedoService.print())

}
