package com.elevenetc.android.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elevenetc.android.news.features.list.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.root, ListFragment())
                .commit()
        }
    }
}