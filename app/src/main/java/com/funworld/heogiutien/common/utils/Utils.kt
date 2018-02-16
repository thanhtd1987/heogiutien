package com.funworld.heogiutien.common.utils

import java.text.DecimalFormat

/**
 * Created by ThanhTD on 2/16/2018.
 */
class Utils {

    companion object {

        fun asMoneyAmount(amount: Int): String{
            val formatter = DecimalFormat("#,###,###")
            return formatter.format(amount)+ "k"
        }
    }
}