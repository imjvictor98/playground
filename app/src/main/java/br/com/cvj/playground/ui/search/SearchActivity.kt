package br.com.cvj.playground.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import br.com.cvj.playground.R
import br.com.cvj.playground.data.repository.search.SearchCountriesDataSource
import br.com.cvj.playground.data.repository.search.SearchCountriesRaw
import br.com.cvj.playground.data.repository.search.SearchCountriesRepository
import br.com.cvj.playground.databinding.ActivitySearchBinding
import br.com.cvj.playground.domain.model.search.SearchCityItem
import br.com.cvj.playground.ui.base.BaseActivity
import br.com.cvj.playground.ui.main.MainActivity
import br.com.cvj.playground.util.extension.applyStyles
import br.com.cvj.playground.util.extension.gone
import br.com.cvj.playground.util.extension.hideKeyboard
import br.com.cvj.playground.util.extension.visible

class SearchActivity : BaseActivity<ISearchContract.Presenter, ActivitySearchBinding>(),
    ISearchContract.View {
    private val mSearchSpinnerAdapter by lazy {
        mBinding.activitySearchSpinner.adapter as? SearchSpinnerAdapter
    }
    private var mSelectedIndex = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding(ActivitySearchBinding.inflate(layoutInflater))
        setContentView(mBinding.root)
        setPresenter(
            SearchPresenter(
                this,
                SearchCountriesRepository(SearchCountriesDataSource(SearchCountriesRaw(resources)))
            )
        )

        setupSearchView()
        setupSpinner()
    }

    private fun setupSearchView() {
        mBinding.activitySearchView.run {
            applyStyles()
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return if (query.isNullOrEmpty()) {
                        false
                    } else {
                        mPresenter?.findCityByName(query)
                        true
                    }
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) return true
                    return if (newText.equals(query.toString(), true)) {
                        true
                    } else {
                        mPresenter?.findCityByName(newText)
                        false
                    }
                }
            })
        }
    }

    private fun setupSpinner() {
        mBinding.activitySearchSpinner.apply {
            adapter = SearchSpinnerAdapter(
                mContext,
                R.layout.spinner_item_city,
                R.id.spinner_item_city_text
            )
            onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (mSelectedIndex != -1) {
                        setResult(MainActivity.EXTRA_CITY_ITEM_RESULT_CODE, Intent().apply {
                            putExtra(
                                MainActivity.EXTRA_CITY_ITEM,
                                mSearchSpinnerAdapter?.getItem(position)
                            )
                        })
                        finish()
                    }
                    mSelectedIndex = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    override fun showResults(cities: List<SearchCityItem>) {
        mSearchSpinnerAdapter?.apply {
            clear()
            addAll(cities)
            notifyDataSetChanged()
        }

        hideKeyboard()
    }

    override fun clearResults() {
        mSearchSpinnerAdapter?.clear()
    }

    override fun showSpinner() {
        mBinding.activitySearchSpinner.visible()
    }

    override fun hideSpinner() {
        mBinding.activitySearchSpinner.gone()
    }

    override fun beforeDestroyView() {}
}