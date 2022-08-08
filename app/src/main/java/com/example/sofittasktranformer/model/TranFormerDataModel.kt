package com.example.sofittasktranformer.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Umer Bilal
 * Created 8/6/2022 at 4:58 PM
 */

@Entity(tableName = "TranFormerDataModel", primaryKeys = ["id","name"])
@Parcelize
data class TranFormerDataModel(
    @SerializedName("courage") var courage: Int,
    @SerializedName("endurance") var endurance: Int,
    @SerializedName("firepower") var firepower: Int,
    @SerializedName("id") var id: String,
    @SerializedName("intelligence") var intelligence: Int,
    @SerializedName("name") var name: String,
    @SerializedName("rank") var rank: Int,
    @SerializedName("skill") var skill: Int,
    @SerializedName("speed") var speed: Int,
    @SerializedName("strength") var strength: Int,
    @SerializedName("team") var team: String,
    @SerializedName("team_icon") var teamIcon: String
): Parcelable