package io.github.xnovo3000.openweather.model

import androidx.annotation.StringRes
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

        private fun fromString(value: String): QueryLanguage? {
            return when (value) {
                "en" -> ENGLISH
                "de" -> GERMAN
                "fr" -> FRENCH
                "es" -> SPANISH
                "it" -> ITALIAN
                "pt" -> PORTUGUESE
                "ru" -> RUSSIAN
                "tr" -> TURKISH
                "hi" -> HINDI
                else -> null
            }
        }

        fun fromStringOrDefault(value: String): QueryLanguage {
            return fromString(value) ?: ENGLISH
        }

    }

}