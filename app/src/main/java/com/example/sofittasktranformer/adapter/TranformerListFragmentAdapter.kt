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

package com.example.sofittasktranformer.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sofittasktranformer.R
import com.example.sofittasktranformer.databinding.TranformerListItemBinding
import com.example.sofittasktranformer.model.FromTranFormerDataModelToListModel
import com.example.sofittasktranformer.model.TranFormerDataModel
import com.example.sofittasktranformer.model.TranFormerListDataModel
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso


class TranformerListFragmentAdapter(
    var editClick: (pos: TranFormerListDataModel) -> Unit,
    val delClick: (pos: TranFormerListDataModel) -> Unit
) :
    ListAdapter<TranFormerDataModel, TranformerListFragmentAdapter.ViewHolder>(
        ResultItemDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.tranformer_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position).FromTranFormerDataModelToListModel(editClick, delClick))
    }

    class ViewHolder(
        private val binding: TranformerListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(tranformModel: TranFormerListDataModel) {
            with(binding) {
                ivDelete.setOnClickListener {
                    tranformModel.deleteClick.invoke(tranformModel)
                }
                ivEdit.setOnClickListener {
                    tranformModel.editClick.invoke(tranformModel)
                }
                model = tranformModel
                executePendingBindings()
            }
        }
    }
}

class ResultItemDiffCallback : DiffUtil.ItemCallback<TranFormerDataModel>() {

    override fun areItemsTheSame(
        oldItem: TranFormerDataModel,
        newItem: TranFormerDataModel
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(
        oldItem: TranFormerDataModel,
        newItem: TranFormerDataModel
    ): Boolean {
        return oldItem.name == newItem.name
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ShapeableImageView, url: String) {
    Picasso.get().load(url).error(R.drawable.ic_baseline_error_outline_24).into(view)
}

