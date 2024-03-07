package com.openclassrooms.realestatemanager.data.utils

import android.content.Context
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
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
            return (dollars / 1.09).roundToInt()
        }

        /**
         * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         *
         * @param dollars
         * @return
         */
        fun convertEuroToDollar(euros: Int): Int {
            return (euros * 1.09).roundToInt()
        }

        /**
         * Conversion de la date d'aujourd'hui en un format plus approprié
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         *
         * @return
         */
        fun getTodayDate(): String =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time)

        /**
         * Vérification de la connexion réseau
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         *
         * @param context
         * @return
         */
        fun isInternetAvailable(context: Context): Boolean {
            return true //todo david
        }

        fun formatDate(dayOfMonth: Int, monthOfYear: Int, year: Int): String =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    set(Calendar.MONTH, monthOfYear)
                    set(Calendar.YEAR, year)
                }.time
            )

         fun formatPriceForUI(price: Int): String {
            val decimalFormat = NumberFormat.getInstance() as DecimalFormat
            return decimalFormat.format(price)
        }

         fun formatPriceWithSpace(price: Int): String {
            val decimalFormatSymbols = DecimalFormatSymbols.getInstance().apply {
                groupingSeparator = ' '
            }
            val decimalFormat = DecimalFormat("#,###", decimalFormatSymbols)
            return decimalFormat.format(price)
        }
    }
}
