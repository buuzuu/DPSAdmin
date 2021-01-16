package com.example.dpsadmin

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dpsadmin.`interface`.NotificationService
import com.example.dpsadmin.adapter.BillAdapter
import com.example.dpsadmin.adapter.MsgAdapter
import com.example.dpsadmin.model.FBToken
import com.example.dpsadmin.model.Message
import com.example.dpsadmin.model.NotificationBody
import com.example.dpsadmin.model.Student
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.robertlevonyan.views.chip.OnSelectClickListener
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_notify_student.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class NotifyStudent : AppCompatActivity(), OnSelectClickListener {

    private lateinit var toolbar: Toolbar
    lateinit var myWindow: Window
    var pg: Boolean = false
    var ln: Boolean = false
    var un: Boolean = false
    var one: Boolean = false
    var two: Boolean = false
    var three: Boolean = false
    var four: Boolean = false
    var five: Boolean = false
    var six: Boolean = false
    val db = Firebase.firestore

    var seven: Boolean = false
    var eight: Boolean = false
    private lateinit var dialog: android.app.AlertDialog
    private var adapter: MsgAdapter? = null
    lateinit var service: NotificationService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify_student)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        rv.layoutManager = LinearLayoutManager(this)
        setChipListners()
        service = Common.generalRetrofit.create(NotificationService::class.java)
        myWindow = this.window
        dialog =
            SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).setMessage("Sending ...")
                .setCancelable(false).build()
        myWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        myWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        myWindow.statusBarColor = getColor(R.color.salary)
        toolbar.setBackgroundColor(getColor(R.color.salary))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val ref = db.collection("general_msg")
        val query: Query = ref.orderBy("timestamp", Query.Direction.DESCENDING).limit(20)
        val options = FirestoreRecyclerOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .build()
        adapter = MsgAdapter(options)
        adapter!!.setHasStableIds(true)
        rv.adapter = adapter
        adapter!!.notifyDataSetChanged()
        send.setOnClickListener {

            if (pg)
                sendNotification("class_playGroup")
            if (ln)
                sendNotification("class_lowerNursery")
            if (un)
                sendNotification("class_upperNursery")
            if (one)
                sendNotification("class_one")
            if (two)
                sendNotification("class_two")
            if (three)
                sendNotification("class_three")
            if (four)
                sendNotification("class_four")
            if (five)
                sendNotification("class_five")
            if (six)
                sendNotification("class_six")
            if (seven)
                sendNotification("class_seven")
            if (eight)
                sendNotification("class_eight")

            val tsLong = System.currentTimeMillis() / 1000
            val currentDate =
                Common.getDay(Calendar.getInstance()) + "-" + Common.getMonth(Calendar.getInstance()) + "-" + Common.getYear(
                    Calendar.getInstance()
                )
            db.collection("general_msg").document(tsLong.toString()).set(Message(tsLong.toString(), currentDate,
                msg.editText?.text.toString().trim()))

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

    fun setChipListners() {
        chip0.onSelectClickListener = this
        chip1.onSelectClickListener = this
        chip2.onSelectClickListener = this
        chip3.onSelectClickListener = this
        chip4.onSelectClickListener = this
        chip5.onSelectClickListener = this
        chip6.onSelectClickListener = this
        chip7.onSelectClickListener = this
        chip8.onSelectClickListener = this
        chip9.onSelectClickListener = this
        chip10.onSelectClickListener = this

    }

    override fun onSelectClick(v: View?, selected: Boolean) {

        when (v?.id) {
            R.id.chip0 ->
                pg = selected
            R.id.chip1 ->
                ln = selected
            R.id.chip2 ->
                un = selected
            R.id.chip3 ->
                one = selected
            R.id.chip4 ->
                two = selected
            R.id.chip5 ->
                three = selected
            R.id.chip6 ->
                four = selected
            R.id.chip7 ->
                five = selected
            R.id.chip8 ->
                six = selected
            R.id.chip9 ->
                seven = selected
            R.id.chip10 ->
                eight = selected
        }


    }


    fun sendNotification(className: String) {
        val tokenList: ArrayList<String> = ArrayList()
        db.collection("notification_token").document(className).collection("tokens").get()
            .addOnSuccessListener {
                var obj: FBToken
                for (document in it) {
                    obj = document.toObject(FBToken::class.java)
                    tokenList.add(obj.firebaseToken)
                }
                val data = NotificationBody(tokenList,
                    "Divine Public School",
                    getString(R.string.serverKey),
                    msg.editText?.text.toString().trim())

                Log.d("TAG", tokenList.size.toString())
                val call = service.sendNotification(data)
                Log.d("TAG", data.toString())

                call.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("TAG", t.message)
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.d("TAG", response.code().toString())


                        when (response.code()) {
                            200 -> {
                                saveMessageToDB(className, msg.editText?.text.toString().trim())
                            }
                        }
                    }

                })
            }
    }

    private fun saveMessageToDB(className: String, msges: String) {

        val currentDate =
            Common.getDay(Calendar.getInstance()) + "-" + Common.getMonth(Calendar.getInstance()) + "-" + Common.getYear(
                Calendar.getInstance()
            )
        val tsLong = System.currentTimeMillis() / 1000
        db.collection("notification_msg").document(className).collection("messages")
            .document(tsLong.toString()).set(Message(tsLong.toString(), currentDate, msges))


    }
    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }
}
