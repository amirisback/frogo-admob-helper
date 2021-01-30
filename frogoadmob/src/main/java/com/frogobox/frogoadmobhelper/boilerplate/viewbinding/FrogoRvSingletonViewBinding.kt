package com.frogobox.frogoadmobhelper.boilerplate.viewbinding

import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import com.frogobox.frogoadmobhelper.widget.FrogoAdmobRecyclerView
import com.frogobox.frogoadmobhelper.base.parent.FrogoRecyclerViewListener
import com.frogobox.frogoadmobhelper.util.FrogoRvConstant

/*
 * Created by Faisal Amir
 * =========================================
 * FrogoRecyclerViewAdapter
 * Copyright (C) 29/04/2020.      
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * FrogoBox Inc
 * com.frogobox.frogoadmobhelper.boilerplate
 * 
 */
class FrogoRvSingletonViewBinding<T, V : ViewBinding> :
    IFrogoRvSingletonViewBinding<T, V> {

    private lateinit var customViewBinding: V

    private lateinit var frogoViewAdapterBindingCallback: FrogoViewAdapterBindingCallback<T, V>
    private lateinit var frogoViewAdapterBinding: FrogoViewAdapterBinding<T, V>

    private lateinit var mFrogoAdmobRecyclerView: FrogoAdmobRecyclerView
    private var layoutSpanCount = 0
    private var optionLayoutManager = ""
    private var optionDividerItem = false
    private var listData: List<T>? = null
    private var optionAdapter = ""

    override fun initSingleton(frogoAdmobRecyclerView: FrogoAdmobRecyclerView): FrogoRvSingletonViewBinding<T, V> {
        mFrogoAdmobRecyclerView = frogoAdmobRecyclerView
        return this
    }

    override fun createLayoutLinearVertical(dividerItem: Boolean): FrogoRvSingletonViewBinding<T, V> {
        optionLayoutManager =
            FrogoRvConstant.LAYOUT_LINEAR_VERTICAL
        optionDividerItem = dividerItem
        Log.d("injector-layoutManager", optionLayoutManager)
        Log.d("injector-divider", optionDividerItem.toString())
        return this
    }

    override fun createLayoutLinearHorizontal(dividerItem: Boolean): FrogoRvSingletonViewBinding<T, V> {
        optionLayoutManager =
            FrogoRvConstant.LAYOUT_LINEAR_HORIZONTAL
        optionDividerItem = dividerItem
        Log.d("injector-layoutManager", optionLayoutManager)
        Log.d("injector-divider", optionDividerItem.toString())
        return this
    }

    override fun createLayoutStaggeredGrid(spanCount: Int): FrogoRvSingletonViewBinding<T, V> {
        optionLayoutManager =
            FrogoRvConstant.LAYOUT_STAGGERED_GRID
        layoutSpanCount = spanCount
        Log.d("injector-layoutManager", optionLayoutManager)
        Log.d("injector-divider", optionDividerItem.toString())
        return this
    }

    override fun createLayoutGrid(spanCount: Int): FrogoRvSingletonViewBinding<T, V> {
        optionLayoutManager =
            FrogoRvConstant.LAYOUT_GRID
        layoutSpanCount = spanCount
        Log.d("injector-layoutManager", optionLayoutManager)
        Log.d("injector-divider", optionDividerItem.toString())
        return this
    }

    override fun addData(listData: List<T>): FrogoRvSingletonViewBinding<T, V> {
        this.listData = listData
        Log.d("injector-listData", this.listData.toString())
        return this
    }

    override fun addCustomView(customViewBinding: V): FrogoRvSingletonViewBinding<T, V> {
        this.customViewBinding = customViewBinding
        Log.d("injector-customView", this.customViewBinding.toString())
        return this
    }

    override fun addCallback(
        frogoViewAdapterBindingCallback: FrogoViewAdapterBindingCallback<T, V>
    ): FrogoRvSingletonViewBinding<T, V> {
        this.frogoViewAdapterBindingCallback = frogoViewAdapterBindingCallback
        Log.d("injector-adaptCallback", this.frogoViewAdapterBindingCallback.toString())
        return this
    }

    private fun setupLayoutManager() {

        Log.d("injector-option", optionLayoutManager)
        Log.d("injector-divider", optionDividerItem.toString())
        Log.d("injector-spanCount", layoutSpanCount.toString())

        if (optionLayoutManager.equals(FrogoRvConstant.LAYOUT_LINEAR_VERTICAL)) {
            mFrogoAdmobRecyclerView.layoutManager =
                LinearLayoutManager(mFrogoAdmobRecyclerView.context, LinearLayoutManager.VERTICAL, false)
            if (optionDividerItem) {
                mFrogoAdmobRecyclerView.addItemDecoration(
                    DividerItemDecoration(
                        mFrogoAdmobRecyclerView.context,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
        } else if (optionLayoutManager.equals(FrogoRvConstant.LAYOUT_LINEAR_HORIZONTAL)) {
            mFrogoAdmobRecyclerView.layoutManager = LinearLayoutManager(
                mFrogoAdmobRecyclerView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            if (optionDividerItem) {
                mFrogoAdmobRecyclerView.addItemDecoration(
                    DividerItemDecoration(
                        mFrogoAdmobRecyclerView.context,
                        LinearLayoutManager.HORIZONTAL
                    )
                )
            }
        } else if (optionLayoutManager.equals(FrogoRvConstant.LAYOUT_STAGGERED_GRID)) {
            mFrogoAdmobRecyclerView.layoutManager =
                StaggeredGridLayoutManager(layoutSpanCount, StaggeredGridLayoutManager.VERTICAL)
        } else if (optionLayoutManager.equals(FrogoRvConstant.LAYOUT_GRID)) {
            mFrogoAdmobRecyclerView.layoutManager =
                GridLayoutManager(mFrogoAdmobRecyclerView.context, layoutSpanCount)
        }

    }

    private fun createAdapter() {
        optionAdapter = FrogoRvConstant.FROGO_ADAPTER_VIEW_BINDING
        frogoViewAdapterBinding =
            FrogoViewAdapterBinding(object :
                FrogoViewHolderBindingCallback<T, V> {
                override fun setupInitComponent(viewBinding: V, data: T) {
                    frogoViewAdapterBindingCallback.setupInitComponent(viewBinding, data)
                }
            })

        frogoViewAdapterBinding.setupRequirement(customViewBinding, listData,
            object :
                FrogoRecyclerViewListener<T> {
                override fun onItemClicked(data: T) {
                    frogoViewAdapterBindingCallback.onItemClicked(data)
                }

                override fun onItemLongClicked(data: T) {
                    frogoViewAdapterBindingCallback.onItemLongClicked(data)
                }
            })

    }

    private fun <T> setupInnerAdapter() {
        Log.d("injector-optionAdapter", optionAdapter)
        mFrogoAdmobRecyclerView.adapter = frogoViewAdapterBinding
    }

    override fun build(): FrogoRvSingletonViewBinding<T, V> {
        createAdapter()
        setupLayoutManager()
        setupInnerAdapter<T>()
        return this
    }


}