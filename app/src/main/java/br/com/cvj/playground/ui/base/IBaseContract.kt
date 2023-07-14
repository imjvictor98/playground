package br.com.cvj.playground.ui.base

interface IBaseContract {

    interface BaseView {
        fun beforeDestroyView()
    }

    interface BasePresenter<T> {
        val mView: T
        fun beforeDestroyPresenter()
    }
}