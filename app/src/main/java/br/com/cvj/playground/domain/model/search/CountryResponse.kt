package br.com.cvj.playground.domain.model.search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
class CountryResponse : ArrayList<CountryResponse.SearchCountryResponseItem>(), Serializable{
    @JsonClass(generateAdapter = true)
    data class SearchCountryResponseItem(
        @Json(name = "altSpellings")
        val altSpellings: List<String> = listOf(),
        @Json(name = "area")
        val area: Double = 0.0,
        @Json(name = "borders")
        val borders: List<String>? = listOf(),
        @Json(name = "capital")
        val capital: List<String> = listOf(),
        @Json(name = "capitalInfo")
        val capitalInfo: SearchCapitalInfo = SearchCapitalInfo(),
        @Json(name = "car")
        val car: SearchCar = SearchCar(),
        @Json(name = "cca2")
        val cca2: String = "",
        @Json(name = "cca3")
        val cca3: String = "",
        @Json(name = "ccn3")
        val ccn3: String = "",
        @Json(name = "cioc")
        val cioc: String? = "",
        @Json(name = "coatOfArms")
        val coatOfArms: SearchCoatOfArms? = SearchCoatOfArms(),
        @Json(name = "continents")
        val continents: List<String> = listOf(),
        @Json(name = "currencies")
        val currencies: SearchCurrencies = SearchCurrencies(),
        @Json(name = "demonyms")
        val demonyms: SearchDemonyms = SearchDemonyms(),
        @Json(name = "fifa")
        val fifa: String? = "",
        @Json(name = "flag")
        val flag: String = "",
        @Json(name = "flags")
        val flags: SearchFlags = SearchFlags(),
        @Json(name = "gini")
        val gini: SearchGini? = SearchGini(),
        @Json(name = "idd")
        val idd: SearchIdd = SearchIdd(),
        @Json(name = "independent")
        val independent: Boolean = false,
        @Json(name = "landlocked")
        val landlocked: Boolean = false,
        @Json(name = "languages")
        val languages: SearchLanguages = SearchLanguages(),
        @Json(name = "latlng")
        val latlng: List<Double> = listOf(),
        @Json(name = "maps")
        val maps: SearchMaps = SearchMaps(),
        @Json(name = "name")
        val name: SearchName = SearchName(),
        @Json(name = "population")
        val population: Int = 0,
        @Json(name = "postalCode")
        val postalCode: SearchPostalCode? = SearchPostalCode(),
        @Json(name = "region")
        val region: String = "",
        @Json(name = "startOfWeek")
        val startOfWeek: String = "",
        @Json(name = "status")
        val status: String = "",
        @Json(name = "subregion")
        val subregion: String = "",
        @Json(name = "timezones")
        val timezones: List<String> = listOf(),
        @Json(name = "tld")
        val tld: List<String> = listOf(),
        @Json(name = "translations")
        val translations: SearchTranslations = SearchTranslations(),
        @Json(name = "unMember")
        val unMember: Boolean = false
    ): Serializable {
        @JsonClass(generateAdapter = true)
        data class SearchCapitalInfo(
            @Json(name = "latlng")
            val latlng: List<Double> = listOf()
        ): Serializable
    
        @JsonClass(generateAdapter = true)
        data class SearchCar(
            @Json(name = "side")
            val side: String = "",
            @Json(name = "signs")
            val signs: List<String> = listOf()
        ): Serializable
    
        @JsonClass(generateAdapter = true)
        data class SearchCoatOfArms(
            @Json(name = "png")
            val png: String? = "",
            @Json(name = "svg")
            val svg: String? = ""
        ): Serializable
    
        @JsonClass(generateAdapter = true)
        data class SearchCurrencies(
            @Json(name = "BND")
            val bND: SearchBND? = SearchBND(),
            @Json(name = "BRL")
            val bRL: SearchBRL? = SearchBRL(),
            @Json(name = "EUR")
            val eUR: SearchEUR? = SearchEUR(),
            @Json(name = "GBP")
            val gBP: SearchGBP? = SearchGBP(),
            @Json(name = "GIP")
            val gIP: SearchGIP? = SearchGIP(),
            @Json(name = "SGD")
            val sGD: SearchSGD? = SearchSGD(),
            @Json(name = "USD")
            val uSD: SearchUSD? = SearchUSD(),
            @Json(name = "XAF")
            val xAF: SearchXAF? = SearchXAF()
        ): Serializable {
            @JsonClass(generateAdapter = true)
            data class SearchBND(
                @Json(name = "name")
                val name: String = "",
                @Json(name = "symbol")
                val symbol: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchBRL(
                @Json(name = "name")
                val name: String = "",
                @Json(name = "symbol")
                val symbol: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchEUR(
                @Json(name = "name")
                val name: String = "",
                @Json(name = "symbol")
                val symbol: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchGBP(
                @Json(name = "name")
                val name: String = "",
                @Json(name = "symbol")
                val symbol: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchGIP(
                @Json(name = "name")
                val name: String = "",
                @Json(name = "symbol")
                val symbol: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchSGD(
                @Json(name = "name")
                val name: String = "",
                @Json(name = "symbol")
                val symbol: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchUSD(
                @Json(name = "name")
                val name: String = "",
                @Json(name = "symbol")
                val symbol: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchXAF(
                @Json(name = "name")
                val name: String = "",
                @Json(name = "symbol")
                val symbol: String = ""
            ): Serializable
        }
    
        @JsonClass(generateAdapter = true)
        data class SearchDemonyms(
            @Json(name = "eng")
            val eng: SearchEng = SearchEng(),
            @Json(name = "fra")
            val fra: SearchFra? = SearchFra()
        ): Serializable {
            @JsonClass(generateAdapter = true)
            data class SearchEng(
                @Json(name = "f")
                val f: String = "",
                @Json(name = "m")
                val m: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchFra(
                @Json(name = "f")
                val f: String = "",
                @Json(name = "m")
                val m: String = ""
            ): Serializable
        }
    
        @JsonClass(generateAdapter = true)
        data class SearchFlags(
            @Json(name = "alt")
            val alt: String? = "",
            @Json(name = "png")
            val png: String = "",
            @Json(name = "svg")
            val svg: String = ""
        ): Serializable
    
        @JsonClass(generateAdapter = true)
        data class SearchGini(
            @Json(name = "2011")
            val x2011: Double? = 0.0,
            @Json(name = "2017")
            val x2017: Double? = 0.0,
            @Json(name = "2018")
            val x2018: Double? = 0.0,
            @Json(name = "2019")
            val x2019: Double? = 0.0
        ): Serializable
    
        @JsonClass(generateAdapter = true)
        data class SearchIdd(
            @Json(name = "root")
            val root: String = "",
            @Json(name = "suffixes")
            val suffixes: List<String> = listOf()
        ): Serializable
    
        @JsonClass(generateAdapter = true)
        data class SearchLanguages(
            @Json(name = "ell")
            val ell: String? = "",
            @Json(name = "eng")
            val eng: String? = "",
            @Json(name = "fra")
            val fra: String? = "",
            @Json(name = "kon")
            val kon: String? = "",
            @Json(name = "lin")
            val lin: String? = "",
            @Json(name = "msa")
            val msa: String? = "",
            @Json(name = "por")
            val por: String? = "",
            @Json(name = "spa")
            val spa: String? = "",
            @Json(name = "tur")
            val tur: String? = ""
        ): Serializable
    
        @JsonClass(generateAdapter = true)
        data class SearchMaps(
            @Json(name = "googleMaps")
            val googleMaps: String = "",
            @Json(name = "openStreetMaps")
            val openStreetMaps: String = ""
        ): Serializable
    
        @JsonClass(generateAdapter = true)
        data class SearchName(
            @Json(name = "common")
            val common: String = "",
            @Json(name = "nativeName")
            val nativeName: SearchNativeName = SearchNativeName(),
            @Json(name = "official")
            val official: String = ""
        ) : Serializable{
            @JsonClass(generateAdapter = true)
            data class SearchNativeName(
                @Json(name = "ell")
                val ell: SearchEll? = SearchEll(),
                @Json(name = "eng")
                val eng: SearchEng? = SearchEng(),
                @Json(name = "fra")
                val fra: SearchFra? = SearchFra(),
                @Json(name = "kon")
                val kon: SearchKon? = SearchKon(),
                @Json(name = "lin")
                val lin: SearchLin? = SearchLin(),
                @Json(name = "msa")
                val msa: SearchMsa? = SearchMsa(),
                @Json(name = "por")
                val por: SearchPor? = SearchPor(),
                @Json(name = "spa")
                val spa: SearchSpa? = SearchSpa(),
                @Json(name = "tur")
                val tur: SearchTur? = SearchTur()
            ): Serializable {
                @JsonClass(generateAdapter = true)
                data class SearchEll(
                    @Json(name = "common")
                    val common: String = "",
                    @Json(name = "official")
                    val official: String = ""
                ): Serializable
    
                @JsonClass(generateAdapter = true)
                data class SearchEng(
                    @Json(name = "common")
                    val common: String = "",
                    @Json(name = "official")
                    val official: String = ""
                ): Serializable
    
                @JsonClass(generateAdapter = true)
                data class SearchFra(
                    @Json(name = "common")
                    val common: String = "",
                    @Json(name = "official")
                    val official: String = ""
                ): Serializable
    
                @JsonClass(generateAdapter = true)
                data class SearchKon(
                    @Json(name = "common")
                    val common: String = "",
                    @Json(name = "official")
                    val official: String = ""
                ): Serializable
    
                @JsonClass(generateAdapter = true)
                data class SearchLin(
                    @Json(name = "common")
                    val common: String = "",
                    @Json(name = "official")
                    val official: String = ""
                ): Serializable
    
                @JsonClass(generateAdapter = true)
                data class SearchMsa(
                    @Json(name = "common")
                    val common: String = "",
                    @Json(name = "official")
                    val official: String = ""
                ): Serializable
    
                @JsonClass(generateAdapter = true)
                data class SearchPor(
                    @Json(name = "common")
                    val common: String = "",
                    @Json(name = "official")
                    val official: String = ""
                ): Serializable
    
                @JsonClass(generateAdapter = true)
                data class SearchSpa(
                    @Json(name = "common")
                    val common: String = "",
                    @Json(name = "official")
                    val official: String = ""
                ): Serializable
    
                @JsonClass(generateAdapter = true)
                data class SearchTur(
                    @Json(name = "common")
                    val common: String = "",
                    @Json(name = "official")
                    val official: String = ""
                ): Serializable
            }
        }
    
        @JsonClass(generateAdapter = true)
        data class SearchPostalCode(
            @Json(name = "format")
            val format: String = "",
            @Json(name = "regex")
            val regex: String = ""
        ): Serializable
    
        @JsonClass(generateAdapter = true)
        data class SearchTranslations(
            @Json(name = "ara")
            val ara: SearchAra = SearchAra(),
            @Json(name = "bre")
            val bre: SearchBre = SearchBre(),
            @Json(name = "ces")
            val ces: SearchCes = SearchCes(),
            @Json(name = "cym")
            val cym: SearchCym = SearchCym(),
            @Json(name = "deu")
            val deu: SearchDeu = SearchDeu(),
            @Json(name = "est")
            val est: SearchEst = SearchEst(),
            @Json(name = "fin")
            val fin: SearchFin = SearchFin(),
            @Json(name = "fra")
            val fra: SearchFra = SearchFra(),
            @Json(name = "hrv")
            val hrv: SearchHrv = SearchHrv(),
            @Json(name = "hun")
            val hun: SearchHun = SearchHun(),
            @Json(name = "ita")
            val ita: SearchIta = SearchIta(),
            @Json(name = "jpn")
            val jpn: SearchJpn = SearchJpn(),
            @Json(name = "kor")
            val kor: SearchKor = SearchKor(),
            @Json(name = "nld")
            val nld: SearchNld = SearchNld(),
            @Json(name = "per")
            val per: SearchPer = SearchPer(),
            @Json(name = "pol")
            val pol: SearchPol = SearchPol(),
            @Json(name = "por")
            val por: SearchPor = SearchPor(),
            @Json(name = "rus")
            val rus: SearchRus = SearchRus(),
            @Json(name = "slk")
            val slk: SearchSlk = SearchSlk(),
            @Json(name = "spa")
            val spa: SearchSpa = SearchSpa(),
            @Json(name = "srp")
            val srp: SearchSrp = SearchSrp(),
            @Json(name = "swe")
            val swe: SearchSwe = SearchSwe(),
            @Json(name = "tur")
            val tur: SearchTur = SearchTur(),
            @Json(name = "urd")
            val urd: SearchUrd = SearchUrd(),
            @Json(name = "zho")
            val zho: SearchZho = SearchZho()
        ) : Serializable {
            @JsonClass(generateAdapter = true)
            data class SearchAra(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            )
    
            @JsonClass(generateAdapter = true)
            data class SearchBre(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchCes(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchCym(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchDeu(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchEst(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchFin(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchFra(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchHrv(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchHun(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchIta(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchJpn(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchKor(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchNld(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchPer(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchPol(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchPor(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchRus(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchSlk(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchSpa(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchSrp(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchSwe(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchTur(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchUrd(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
    
            @JsonClass(generateAdapter = true)
            data class SearchZho(
                @Json(name = "common")
                val common: String = "",
                @Json(name = "official")
                val official: String = ""
            ): Serializable
        }
    }
}