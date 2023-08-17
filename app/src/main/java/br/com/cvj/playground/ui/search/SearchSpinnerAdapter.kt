package br.com.cvj.playground.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import br.com.cvj.playground.databinding.SpinnerItemCityBinding
import br.com.cvj.playground.domain.model.search.SearchCityItem

//ArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull T[] objects)
//    public ArrayAdapter(@NonNull Context context, @LayoutRes int resource,
//            @IdRes int textViewResourceId, @NonNull List<T> objects
class SearchSpinnerAdapter(
    context: Context,
    @LayoutRes private val layoutRes: Int,
    @IdRes private val textViewId: Int,
    private val mItems: ArrayList<SearchCityItem> = arrayListOf()
) : ArrayAdapter<SearchCityItem>(context, layoutRes, textViewId, mItems) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView: View = convertView ?: SpinnerItemCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).root

        val item: SearchCityItem = mItems[position]

        val itemTextView = itemView.findViewById<TextView>(textViewId)
        itemTextView.text = item.toString()

        return itemView
    }
}