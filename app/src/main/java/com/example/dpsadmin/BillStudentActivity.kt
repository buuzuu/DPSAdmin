package com.example.dpsadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dpsadmin.`interface`.OnBillStudentClickListner
import com.example.dpsadmin.adapter.BillAdapter
import com.example.dpsadmin.model.Student
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_bill_student.*

class BillStudentActivity : AppCompatActivity(), OnBillStudentClickListner {

    val db = Firebase.firestore
    lateinit var myWindow: Window
    private lateinit var toolbar: Toolbar
    private var adapter: BillAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_student)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        myWindow = this.window
        myWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        myWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        myWindow.statusBarColor = getColor(R.color.bill)
        toolbar.setBackgroundColor(getColor(R.color.bill))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val ref = db.collection(intent.getStringExtra("collection"))
        var query: Query = ref.orderBy("rollNumber", Query.Direction.ASCENDING)
        var options = FirestoreRecyclerOptions.Builder<Student>()
            .setQuery(query, Student::class.java)
            .build()
        adapter = BillAdapter(options,this)
        bill_rv.layoutManager = LinearLayoutManager(this)
        bill_rv.setHasFixedSize(true)
        bill_rv.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
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

    override fun onStudentForBillClicked(enrollNo: String) {
        var intent = Intent(this,GenerateBill::class.java)
        intent.putExtra("enrollNumber", enrollNo )
        intent.putExtra("collection", intent.getStringExtra("collection") )
        startActivity(intent)
    }
}
