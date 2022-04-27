package ru.mirea.istornikov.loadermanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {
    val TAG:String = this.javaClass.simpleName
    var LoaderID:Int = 1111
    lateinit var inputTextView:TextView
    lateinit var editText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editRandomText)
        inputTextView = findViewById(R.id.result)
        val bundle:Bundle = Bundle()
        bundle.putString("word","mirea")
        supportLoaderManager.initLoader(LoaderID,bundle, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
        Toast.makeText(this, "onCreateLoader" + id,Toast.LENGTH_SHORT).show()
        return MyLoader(this, args)
    }

    override fun onLoadFinished(loader: Loader<String>, data: String?) {
        if(loader.id == LoaderID){
            Log.d(TAG,"onLoadFinished" + data)
            Toast.makeText(this, "onLoadFinished" + data, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLoaderReset(loader: Loader<String>) {
        Log.d(TAG,"onLoaderReset")
    }

    fun onClick(view:View){
        val bundle = Bundle()
        bundle.putString("OldWord", editText.text.toString())
        LoaderID++
        supportLoaderManager.initLoader(LoaderID, bundle, this)
        inputTextView.text = randomString(editText.text.toString())
    }

    private fun randomString(s: String) : String{
        val res = s.toCharArray()
        res.shuffle()
        return res.concatToString()
    }
}