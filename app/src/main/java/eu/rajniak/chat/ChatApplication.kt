package eu.rajniak.chat

import android.app.Application

class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ChatServiceLocator.initialize(this)
    }
}
