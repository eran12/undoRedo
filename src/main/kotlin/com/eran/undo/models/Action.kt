package com.eran.undo.models

/**
 * @author Eran Eichenbaum - 19/04/2021.
 */
abstract class Action(
    open val value: String? = "",
    open val fromPosition: Int = -1,
    open val toPosition: Int,
    open val startElement: String = "",
    open val endElement: String = "",
) {
    abstract fun execute(text: String): String
}

data class TextAction(
    override val value: String,
    override val fromPosition: Int,
    override val toPosition: Int = -1
) : Action(value, fromPosition, toPosition) {
    override fun execute(text: String): String {
        return if (text.isNotBlank()) {
            StringBuilder().apply {
                if (fromPosition > -1 && this.length > fromPosition) {
                    this.insert(fromPosition, value)
                } else {
                    this.append(value)
                }
                if (toPosition > -1 && this.length > toPosition) {
                    this.insert(toPosition, endElement)
                }
            }.toString()
        } else {
            value
        }
    }
}

abstract class DecorationAction(
    override val fromPosition: Int,
    override val toPosition: Int,
    override val startElement: String,
    override val endElement: String,
) : Action(
    null, fromPosition, toPosition, startElement, endElement
) {
    override fun execute(text: String): String {
        return if (text.length > toPosition) {
            StringBuilder().apply {
                this.append(text)
                this.insert(toPosition, endElement)
                this.insert(fromPosition, startElement)
            }.toString()
        } else {
            text
        }
    }
}

data class RemoveAction(
    override val fromPosition: Int,
    override val toPosition: Int,
) : DecorationAction(fromPosition, toPosition, "", "") {
    override fun execute(text: String): String {
        return if (text.length > fromPosition) {
            StringBuilder().apply {
                this.append(text)
                if (this.length > toPosition) {
                    this.setRange(fromPosition, toPosition, startElement)
                }
            }.toString()
        } else {
            text
        }
    }
}

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
