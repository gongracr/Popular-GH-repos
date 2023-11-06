package network.model

import com.google.gson.annotations.SerializedName

data class SearchBaseModel(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<GHProjectDTO>,
)
