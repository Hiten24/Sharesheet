package com.example.sharesheet

import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sharesheet.databinding.ItemAppBinding

class AppsAdapter: RecyclerView.Adapter<AppsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemAppBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<ResolveInfo>() {
        override fun areItemsTheSame(
            oldItem: ResolveInfo,
            newItem: ResolveInfo
        ): Boolean {
            /*Log.d(
                "differFiles",
                "areItemsTheSame ${newItem.typeName} : ${oldItem.typeId == newItem.typeId}"
            )*/
            return oldItem.specificIndex == newItem.specificIndex /*&& oldItem.typeName == newItem.typeName*/
        }

        override fun areContentsTheSame(
            oldItem: ResolveInfo,
            newItem: ResolveInfo
        ): Boolean {
            return oldItem.resolvePackageName == newItem.resolvePackageName
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsAdapter.ViewHolder {
        return ViewHolder(ItemAppBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AppsAdapter.ViewHolder, position: Int) {
        val binding = holder.binding
        val app = differ.currentList[position]
        val context = holder.binding.root.context
        /*binding.icon.setImageDrawable(ContextCompat.getDrawable(context, app.iconResource))
        binding.name.text = context.getString(app.labelRes)*/
        binding.icon.setImageDrawable(app.loadIcon(context.packageManager))
        binding.name.text = app.loadLabel(context.packageManager)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}