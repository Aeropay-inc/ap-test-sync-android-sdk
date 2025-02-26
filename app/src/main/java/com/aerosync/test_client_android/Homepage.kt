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
import androidx.fragment.app.FragmentActivity
import com.aerosync.bank_link_sdk.EventListener
import com.aerosync.bank_link_sdk.PayloadEventType
import com.aerosync.bank_link_sdk.PayloadSuccessType
import com.aerosync.bank_link_sdk.Widget



class Homepage : FragmentActivity(), EventListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val dropdown = findViewById<Spinner>(R.id.spinner)
        //create a list of items for the spinner.
        val items = arrayOf("STAGE", "SANDBOX", "PROD")
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
                config.environment = environment.toString(); //STAGE,PROD
                config.token = token.toString();
                config.consumerId = consumerId.toString();
                config.open();
            }
        }
    }

    override fun onSuccess(event: PayloadSuccessType?, context: Context?) {
        if (event != null) {
            Log.d("aerosync-tag", "ON SUCCESS EVENT ${event.toString()}")
            Toast.makeText(context,  "user = ${event.user_id}, " +
                    "ClientName = ${event.ClientName}, " +
                    "FILoginAcctId = ${event.FILoginAcctId}", Toast.LENGTH_SHORT).show()
        }
        val intent = Intent(context, Homepage::class.java)
        context?.startActivity(intent);
        (context as Activity).finish()
    }

    override fun onEvent(event: PayloadEventType?, context: Context?) {
        if (event != null) {
            Log.d("aerosync-tag", "ON EVENT EVENT ${event.toString()}")
            Toast.makeText(context, "ONEVENT: onLoadApi = ${event.onLoadApi},\" +\n" +
                    "                    \"pageTitle = ${event.pageTitle}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(error: String?, context: Context?) {
        Log.d("aerosync-tag", "ON ERROR EVENT ${error.toString()}")
        Toast.makeText(context, "ONERROR: ${error}", Toast.LENGTH_SHORT).show()
    }

    override fun onClose(context: Context?) {
        Log.d("aerosync-tag", "ON CLOSE EVENT")
        Toast.makeText(context, "ONCLOSE", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, Homepage::class.java)
        context?.startActivity(intent);
        (context as Activity).finish()

    }


}