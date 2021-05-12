package com.example.PlantManagerApp


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class ReportAProblemActivity : AppCompatActivity() {

    private var buttonSend: Button? = null
    private var textTo: EditText? = null
    private var textSubject: EditText? = null
    private var textMessage: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_a_problem)

        buttonSend = findViewById<View>(R.id.buttonSend) as Button
        textSubject = findViewById<View>(R.id.editTextSubject) as EditText
        textMessage = findViewById<View>(R.id.editTextMessage) as EditText

        buttonSend!!.setOnClickListener(View.OnClickListener {
            val to = "vladlenaermak@gmail.com"
            val subject = textSubject!!.getText().toString()
            val message = textMessage!!.getText().toString()
            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            email.putExtra(Intent.EXTRA_SUBJECT, subject)
            email.putExtra(Intent.EXTRA_TEXT, message)

            email.type = "message/rfc822"
            startActivity(Intent.createChooser(email, "Выберите email клиент :"))
        })

    }

    fun to_help(view: View) {
        val HelpIntent = Intent(this, HelpActivity::class.java)
        startActivity(HelpIntent)
    }
}