package cz.kiec.idontwanttosee.dbs.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    "rule",
    indices = [Index(value = ["filter_package_name", "filter_ignore_ongoing", "filter_ignore_with_progress_bar"])]
)
data class Rule(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @Embedded("filter_")
    val filters: Filters,
    @Embedded("action_")
    val actions: Actions,
) {
    data class Filters(
        @ColumnInfo(name = "package_name") val packageName: String,
        @ColumnInfo(name = "ignore_ongoing") val ignoreOngoing: Boolean,
        @ColumnInfo(name = "ignore_with_progress_bar") val ignoreWithProgressBar: Boolean,
    )

    data class Actions(
        @ColumnInfo(name = "hide_title") val hideTitle: Boolean,
        @ColumnInfo(name = "hide_content") val hideContent: Boolean,
        @ColumnInfo(name = "hide_large_image") val hideLargeImage: Boolean,
    )
}