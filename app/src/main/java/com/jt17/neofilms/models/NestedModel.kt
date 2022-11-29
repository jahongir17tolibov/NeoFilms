package com.jt17.neofilms.models

data class NestedModel(
    val titleName: String,
    val modelList: List<mainModel>
)
