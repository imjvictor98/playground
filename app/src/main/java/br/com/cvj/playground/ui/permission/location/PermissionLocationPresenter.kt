package br.com.cvj.playground.ui.permission.location


class PermissionLocationPresenter(
    override val mView: IPermissionLocationContract.View
) : IPermissionLocationContract.Presenter {

    override fun handleSkipButton() {
        mView.afterSkipShowHome()
    }

    override fun handleLocationButton() {
        mView.requestLocationPermission()
    }

    override fun handleOnLocationAllowed() {
        mView.afterAllowPermissionsShowHome()
    }

    override fun beforeDestroyPresenter() {}
}