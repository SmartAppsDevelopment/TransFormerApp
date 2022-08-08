package com.example.sofittasktranformer.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Umer Bilal
 * Created 8/6/2022 at 4:58 PM
 */


@Parcelize
data class TranFormerListDataModel(
    val id:String,
   val name:String,
   val rank:String,
   val iconUrl:String,
   val deleteClick:(trandModel:TranFormerListDataModel)->Unit,
   val editClick:(transModel:TranFormerListDataModel)->Unit
): Parcelable
    fun TranFormerDataModel.FromTranFormerDataModelToListModel(deleteClick: (pos:TranFormerListDataModel) -> Unit,editClick: (pos:TranFormerListDataModel) -> Unit):TranFormerListDataModel {
//        Strength + Intelligence + Speed + Endurance + Firepower
        val overallStrength=strength+intelligence+speed+endurance+firepower

        return TranFormerListDataModel(id, name!!,"Rank: ${overallStrength}",teamIcon!!, deleteClick,editClick)
    }
