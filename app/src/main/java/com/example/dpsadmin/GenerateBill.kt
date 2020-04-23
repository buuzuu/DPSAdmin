package com.example.dpsadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dpsadmin.adapter.ClassBookGridAdapter
import com.example.dpsadmin.model.Book
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_generate_bill.*
import java.util.ArrayList

class GenerateBill : AppCompatActivity() {

    val db = Firebase.firestore
    lateinit var myWindow: Window
    private var path: String = ""
    private lateinit var toolbar: Toolbar
    private lateinit var book: Book
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_bill)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        myWindow = this.window
        // path = intent.getStringExtra("collection")
        myWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        myWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        myWindow.statusBarColor = getColor(R.color.bill)
        toolbar.setBackgroundColor(getColor(R.color.bill))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        book_rv.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)


        db.collection("fees").document("books").get()
            .addOnSuccessListener {
                var bookList = ArrayList<Book>()
                for ((key, value) in it.get("class_one") as HashMap<*, *>) {
                    book = Book(key.toString(), value.toString().toInt())
                    bookList.add(book)
                }
                book_rv.adapter = ClassBookGridAdapter(bookList)

            }.addOnFailureListener {
            }


        viewBtn.setOnClickListener {
            when (book_rv.visibility) {
                View.VISIBLE -> {
                    book_rv.visibility = View.GONE
                }
                View.GONE -> {
                    book_rv.visibility = View.VISIBLE
                }
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
