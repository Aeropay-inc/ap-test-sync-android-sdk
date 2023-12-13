package com.aerosync.test_client_android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aerosync.bank_link_sdk.EventListener
import com.aerosync.bank_link_sdk.Widget


class Homepage : AppCompatActivity(), EventListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val dropdown = findViewById<Spinner>(R.id.spinner)
        //create a list of items for the spinner.
        val items = arrayOf("DEV", "STAGE", "PROD")
        val adapter: Any? = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_dropdown_item, items)
        dropdown.adapter = adapter as SpinnerAdapter?
    }

    fun onClick(v: View?) {
        when (v?.id) {
            R.id.button -> {
                // open Aerosync widget
                val token = findViewById<EditText>(R.id.token).text;
                val consumerId = findViewById<EditText>(R.id.consumerId).text;
                val envSpinner = findViewById<View>(R.id.spinner) as Spinner
                val environment = envSpinner.selectedItem
                val config = Widget(this, this);
                config.environment = environment.toString(); //DEV, STAGE,PROD
                config.token = token.toString();
                config.consumerId = consumerId.toString();
                Log.d("widget", token.toString())
                Log.d("widget", consumerId.toString())
                Log.d("widget", environment.toString())
                Log.d("widget", config.url.toString())
                config.open();
            }
        }
    }

    override fun onSuccess(response: String?, context: Context) {
        // perform steps when user have completed the bank link workflow
        // sample code
        Toast.makeText(context, "onSuccess--> $response", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, Homepage::class.java)
        context.startActivity(intent);
    }

    override fun onEvent(type: String?, payload: String?, context: Context) {
        // capture all the Aerosync events
        // sample code
        Toast.makeText(context, "onEvent--> $payload", Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: String?, context: Context) {
        // error handling
        // sample code
        Toast.makeText(context, "onError--> $error", Toast.LENGTH_SHORT).show()
    }

    override fun onClose(context: Context) {
        // when widget is closed by user
        // sample code
        Toast.makeText(context,"widget closed", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, Homepage::class.java)
        context.startActivity(intent);
        (context as Activity).finish()
    }
}