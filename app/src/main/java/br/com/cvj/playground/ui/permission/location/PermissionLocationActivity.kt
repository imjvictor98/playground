package br.com.cvj.playground.ui.permission.location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import br.com.cvj.playground.util.helper.PermissionHelper
import br.com.cvj.playground.databinding.ActivityPermissionLocationBinding
import br.com.cvj.playground.ui.base.BaseActivity

class PermissionLocationActivity :
    BaseActivity<PermissionLocationPresenter, ActivityPermissionLocationBinding>(),
    IPermissionLocationContract.View {
    companion object {
        fun start(context: Context) {
            Intent(context, PermissionLocationActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding(ActivityPermissionLocationBinding.inflate(layoutInflater))
        setContentView(mBinding?.root)
        setPresenter(PermissionLocationPresenter(this))

        mBinding?.activityPermissionLocationSkip?.setOnClickListener {
            mPresenter?.handleSkipButton()
        }

        mBinding?.activityPermissionLocationRequest?.setOnClickListener {
            mPresenter?.handleLocationButton()
        }

        mBinding?.activityPermissionLocationRequest?.performClick()
    }

    override fun beforeDestroyView() {

    }

    override fun afterSkipShowHome() {
//        MainActivity.start(this)
        finish()
    }

    override fun requestLocationPermission() {
        if (!PermissionHelper.hasLocationPermissions(mContext)) {
            PermissionHelper.requestLocationPermission(this)
        } else {
            mPresenter?.handleOnLocationAllowed()
        }
    }

    override fun afterAllowPermissionsShowHome() {
//        MainActivity.start(mContext)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermissionHelper.REQUEST_CODE_LOCATION_PERMISSIONS -> {
                if (PermissionHelper.checkLocationPermissionsAllowed(permissions, grantResults)) {
                    mPresenter?.handleOnLocationAllowed()
                }
                return
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}