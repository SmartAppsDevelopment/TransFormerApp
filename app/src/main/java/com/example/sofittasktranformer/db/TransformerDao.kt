/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sofittasktranformer.db

import androidx.room.*
import com.example.sofittasktranformer.model.TranFormerDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransformerDao {
    @Query("SELECT * FROM TranFormerDataModel")
    fun getUserNameList(): Flow<List<TranFormerDataModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTransFormer(dataModel: TranFormerDataModel): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTransFormers(dataModel: List<TranFormerDataModel>)

    @Delete
    fun deleteTransFormer(datamodel: TranFormerDataModel)

    @Query("DELETE FROM tranformerdatamodel")
    fun deleteAllDataFromDb()

    @Query("DELETE FROM tranformerdatamodel where id=:userid")
    fun deleteWhereIdIs(userid: String)

    @Update
    fun updateTransFormer(dataModel: TranFormerDataModel)
}
