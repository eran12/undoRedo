package com.eran.undo.models

/**
 * @author Eran Eichenbaum - 22/04/2021.
 */
interface ActionManager {
    fun addToNormal(action: Action)
    fun undo()
    fun redo()
    fun clear()
    fun getNormalStack(): List<Action>
}
