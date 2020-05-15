package com.example.dpsadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dpsadmin.`interface`.OnBookSelectClickListner
import com.example.dpsadmin.`interface`.OnMonthClickListner
import com.example.dpsadmin.`interface`.OnPlaceClickListner
import com.example.dpsadmin.adapter.ClassBookGridAdapter
import com.example.dpsadmin.adapter.MonthAdapter
import com.example.dpsadmin.adapter.PlaceAdapter
import com.example.dpsadmin.adapter.PreviousBill
import com.example.dpsadmin.model.*
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_generate_bill.*
import kotlinx.android.synthetic.main.pevious_bill.view.*
import java.util.*
import kotlin.collections.HashMap

class GenerateBill : AppCompatActivity(), OnBookSelectClickListner, OnMonthClickListner,
    OnPlaceClickListner {
    var isJuniorTie: Boolean = false
    var isSeniorTie: Boolean = false
    var isBelt: Boolean = false
    var isDiary: Boolean = false
    var isIAndFeeCard: Boolean = false
    var isNewAdmission: Boolean = false
    var admissionFee = 0
    var tuitionFee = 0
    var bookPrice = 0
    var jtiePrice = 0
    var stiePrice = 0
    var diaryPrice = 0
    var beltPrice = 0
    var ifeeCardPrice = 0
    var placePrice = 0
    var annualCharge = 0
    var computerFee = 0
    var examFee = 0
    var total = 0
    val db = Firebase.firestore
    lateinit var myWindow: Window
    lateinit var otherFees: OtherFees
    private lateinit var toolbar: Toolbar
    private lateinit var book: Book
    var intentCollection: String = ""
    var billMonthString: String = ""
    var currentMonth: String = ""
    var currentClassFee: Int = 0
    var billNo: Int = 0
    lateinit var title: TextView
    lateinit var student: Student
    private lateinit var alert: AlertDialog
    private lateinit var verify_password: TextInputLayout
    private lateinit var verify_button: Button
    var dbMonthList = ArrayList<String>()
    var placeList = ArrayList<Place>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_bill)
        toolbar = findViewById(R.id.toolbar)
        title = toolbar.findViewById(R.id.toolbar_title)
        title.text = "₹ ${0}"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        myWindow = this.window
        currentMonth = getCurrentMonth(Common.getMonth(Calendar.getInstance()))
        intentCollection = intent.getStringExtra("collection")
        myWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        myWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        myWindow.statusBarColor = getColor(R.color.bill)
        toolbar.setBackgroundColor(getColor(R.color.bill))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        book_rv.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
        month_rv.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        place_rv.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        getBillNumber()
        //Get current user
        db.collection(intentCollection).document(intent.getStringExtra("enroll")).get()
            .addOnSuccessListener {
                student = it.toObject(Student::class.java)!!
                name.text = "${student.firstName} ${student.lastName}"
                roll.text = student.rollNumber.toString()
                cls.text = getClassName(student)
                isNew.text = if (student.newStudent) "New" else "Old"
                Picasso.get().load(student.image).into(profileImg)
                isTransport.text = if (student.transportStudent) "Yes" else "No"
            }
            .addOnFailureListener { }
        //get other fee
        db.collection("fees").document("otherFees").get()
            .addOnFailureListener { }
            .addOnSuccessListener {
                otherFees = it.toObject(OtherFees::class.java)!!
            }

        //For Month
        db.collection(intentCollection).document(intent.getStringExtra("enroll"))
            .collection("tuitionFee")
            .get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    var doc: QuerySnapshot? = task.result
                    if (doc!!.isEmpty) {

                        //current class tuition fee
                        db.collection("fees").document("tuition").get()
                            .addOnSuccessListener {
                                val a = it.get(decideTutionField(intentCollection))
                                currentClassFee = a.toString().toInt()
                                doesnotExits()
                            }.addOnFailureListener {
                            }

                    } else {
                        // doesExist()
                        loadMonthAdapter()
                    }
                } else {
                    Toast.makeText(this, "Task Result Failed", Toast.LENGTH_SHORT).show()
                }
            }
        // For Book
        db.collection("fees").document("books").get()
            .addOnSuccessListener {
                var bookList = ArrayList<Book>()
                for ((key, value) in it.get(intentCollection) as HashMap<*, *>) {
                    book = Book(key.toString(), value.toString().toInt(), false)
                    bookList.add(book)
                }
                book_rv.adapter = ClassBookGridAdapter(bookList, this)

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
        viewbtn.setOnClickListener {
            when (place_rv.visibility) {
                View.VISIBLE -> {
                    place_rv.visibility = View.GONE
                }
                View.GONE -> {
                    if(dbMonthList.isNotEmpty()){
                        place_rv.visibility = View.VISIBLE

                    }else{
                        Toast.makeText(this,"Select month first",Toast.LENGTH_SHORT).show()
                    }


                }
            }
        }
        // For Transport fee
        db.collection("fees").document("transport").get()
            .addOnSuccessListener {
                var map = it.get("places") as HashMap<*, *>
                for ((key, value) in map) {
                    placeList.add(Place(key.toString(), value.toString().toInt(), false))
                }
                place_rv.adapter = PlaceAdapter(placeList, this)
            }
            .addOnFailureListener { }

        printBill.setOnClickListener {

            if (total != 0 && dbMonthList.isNotEmpty() ) {


                if(student.transportStudent && placePrice == 0){
                    Toast.makeText(this,"Select Place", Toast.LENGTH_SHORT).show()
                }else{
                    verifyPopup()
                }


            }else{
                Toast.makeText(this,"Select Month", Toast.LENGTH_SHORT).show()
            }

        }

    }


    private fun doesnotExits() {
        var i = 1
        var fee: Fee?
        for (elements in Common.monthArray) {
            fee = Fee(false, elements, i, currentClassFee, "")
            db.collection(intentCollection).document(intent.getStringExtra("enroll"))
                .collection("tuitionFee").document(elements)
                .set(fee)
            i++
        }
        loadMonthAdapter()

    }

    private fun loadMonthAdapter() {
        db.collection(intentCollection).document(intent.getStringExtra("enroll"))
            .collection("tuitionFee").orderBy("nom", Query.Direction.ASCENDING).get()
            .addOnSuccessListener {

                var list: ArrayList<BillFee> = ArrayList()
                var fee: Fee
                for (document in it) {
                    fee = document.toObject(Fee::class.java)
                    list.add(BillFee(fee, false))
                }
                month_rv.adapter = MonthAdapter(list, this)
                (month_rv.adapter as MonthAdapter).notifyDataSetChanged()

            }.addOnFailureListener { }
    }




    fun getCurrentMonth(num: String): String {

        if (num == "1") {
            return "January"
        } else if (num == "2") {
            return "February"
        } else if (num == "3") {
            return "March"
        } else if (num == "4") {
            return "April"
        } else if (num == "5") {
            return "May"
        } else if (num == "6") {
            return "June"
        } else if (num == "7") {
            return "July"
        } else if (num == "8") {
            return "August"
        } else if (num == "9") {
            return "September"
        } else if (num == "10") {
            return "October"
        } else if (num == "11") {
            return "November"
        } else if (num == "12") {
            return "December"
        }
        return "Null"
    }

    fun decideTutionField(collection: String): String {

        when (collection) {

            "class_one" -> return "classOne"
            "class_two" -> return "classTwo"
            "class_three" -> return "classThree"
            "class_four" -> return "classFour"
            "class_five" -> return "classFive"
            "class_six" -> return "classSix"
            "class_seven" -> return "classSeven"
            "class_eight" -> return "classEight"
            "class_playGroup" -> return "playGroup"
            "class_upperNursery" -> return "upperNursery"
            "class_lowerNursery" -> return "lowerNursery"
        }
        return ""


    }


    fun onRadioButtonClicked(view: View) {
        if (view is CheckBox) {
            when (view.getId()) {
                R.id.jTie ->
                    if (view.isChecked) {
                        isJuniorTie = true
                        jtiePrice = otherFees.tieFeeJunior
                        computePayablePrice()
                    } else if (!view.isChecked) {
                        isJuniorTie = false
                        jtiePrice = 0
                        computePayablePrice()
                    }
                R.id.sTie ->
                    if (view.isChecked) {
                        isSeniorTie = true
                        stiePrice = otherFees.tieFeeSenior
                        computePayablePrice()

                    } else if (!view.isChecked) {
                        isSeniorTie = false
                        stiePrice = 0
                        computePayablePrice()
                    }
            }
        }
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            when (view.id) {
                R.id.ifeeCard -> {
                    if (view.isChecked) {
                        ifeeCardPrice = otherFees.iandfeeCardPrice
                        isIAndFeeCard = true
                        computePayablePrice()


                    } else if (!view.isChecked) {
                        ifeeCardPrice = 0
                        isIAndFeeCard = false
                        computePayablePrice()

                    }
                }

                R.id.belt -> {
                    if (view.isChecked) {
                        beltPrice = otherFees.beltPrice
                        isBelt = true
                        computePayablePrice()


                    } else if (!view.isChecked) {
                        beltPrice = 0
                        isBelt = false
                        computePayablePrice()

                    }
                }
                R.id.diary -> {
                    if (view.isChecked) {
                        diaryPrice = otherFees.diaryFee
                        isDiary = true
                        computePayablePrice()


                    } else if (!view.isChecked) {
                        diaryPrice = 0
                        isDiary = false
                        computePayablePrice()

                    }
                }
                R.id.admissionFee -> {
                    if (view.isChecked) {
                        admissionFee = otherFees.admissionCharge
                        isNewAdmission = true
                        computePayablePrice()


                    } else if (!view.isChecked) {
                        admissionFee = 0
                        isNewAdmission = false
                        computePayablePrice()

                    }
                }

            }
        }
    }
    override fun onBooksSelected(sumPrice: Int) {
        bookPrice = sumPrice
        price.text = "₹ ${sumPrice}"
        computePayablePrice()
    }


    override fun onPlaceSelected(price: Int) {

        placePrice = if (dbMonthList.isEmpty()) price else price*dbMonthList.size

        transportPrice.text = "₹ ${placePrice}"
            computePayablePrice()
    }


    override fun onMonthsSelected(
        finalAmount: Int,
        monthList: ArrayList<String>
    ) {
        tuitionFee = finalAmount
        tuitionPrice.text = "₹ ${finalAmount}"
        if (monthList.isNotEmpty()) {
            dbMonthList = monthList
            billMonthString = getBillMonthString(monthList)
            examFee = 0
            computerFee = 0
            annualCharge = 0
            examFee = if (monthList.contains("January") || monthList.contains("September")) {
                if (monthList.contains("January") && monthList.contains("September")) {
                    otherFees.examFee * 2
                } else {
                    otherFees.examFee
                }
            } else {
                0
            }

            if (monthList.contains("March") || monthList.contains("June") || monthList.contains("September") || monthList.contains(
                    "December"
                )
            ) {

                if (student.entryClass == "Class Three" || student.entryClass == "Class Four" || student.entryClass == "Class Five") {

                    computerFee =
                        if (monthList.contains("March") && monthList.contains("June") && monthList.contains(
                                "September"
                            ) && monthList.contains("December")
                        )
                            otherFees.computerFeeJunior * 4
                        else if (monthList.contains("March") && monthList.contains("June") && monthList.contains(
                                "September"
                            )
                        )
                            otherFees.computerFeeJunior * 3
                        else if (monthList.contains("March") && monthList.contains("June"))
                            otherFees.computerFeeJunior * 2
                        else
                            otherFees.computerFeeJunior


                }
                else if (student.entryClass == "Class Six" || student.entryClass == "Class Seven" || student.entryClass == "Class Eight") {

                    computerFee =
                        if (monthList.contains("March") && monthList.contains("June") && monthList.contains(
                                "September"
                            ) && monthList.contains("December")
                        )
                            otherFees.computerFeeSenior * 4
                        else if (monthList.contains("March") && monthList.contains("June") && monthList.contains(
                                "September"
                            )
                        )
                            otherFees.computerFeeSenior * 3
                        else if (monthList.contains("March") && monthList.contains("June"))
                            otherFees.computerFeeSenior * 2
                        else
                            otherFees.computerFeeSenior


                } else {
                    computerFee = 0

                }

            } else {
                computerFee = 0
            }

            if (monthList.contains("July")) {
                if (!student.newStudent) {
                    annualCharge = otherFees.annualCharge
                }
            }
            computePayablePrice()

        } else {
            billMonthString = ""
            dbMonthList.clear()
            transportPrice.text = "₹ ${0}"
            placePrice =0
            place_rv.visibility = View.GONE
            placeList.forEach {
                if (it.isSelected){
                    it.isSelected = (!it.isSelected)
                }
            }
            place_rv.adapter?.notifyDataSetChanged()
            computePayablePriceWithoutSome()

        }

    }


    fun computePayablePrice() {

        total =
            admissionFee + tuitionFee + bookPrice + jtiePrice + stiePrice + diaryPrice + beltPrice + ifeeCardPrice +placePrice+ examFee + computerFee + annualCharge
        title.text = "₹ ${total}"

        tuition.text = "₹ ${tuitionFee}"
        transport.text = "₹ ${placePrice}"
        examination.text = "₹ ${examFee}"
        computer.text = "₹ ${computerFee}"
        annual.text = "₹ ${annualCharge}"
        admission.text = "₹ ${admissionFee}"
        supplement.text = "₹ ${jtiePrice + stiePrice + diaryPrice + beltPrice + ifeeCardPrice}"
        bookP.text = "₹ ${bookPrice}"
        payableAmount.text = "₹ ${total}"


    }

    fun computePayablePriceWithoutSome() {

        examFee = 0
        computerFee = 0
        annualCharge = 0

        total =
            admissionFee + tuitionFee + bookPrice + jtiePrice + stiePrice + diaryPrice + beltPrice + ifeeCardPrice + placePrice

        title.text = "₹ ${total}"
        tuition.text = "₹ ${tuitionFee}"
        transport.text = "₹ ${placePrice}"
        examination.text = "₹ 0"
        computer.text = "₹ 0"
        annual.text = "₹ 0"
        admission.text = "₹ ${admissionFee}"
        supplement.text = "₹ ${jtiePrice + stiePrice + diaryPrice + beltPrice + ifeeCardPrice}"
        bookP.text = "₹ ${bookPrice}"
        payableAmount.text = "₹ ${total}"


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun getClassName(student: Student?): String {

        when (student?.entryClass) {
            "Play Group" -> {
                return "Play Group"
            }
            "Lower Nursery" -> {
                return "Lower Nursery"
            }
            "Upper Nursery" -> {
                return "Upper Nursery"
            }
            "Class One" -> {
                return "1"
            }
            "Class Two" -> {
                return "2"
            }
            "Class Three" -> {
                return "3"
            }
            "Class Four" -> {
                return "4"
            }
            "Class Five" -> {
                return "5"
            }
            "Class Six" -> {
                return "6"
            }
            "Class Seven" -> {
                return "7"
            }
            "Class Eight" -> {
                return "8"
            }
        }
        return ""

    }

    private fun getBillMonthString(monthList: ArrayList<String>): String {

        if (monthList.size == 1) {
            return monthList[0]
        } else {
            var str = ""
            monthList.forEachIndexed { index: Int, s: String ->

                str += monthList[index].subSequence(0, 3).toString() + " "

            }
            return str
        }


    }


    private fun getBillNumber() {
        db.collection("bill_number").document("bill").get()
            .addOnSuccessListener {
                Log.d("TAGG", it.get("number").toString())
                billNo  = it.get("number").toString().toInt()

            }.addOnFailureListener {

            }

    }

    private fun verifyPopup() {
        val dialogBuilder = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.verify_layout, null)
        verify_password = view.findViewById(R.id.password_Layout)
        verify_button = view.findViewById(R.id.verify)
        dialogBuilder.setView(view)
        dialogBuilder.setCancelable(true)
        alert = dialogBuilder.create()
        alert.show()
        verify_button.setOnClickListener {

            if (verify_password.editText?.text?.toString()?.toLowerCase()
                    ?.trim() == otherFees.verificationPassword
            ) {

                var currentDate =
                    Common.getDay(Calendar.getInstance()) + "-" + Common.getMonth(Calendar.getInstance()) + "-" + Common.getYear(
                        Calendar.getInstance()
                    )
                var name = student.firstName + " " + student.lastName
                var className = getClassName(student)
                var months = billMonthString

                HtmlString.pdfStr = HtmlString.getHtmlForPdf(
                    billNo+1, currentDate, className, name,
                    admissionFee, annualCharge, tuitionFee, computerFee,
                    if(dbMonthList.isNotEmpty()) placePrice * dbMonthList.size else placePrice,
                    examFee,
                    (jtiePrice + stiePrice + diaryPrice + beltPrice + ifeeCardPrice),
                    bookPrice, 0, total, months
                )
                var bill = Bill(billNo+1,currentDate,className,name,admissionFee,annualCharge,tuitionFee,computerFee
                ,placePrice,examFee,
                    (jtiePrice + stiePrice + diaryPrice + beltPrice + ifeeCardPrice),
                    bookPrice,0,total,months)
                savePaymentToDb(bill)

            }

        }


    }

    private fun savePaymentToDb(bill: Bill) {

        db.collection("bill_number").document("bill").set(BillNumber(1+billNo))
        if(dbMonthList.isNotEmpty()){
            for (s in dbMonthList) {
                db.collection(intentCollection).document(intent.getStringExtra("enroll"))
                    .collection("tuitionFee").document(s).update("paid", true)
            }
        }

        db.collection(intentCollection).document(intent.getStringExtra("enroll")).collection("bills")
            .document("${1+billNo}").set(bill)
            .addOnSuccessListener {
                loadMonthAdapter()
                getBillNumber()
                val i = Intent(this, PrintWebView::class.java)
                startActivity(i)
                alert.dismiss()
            }
            .addOnFailureListener {  }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.previous_bill -> {

                db.collection(intentCollection).document(intent.getStringExtra("enroll"))
                    .collection("bills").orderBy("billNo", Query.Direction.DESCENDING)
                    .get().addOnFailureListener {  }.addOnSuccessListener {
                        var list: ArrayList<Bill> = ArrayList()
                        var bill: Bill
                        for (document in it) {
                            bill = document.toObject(Bill::class.java)
                            list.add(bill)
                        }
                        previousPopup(list.size, list)

                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun previousPopup(
        size: Int,
        list: ArrayList<Bill>
    ) {
                if (size == 0){
                    Toast.makeText(this,"No record available",Toast.LENGTH_SHORT).show()
                }else{
                    val dialogBuilder = AlertDialog.Builder(this)
                    val view: View = layoutInflater.inflate(R.layout.pevious_bill, null)
                    view.previours_bill_rv.layoutManager = LinearLayoutManager(this)
                    view.previours_bill_rv.adapter = PreviousBill(list)
                    dialogBuilder.setView(view)
                    dialogBuilder.setCancelable(true)
                    alert = dialogBuilder.create()
                    alert.show()
                }


    }


}
