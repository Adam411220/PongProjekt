package com.example.pong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MenuActivity : AppCompatActivity() {

    private lateinit var db : AppDatabase
    private lateinit var userDao : UserDao

    private val dataSet = arrayListOf<Score>()
    private val adapter = ScoreAdapter(dataSet)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        recyclerSetUp()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "scoresPong").allowMainThreadQueries().build()
        userDao = db.userDao()
        val pastScores = userDao.getAll()
        dataSet.addAll(pastScores.reversed())
        adapter.notifyDataSetChanged()

    }

    private fun recyclerSetUp() {
        val recyclerView : RecyclerView = findViewById(R.id.pastScores)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (data != null) {
                var score = data.getIntExtra("SCORE", -1)
                if (score != -1) {
                    userDao.insertAll(Score(0, score))
                    dataSet.add(0, Score(0, score))
                    adapter.notifyItemInserted(0)
                    findViewById<RecyclerView>(R.id.pastScores).layoutManager?.scrollToPosition(0)
                }
            }
        }
    }

    fun clickPlayButton(view: View) {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivityForResult(myIntent, 100)
    }
}