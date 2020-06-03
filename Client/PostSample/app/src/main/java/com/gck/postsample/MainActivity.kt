package com.gck.postsample

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val post: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sampleBtn.setOnClickListener {
            MyAsyncTask().execute("L001")
        }
         button.setOnClickListener {
            MyAsyncTask().execute("L002")
        }
       // Log.d("Test",asynctask.res)
    }

    fun log(text: String?, id: String?) {
        val layout = findViewById<LinearLayout>(R.id.linearLayout)
        Log.d("Hoge", text)

        val json :JSONObject = JSONObject(text)
        val result = json.getJSONArray(id)
        for(i in 0 until result.length()) {
            println(result[i])
            val textView: TextView = TextView(this)
            textView.text = result[i].toString()
            layout.addView(textView)

        }

    }

    inner class MyAsyncTask: AsyncTask<String, Void, String>() {
        var res:String?=""

        override fun doInBackground(vararg params: String?): String? {
            return post(params[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            log(res, "result")
          //  log(res, "user")
        }

        fun post(command: String?): String?{
            val url = "http://10.0.2.2:3333"
            val client: OkHttpClient = OkHttpClient.Builder().build()

            // create json
            val json = JSONObject()
            json.put("command", command)

            // post
            val postBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())
            val request: Request = Request.Builder().url(url).post(postBody).build()
            val response = client.newCall(request).execute()

            // getResult
            val result: String? = response.body()?.string()
            response.close()
            Log.d("TEST",result)
            res = result
            return result
        }
    }
}
