package com.eran.undo.service

import com.eran.undo.models.BoldAction
import com.eran.undo.models.DecorationAction
import com.eran.undo.models.ItalicAction
import com.eran.undo.models.RemoveAction
import com.eran.undo.models.TextAction
import com.eran.undo.models.UnderlineAction
import com.eran.undo.models.UndoRedo

/**
 * @author Eran Eichenbaum - 19/04/2021.
 */
class UndoRedoService : UndoRedo {

    override fun add(stringToAdd: String, position: Int?) {
        ActionManagerImpl.addToNormal(
            TextAction(stringToAdd, position ?: 0)
        )
    }

    override fun remove(fromPos: Int, toPos: Int) {
        ActionManagerImpl.addToNormal(
            RemoveAction(fromPosition = fromPos, toPosition = toPos)
        )
    }

    override fun bold(fromPos: Int, toPos: Int) {
        ActionManagerImpl.addToNormal(
            BoldAction(fromPosition = fromPos, toPosition = toPos)
        )
    }

    override fun italic(fromPos: Int, toPos: Int) {
        ActionManagerImpl.addToNormal(
            ItalicAction(fromPosition = fromPos, toPosition = toPos)
        )
    }

    override fun underline(fromPos: Int, toPos: Int) {
        ActionManagerImpl.addToNormal(
            UnderlineAction(fromPosition = fromPos, toPosition = toPos)
        )
    }

    override fun undo() {
        ActionManagerImpl.undo()
    }

    override fun redo() {
        ActionManagerImpl.redo()
    }

    override fun clear() {
        ActionManagerImpl.clear()
    }

    override fun print(): String {
        return StringBuilder().apply {
            ActionManagerImpl.getNormalStack().forEach { action ->
                when (action) {
                    is TextAction -> this.append(action.execute(this.toString()))
                    is DecorationAction -> this.setRange(0, this.length, action.execute(this.toString()))
                }
            }
            ActionManagerImpl.clear()
        }.toString()
    }
}
