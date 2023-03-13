package br.com.cvj.playground.ui.permission.location

import br.com.cvj.playground.ui.IBaseContract

interface IPermissionLocationContract {
    interface View: IBaseContract.BaseView { //Isso aqui não é a view, a abstração do que acontece na interação. Por isso é uma camada
        fun afterSkipShowHome()
        fun requestLocationPermission()
        fun afterAllowPermissionsShowHome()
    }

    interface Presenter: IBaseContract.BasePresenter<View> {
        fun handleSkipButton()
        fun handleLocationButton()
        fun handleOnLocationAllowed()
    }
}