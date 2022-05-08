package ru.mirea.istornikov.mireaproject.ui.hardware

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import ru.mirea.istornikov.mireaproject.MainActivity
import ru.mirea.istornikov.mireaproject.R
import ru.mirea.istornikov.mireaproject.databinding.FragmentHardwareBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class HardwareFragment : Fragment() {
    private lateinit var binding : FragmentHardwareBinding

    private var isWork = false
    private val REQUEST_CODE_PERMISSION = 100
    val TAG = MainActivity::class.java.simpleName
    private val PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHardwareBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkPermissions(root)

        setSensors(root)
        setCamera()
        setRecorder()
        return root
    }


    private lateinit var mediaRecorder : MediaRecorder
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioFile : File
    private lateinit var recordBtn : Button
    private lateinit var listenBtn : Button
    private fun setRecorder(){
        mediaRecorder = MediaRecorder()
        mediaPlayer = MediaPlayer()

        recordBtn = binding.recordButton
        recordBtn.setOnClickListener(this::recordClick)
        recordBtn.text = "▶"

        listenBtn = binding.listenButton
        listenBtn.setOnClickListener(this::listenClick)
        listenBtn.text = "▶"


    }

    private fun recordClick(view: View){
        when((view as Button).text){
            "▶" -> {
                view.text = "◼"
                listenBtn.isEnabled = false
                listenBtn.requestFocus()
                startRecording()
            }
            "◼" -> {
                view.text = "▶"
                listenBtn.isEnabled = true
                mediaRecorder.stop()
            }
        }
    }

    private fun listenClick(view: View){
        when((view as Button).text){
            "▶" -> {
                view.text = "◼"
                recordBtn.isEnabled = false
                recordBtn.requestFocus()
                startListening()
            }
            "◼" -> {
                view.text = "▶"
                recordBtn.isEnabled = true
                mediaPlayer.stop()
            }
        }
    }

    private fun startRecording(){
        mediaRecorder.release()
        mediaRecorder = MediaRecorder()
        if(Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
            Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()){
            Log.d(TAG,"sd-card success")
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            audioFile = File(
                requireContext().getExternalFilesDir(
                    Environment.DIRECTORY_MUSIC
                ), "mirea.3gp"
            )
            mediaRecorder.setOutputFile(audioFile.absolutePath)
            mediaRecorder.prepare()
            mediaRecorder.start()
        }
    }

    private fun startListening(){
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()

        mediaPlayer.setDataSource(audioFile.path)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }



    private lateinit var imageUri : Uri
    private lateinit var photoFile: File
    private val CAMERA_REQUEST = 0
    private fun setCamera(){
        binding.cameraButton.setOnClickListener(this::cameraClick)
    }

    private fun cameraClick(view: View){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null && isWork) {
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val authorities = requireActivity().applicationContext.packageName + ".fileprovider"
            imageUri = FileProvider.getUriForFile(view.context, authorities, photoFile)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE_" + timeStamp + "_"
        val storageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            binding.cameraImage.setImageURI(imageUri)
        }
    }

    private fun checkPermissions(view: View){
        var cameraPermissionStatus = ContextCompat.checkSelfPermission(view.context,android.Manifest.permission.CAMERA)
        var storagePermissionStatus = ContextCompat.checkSelfPermission(view.context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) isWork = true
        else ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),CAMERA_REQUEST)

        isWork = hasPermissions(view.context, PERMISSIONS);
        if (!isWork) {
            ActivityCompat.requestPermissions(
                requireActivity(), PERMISSIONS,
                REQUEST_CODE_PERMISSION)
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>) : Boolean{
        for (permission in permissions) {
            if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }



    private lateinit var sensorManager: SensorManager
    private fun setSensors(view: View){
        val listCountSensor = view.findViewById(R.id.sensorsList) as ListView
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        val arrayList: ArrayList<HashMap<String, Any>> = ArrayList()
        var sensorTypeList: HashMap<String, Any>
        for (i in 0..2) {
            sensorTypeList = HashMap()
            sensorTypeList["Name"] = sensors[i].getName()
            sensorTypeList["Value"] = sensors[i].getMaximumRange()
            arrayList.add(sensorTypeList)
        }

        val mHistory = SimpleAdapter(
            view.context,
            arrayList,
            android.R.layout.simple_list_item_2,
            arrayOf("Name", "Value"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        listCountSensor.adapter = mHistory

    }

}