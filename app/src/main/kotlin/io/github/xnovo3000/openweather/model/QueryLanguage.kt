package io.github.xnovo3000.openweather.model

import androidx.annotation.StringRes
import com.ibm.icu.util.ULocale
import io.github.xnovo3000.openweather.R

enum class QueryLanguage(@StringRes val stringRes: Int, val queryName: String) {

    ENGLISH(R.string.query_language_en, "en"),
    GERMAN(R.string.query_language_de, "de"),
    FRENCH(R.string.query_language_fr, "fr"),
    SPANISH(R.string.query_language_es, "es"),
    ITALIAN(R.string.query_language_it, "it"),
    PORTUGUESE(R.string.query_language_pt, "pt"),
    RUSSIAN(R.string.query_language_ru, "ru"),
    TURKISH(R.string.query_language_tr, "tr"),
    HINDI(R.string.query_language_hi, "hi");

    companion object {

        fun default(): QueryLanguage {
            var current: ULocale? = ULocale.getDefault()
            while (current != null) {
                val language = QueryLanguage.values().firstOrNull {
                    current!!.isO3Language.contains(it.queryName)
                }
                if (language != null) {
                    return language
                }
                current = current.fallback
            }
            return ENGLISH
        }

    }

}