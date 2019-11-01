package com.devyk.kotlin_github.config

import android.app.Activity
import android.support.annotation.StyleRes
import com.devyk.kotlin_github.R

object Themer {
    enum class ThemeMode(@StyleRes val normal: Int, @StyleRes val translucent: Int){
        DAY(R.style.AppTheme, R.style.AppTheme_Translucent), NIGHT(R.style.AppTheme_Dark, R.style.AppTheme_Dark_Translucent)
    }

    fun applyProperTheme(activity: Activity, translucent: Boolean = false){
        activity.setTheme(currentTheme().let { if(translucent) it.translucent else it.normal })
    }

    fun currentTheme() = ThemeMode.valueOf(Settings.themeMode)

    fun toggle(activity: Activity){
        when(currentTheme()){
            Themer.ThemeMode.DAY -> Settings.themeMode = ThemeMode.NIGHT.name
            Themer.ThemeMode.NIGHT -> Settings.themeMode = ThemeMode.DAY.name
        }
        activity.recreate()
    }
}