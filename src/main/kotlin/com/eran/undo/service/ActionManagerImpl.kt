package com.eran.undo.service

import com.eran.undo.models.Action
import com.eran.undo.models.ActionManager
import java.util.*

/**
 * @author Eran Eichenbaum - 20/04/2021.
 */
object ActionManagerImpl : ActionManager {
    private val normalStack: Stack<Action> = Stack()
    private val undoStack: Stack<Action> = Stack()

    override fun addToNormal(action: Action) {
        normalStack.push(action)
    }

    override fun undo() {
        if (normalStack.isNotEmpty()) {
            undoStack.push(normalStack.pop())
        }
    }

    override fun redo() {
        if (undoStack.isNotEmpty()) {
            normalStack.push(undoStack.pop())
        }
    }

    override fun clear() {
        normalStack.clear()
        undoStack.clear()
    }

    override fun getNormalStack(): List<Action> {
        return normalStack.toList()
    }

}
