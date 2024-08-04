package bakuen.qwear

import android.app.Application
import android.content.Context

lateinit var appContext: Context
    private set
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}