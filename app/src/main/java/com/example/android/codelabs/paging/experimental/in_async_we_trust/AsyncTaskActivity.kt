package com.example.android.codelabs.paging.experimental.in_async_we_trust

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.databinding.ActivityAsyncBinding
import com.example.android.codelabs.paging.databinding.ActivityMainBinding

class AsyncTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAsyncBinding

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        //setContentView(R.layout.activity_main)

        binding = ActivityAsyncBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val context = this.applicationContext

        binding.button.setOnClickListener {
            binding.textView.text = "ddd"
        }
    }

    class MyProgressBarAsyncTask(val context: Context) : AsyncTask<Void, Integer, Void>() {

        var progressBarValue = 0

        override fun onPreExecute() {
            super.onPreExecute()
            Toast.makeText(context, "Before start", Toast.LENGTH_LONG).show()
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
        }

        override fun onProgressUpdate(vararg values: Integer?) {
            super.onProgressUpdate(*values)
            Toast.makeText(context, "Before finish", Toast.LENGTH_LONG).show()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            return null
        }

    }
}