package com.eran.undo.models

/**
 * @author Eran Eichenbaum - 19/04/2021.
 */
abstract class Action(
    open val value: String? = "",
    open val fromPosition: Int = 0,
    open val toPosition: Int,
    open val startElement: String = "",
    open val endElement: String = "",
)

abstract class DecorationAction(
    override val fromPosition: Int,
    override val toPosition: Int,
    override val startElement: String,
    override val endElement: String,
) : Action(null, fromPosition, toPosition, startElement, endElement)

data class TextAction(
    override val value: String,
    override val fromPosition: Int,
    override val toPosition: Int = value.length
) : Action(value, fromPosition, toPosition)

data class RemoveAction(
    override val fromPosition: Int,
    override val toPosition: Int,
) : DecorationAction(fromPosition, toPosition, "", "")

data class BoldAction(
    override val fromPosition: Int,
    override val toPosition: Int,
) : DecorationAction(fromPosition, toPosition, "<b>", "</b>")

data class UnderlineAction(
    override val fromPosition: Int,
    override val toPosition: Int,
) : DecorationAction(fromPosition, toPosition, "<u>", "</u>")

data class ItalicAction(
    override val fromPosition: Int,
    override val toPosition: Int,
) : DecorationAction(fromPosition, toPosition, "<i>", "</i>")
