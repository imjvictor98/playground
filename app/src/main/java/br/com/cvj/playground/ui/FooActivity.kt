package br.com.cvj.playground.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.com.cvj.playground.R
import br.com.cvj.playground.data.repository.search.SearchCountriesDataSource
import br.com.cvj.playground.data.repository.search.SearchCountriesRaw
import br.com.cvj.playground.data.repository.search.SearchCountriesRepository
import kotlin.concurrent.thread

class FooActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var repository: SearchCountriesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foo)

        repository = SearchCountriesRepository(
            SearchCountriesDataSource(SearchCountriesRaw(resources))
        )

        textView = findViewById(R.id.textView)

        callbackWay()
    }

    //Cada aplicação android roda em cima da main thread
    //Esta que nunca deve ser bloqueada por motivo algum


    private fun firstWay() {
//        thread {
//            val weather = repository.fetchCountriesBy("Taguatinga")
//            val weatherFirst = weather?.first()
//            runOnUiThread {
//                textView.text = weatherFirst?.name
//            }
//        }
    }

    private fun callbackWay() {
        getNewsFromApi { news ->
            var sortedNews = ""
            news.sortedByDescending{ it }.forEach { sortedNews += "\n$it" }
            runOnUiThread {
                textView.text = sortedNews
            }
        }
    }

    private fun getNewsFromApi(callback: (news: List<String>) -> Unit) {
        thread {
            val listOfStrings = listOf(
                "Bee",
                "Bug",
                "Butterfly"
            )
            Thread.sleep(3000)
            callback(listOfStrings)
        }
    }
}