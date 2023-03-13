package br.com.cvj.playground.ui

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T, B>: AppCompatActivity(), IBaseContract.BaseView {
    private var _presenter: T? = null
    val mPresenter get() = _presenter

    private var _binding: B? = null
    val mBinding get() = _binding

    fun setPresenter(newPresenter: T) {
        _presenter = newPresenter
    }

    fun setBinding(newBinding: B) {
        _binding = newBinding
    }

    override fun onDestroy() {
        beforeDestroyView()
        (_presenter as IBaseContract.BasePresenter<*>).beforeDestroyPresenter()
        super.onDestroy()
    }
}