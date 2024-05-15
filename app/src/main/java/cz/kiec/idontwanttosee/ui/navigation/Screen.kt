package cz.kiec.idontwanttosee.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Main

@Serializable
data object Rules

@Serializable
data object AddRule

@Serializable
data class ModifyRule(val id: Long)