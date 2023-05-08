package br.com.cvj.playground.ui.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment<T, B>: IBaseContract.BaseView, Fragment {
    constructor(layout: Int) : this() {
        Fragment(layout)
    }

    constructor() {
        Fragment()
    }

    private var _presenter: T? = null
    val mPresenter get() = _presenter

    private var _binding: B? = null
    val mBinding get() = _binding

    private lateinit var _context: Context
    val mContext get() = _context

    fun setPresenter(newPresenter: T) {
        _presenter = newPresenter
    }

    fun setBinding(newBinding: B) {
        _binding = newBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _context = requireContext()
    }

    override fun onDestroy() {
        beforeDestroyView()
        (_presenter as IBaseContract.BasePresenter<*>).beforeDestroyPresenter()
        super.onDestroy()
    }
}