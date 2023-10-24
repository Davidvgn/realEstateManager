package com.openclassrooms.realestatemanager.data.utils

import android.content.Context
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

class Utils {

    companion object {
        /**
         * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         *
         * @param dollars
         * @return
         */
        fun convertDollarToEuro(dollars: Int): Int {
            return (dollars * 0.95).roundToInt()
        }

        /**
         * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         *
         * @param dollars
         * @return
         */
        fun convertEuroToDollar(euros: Int): Int {
            return (euros / 0.95).roundToInt()
        }

        /**
         * Conversion de la date d'aujourd'hui en un format plus approprié
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         *
         * @return
         */
        fun getTodayDate(): String { //todo david
//        val dateFormat = SimpleDateFormat.getDateInstance("dd/MM/yyyy")
            val dateFormat = SimpleDateFormat.getDateInstance()
            return dateFormat.format("dd/MM/yyyy")
        }

        /**
         * Vérification de la connexion réseau
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         *
         * @param context
         * @return
         */
        fun isInternetAvailable( context : Context) : Boolean {
            return true //todo david
        }
    }


}