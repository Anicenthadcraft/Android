package com.ancient.ancient_handcraft.app.CategoryWiseProduct.InputParams

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MainObj(
    @SerializedName("searchParams") var searchParams: SearchParams? = null,
    @SerializedName("pagination") var pagination: PaginationObj? = null,
    @SerializedName("sortField") var sortField: SortFieldObj? = null
) : Serializable