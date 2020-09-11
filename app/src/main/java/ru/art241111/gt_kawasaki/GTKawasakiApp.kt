package ru.art241111.gt_kawasaki

import android.app.Application

class GTKawasakiApp: Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
        lateinit var instance: GTKawasakiApp
            private set
    }
}