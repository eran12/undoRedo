package com.eran.undo.models

/**
 * @author Eran Eichenbaum - 19/04/2021.
 */
interface UndoRedo {

    fun add(stringToAdd: String, position: Int? = stringToAdd.length)
    fun remove(fromPos: Int, toPos: Int)

    fun bold(fromPos: Int, toPos: Int)
    fun italic(fromPos: Int, toPos: Int)
    fun underline(fromPos: Int, toPos: Int)

    fun undo()
    fun redo()
    fun clear()
    fun print(): String
}
