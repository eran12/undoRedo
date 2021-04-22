package com.eran.undo

import com.eran.undo.service.UndoRedoService

class UndoApplication

fun main(args: Array<String>) {
	val undoRedoService = UndoRedoService()

	undoRedoService.add("ben")
	undoRedoService.add("222")
	undoRedoService.bold(0, 2)
	undoRedoService.underline(0, 2)
	undoRedoService.undo()
	undoRedoService.undo()
	undoRedoService.redo()
	println(undoRedoService.print())

}
