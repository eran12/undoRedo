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
            TextAction(stringToAdd, position ?: stringToAdd.length)
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
            val decorationMap: MutableMap<Pair<Int, Int>, Pair<String, String>?> = mutableMapOf()
            ActionManagerImpl.getNormalStack().forEach { action ->
                when (action) {
                    is TextAction -> this.append(action.value)
                    is RemoveAction -> {
                        // TODO: fix when word is removing the tags are wrapping over
                        val positions: Pair<Int, Int> = action.fromPosition to action.toPosition
                        decorationMap[positions] = null
                        this.deleteRange(positions.first, positions.second + 1)
                    }
                    is DecorationAction -> {
                        val positions: Pair<Int, Int> = action.fromPosition to action.toPosition

                        // building multi tags to rap string later
                        decorationMap[positions] = decorationMap[positions]?.copy(
                            first = action.startElement.plus(decorationMap[positions]!!.first),
                            second = decorationMap[positions]!!.second.plus(action.endElement)
                        ) ?: action.startElement to action.endElement
                    }
                    else -> throw Exception("Action not implemented for type: ${action.javaClass}")
                }
            }
            // attach tags to words by position
            decorationMap.forEach { (positions: Pair<Int, Int>, decorationToAdd: Pair<String, String>?) ->
                if (decorationToAdd != null) {
                    this.insert(positions.second + 1, decorationToAdd.second)
                    this.insert(positions.first, decorationToAdd.first)
                }
            }
            ActionManagerImpl.clear()
        }.toString()
    }
}
