package com.ayesh.leveintest.presantation.navigation

import kotlinx.serialization.Serializable

sealed class SubGraph {

    @Serializable
    data object Dashboard : SubGraph()
}