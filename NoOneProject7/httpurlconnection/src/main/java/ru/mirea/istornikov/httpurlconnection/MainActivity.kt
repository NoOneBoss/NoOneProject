package ru.mirea.istornikov.httpurlconnection

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    private lateinit var ipTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var countryTextView: TextView
    private val url = "http://whatismyip.akamai.com/"

    private inner class DownloadPageTask :
        AsyncTask<String?, Void?, Info?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            ipTextView!!.text = "Loading..."
        }

        override fun doInBackground(vararg strings: String?): Info? {
            return try {
                val ip = downloadIpInfo(strings[0]!!)
                getInformationByIp(ip)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(info: Info?) {
            ipTextView.text = info!!.ip
            cityTextView.text = info.city
            countryTextView.text = info.country
            super.onPostExecute(info)
        }

        private fun getInformationByIp(ip: String): Info? {
            try {
                val content = getContentFromApi(
                    "http://ip-api.com/json/$ip",
                    "GET"
                )
                val responseJson = JSONObject(content)
                val country = responseJson["country"].toString()
                val city = responseJson["city"].toString()
                return Info(ip, city, country)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return null
        }

        @Throws(IOException::class)
        private fun getContentFromApi(address: String, method: String): String {
            var inputStream: InputStream? = null
            var data = ""
            try {
                val url = URL(address)
                val connection = url
                    .openConnection() as HttpURLConnection
                connection.readTimeout = 100000
                connection.connectTimeout = 100000
                connection.requestMethod = method
                connection.instanceFollowRedirects = true
                connection.useCaches = false
                connection.doInput = true
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.inputStream
                    val bos = ByteArrayOutputStream()
                    var read: Int
                    while (inputStream.read().also { read = it } != -1) {
                        bos.write(read)
                    }
                    bos.close()
                    data = bos.toString()
                } else {
                    data = connection.responseMessage + " . Error Code : " + responseCode
                }
                connection.disconnect()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream?.close()
            }
            return data
        }

        @Throws(IOException::class)
        private fun downloadIpInfo(address: String): String {
            return getContentFromApi(address, "GET")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ipTextView = findViewById(R.id.ipAdd)
        cityTextView = findViewById(R.id.city)
        countryTextView = findViewById(R.id.country)
        val getInfoButton = findViewById<Button>(R.id.buttonSV)
        getInfoButton.setOnClickListener { view: View ->
            onGetInfoClick(
                view
            )
        }
    }

    private fun onGetInfoClick(view: View) {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkinfo: NetworkInfo? = null
        if (connectivityManager != null) {
            networkinfo = connectivityManager.activeNetworkInfo
        }
        if (networkinfo != null && networkinfo.isConnected) {
            DownloadPageTask().execute(url)
        } else {
            Toast.makeText(
                this, "No Internet connection",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}