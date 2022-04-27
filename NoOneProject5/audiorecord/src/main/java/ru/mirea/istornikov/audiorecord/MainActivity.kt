package ru.mirea.istornikov.audiorecord

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File

class MainActivity : AppCompatActivity() {

    private val TAG:String = MainActivity::class.java.simpleName
    private val REQUEST_CODE_PERMISSION = 100
    private var PERMISSIONS = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.RECORD_AUDIO)
    private var isWork: Boolean = false
    private lateinit var startRecordBtn : Button
    private lateinit var stopRecordBtn : Button
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var audioFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isWork = hasPermissions(this, PERMISSIONS)
        if(!isWork) ActivityCompat.requestPermissions(this,PERMISSIONS,REQUEST_CODE_PERMISSION)
        startRecordBtn = findViewById(R.id.btnStart)
        stopRecordBtn = findViewById(R.id.btnStop)
        mediaRecorder = MediaRecorder()
    }

    private fun hasPermissions(context: Context, permissions: Array<String>) : Boolean{
        for (permission in permissions) {
            if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_PERMISSION) isWork = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
    }

    fun onRecordStart(view: View){
        startRecordBtn.isEnabled = false
        stopRecordBtn.isEnabled = true
        stopRecordBtn.requestFocus()
        startRecording()
    }

    fun onRecordStop(view: View){
        startRecordBtn.isEnabled = true
        stopRecordBtn.isEnabled = false
        startRecordBtn.requestFocus()
        stopRecording()
        processAudioFile()
    }

    private fun startRecording(){
        if(Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
            Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()){
            Log.d(TAG,"sd-card success")
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            audioFile = File(
                getExternalFilesDir(
                    Environment.DIRECTORY_MUSIC
                ), "mirea.3gp"
            )
            mediaRecorder.setOutputFile(audioFile.absolutePath)
            mediaRecorder.prepare()
            mediaRecorder.start()
            Toast.makeText(this,"Recording started", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRecording(){
        Log.d(TAG,"stopRecording")
        mediaRecorder.stop()
        mediaRecorder.reset()
        mediaRecorder.release()
        Toast.makeText(this,"You are not recording right now!",Toast.LENGTH_SHORT).show()
    }

    private fun processAudioFile(){
        Log.d(TAG, "processAudioFile")
        var values:ContentValues = ContentValues(4)
        val current = System.currentTimeMillis()
        values.put(MediaStore.Audio.Media.TITLE,"audio" + audioFile.name)
        values.put(MediaStore.Audio.Media.DATE_ADDED,current / 1000)
        values.put(MediaStore.Audio.Media.MIME_TYPE,"audio/3gpp")
        values.put(MediaStore.Audio.Media.DATA,audioFile.absolutePath)
        Log.d(TAG,"audioFile: " + audioFile.canRead())
        val baseUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val newUri = contentResolver.insert(baseUri,values)
        sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri))
    }
}