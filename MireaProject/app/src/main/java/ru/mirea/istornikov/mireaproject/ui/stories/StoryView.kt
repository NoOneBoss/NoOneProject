package ru.mirea.istornikov.mireaproject.ui.stories

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.gson.GsonBuilder
import ru.mirea.istornikov.mireaproject.MainActivity
import ru.mirea.istornikov.mireaproject.R
import ru.mirea.istornikov.mireaproject.ui.stories.database.App
import ru.mirea.istornikov.mireaproject.ui.stories.database.Story
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class StoryView : AppCompatActivity() {
    private lateinit var cameraImage : ImageView
    private lateinit var imageUri: Uri
    companion object {
        private lateinit var photoFile: File
    }
    private val CAMERA_REQUEST = 0

    private var isWork = false
    private val REQUEST_CODE_PERMISSION = 100
    val TAG = MainActivity::class.java.simpleName
    private val PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_view)
        checkPermissions()
        cameraImage = findViewById(R.id.image)
        cameraImage.setOnClickListener(this::cameraClick)
        findViewById<Button>(R.id.saveBtn).setOnClickListener(this::saveClick)
    }

    private fun saveClick(view: View){
        val database = App.instance.database
        val storyDao = database.storyDao()

        val authorities = applicationContext.packageName + ".fileprovider"
        imageUri = FileProvider.getUriForFile(this, authorities, photoFile)

        val story = Story()
        story.imagePath = imageUri.toString()
        story.storyDate = SimpleDateFormat("d MMM. yyyy HH:mm").format(Date())
        story.storyText = findViewById<TextView>(R.id.storyText).text.toString()
        storyDao.insert(story)

        println(photoFile.toString())
        println(GsonBuilder().create().toJson(imageUri).toString())
        println(GsonBuilder().create().toJson(story).toString())
        val intent = Intent(this, StoriesFragment::class.java)
        startActivity(intent)
    }

    private fun cameraClick(view: View){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null && isWork) {
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val authorities = applicationContext.packageName + ".fileprovider"
            imageUri = FileProvider.getUriForFile(view.context, authorities, photoFile)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE_" + timeStamp + "_"
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            cameraImage.setImageURI(imageUri)
        }
    }

    private fun checkPermissions(){
        var cameraPermissionStatus = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        var storagePermissionStatus = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) isWork = true
        else ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),CAMERA_REQUEST)

        isWork = hasPermissions(this, PERMISSIONS);
        if (!isWork) {
            ActivityCompat.requestPermissions(
                this, PERMISSIONS,
                REQUEST_CODE_PERMISSION)
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>) : Boolean{
        for (permission in permissions) {
            if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }
}