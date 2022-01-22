package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.AdapterBottomBinding
import com.example.myapplication.databinding.AdapterMainBinding

class TestAdapter(var c: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mData: ArrayList<UserBean> = ArrayList()
    val loadMore:LoadMore = LoadMore()
    var content: Int = 1
    var bottom: Int = 2

    fun updateLoad(loadMore: LoadMore) {
        this.loadMore.showText = loadMore.showText
        this.loadMore.isShow = loadMore.isShow
        notifyDataSetChanged()
    }

    fun setDataUpdate(data: ArrayList<UserBean>) {
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < mData.size) {
            content
        } else {
            bottom
        }
    }

    class VHolder(binding: AdapterMainBinding) : RecyclerView.ViewHolder(binding.root) {
        var holderBinding = binding
    }

    class BottomHolder(bindingB: AdapterBottomBinding) : RecyclerView.ViewHolder(bindingB.root) {
        var holderBingB = bindingB

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == content) {
            return VHolder(AdapterMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else if (viewType == bottom) {
            return BottomHolder(AdapterBottomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        return BottomHolder(AdapterBottomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VHolder) {
            val d = mData.get(position)
            holder.holderBinding.userBean = d
            holder.holderBinding.executePendingBindings()
        } else if(holder is BottomHolder){
            holder.holderBingB.load = loadMore
            holder.holderBingB.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return mData.size + 1
    }
}