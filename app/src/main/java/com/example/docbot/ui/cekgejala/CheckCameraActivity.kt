package com.example.docbot.ui.cekgejala

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.docbot.databinding.ActivityCheckCameraBinding
import com.example.docbot.ml.Diseases01V8Best
import com.example.docbot.ml.Fruitsvegetable02V5
import com.example.docbot.ui.hasil.ResultActivity
import com.example.docbot.ui.hasil.ResultDiseasesActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CheckCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckCameraBinding

    private lateinit var bitmap: Bitmap

    private var statusPhoto = false

    private var photo : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val check = intent.getStringExtra(EXTRA_ACT)

        binding.btnAmbil.setOnClickListener {
            startActivityForResult(Intent(this, CameraActivity::class.java), CODE_CAMERA)
        }

        binding.btnPilih.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, CODE_GALLERY)
        }

        binding.btnCek.setOnClickListener {
            if (statusPhoto){

                if (check == "buah"){
                    val list = getFileName("fruits2.txt")
                    val model = Fruitsvegetable02V5.newInstance(this)

                    // Creates inputs for reference.
                    val inputFeature0 = TensorBuffer.createFixedSize(
                            intArrayOf(1, 150, 150, 3),
                            DataType.FLOAT32
                    )
                    inputFeature0.loadBuffer(getImageData(150))

                    // Runs model inference and gets result.
                    val outputs = model.process(inputFeature0)
                    val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                    val max = getMax(outputFeature0.floatArray, 5)
                    val name = list[max]

                    // Releases model resources if no longer used.
                    model.close()

                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(ResultActivity.EXTRA_NAME, name)
                    intent.putExtra(ResultActivity.EXTRA_IMAGE, photo)
                    intent.putExtra(ResultActivity.EXTRA_VIT, "vitamin C")
                    startActivity(intent)
                    finish()
                }else if(check == "umum"){
                    val list = getFileName("diseases.txt")
                    val model = Diseases01V8Best.newInstance(this)

                    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                    inputFeature0.loadBuffer(getImageData(224))

                    val outputs = model.process(inputFeature0)
                    val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                    val max = getMax(outputFeature0.floatArray, 3)
                    val name = list[max]

                    model.close()

                    val intent = Intent(this, ResultDiseasesActivity::class.java)
                    intent.putExtra(ResultDiseasesActivity.EXTRA_NAME, name)
                    intent.putExtra(ResultDiseasesActivity.EXTRA_IMAGE, photo)
                    intent.putExtra(ResultDiseasesActivity.EXTRA_DATA, "segera melakukan konsultasi dengan dokter")
                    startActivity(intent)
                    finish()
                }

            }
            else Toast.makeText(this, "Ambil gambar dulu", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CODE_CAMERA){
            if(resultCode == Activity.RESULT_OK){
                val uri = data?.getStringExtra(CameraActivity.CEK_URI)
                Glide.with(this)
                        .load(uri)
                        .into(binding.imageView2)
                statusPhoto = true

                photo = uri
                val u = Uri.parse(uri)
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, u)
            }
        }
        else if (requestCode == CODE_GALLERY && resultCode == Activity.RESULT_OK){
            statusPhoto = true

            val uri: Uri? =  data?.data
            photo = uri.toString()
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

            Glide.with(this).load(data?.data).into(binding.imageView2)
        }
    }

    private fun getMax(arr: FloatArray, j: Int): Int{
        var ind = 0
        var min = 0.0F

        for (i in 0..j)
        {
            if(arr[i] > min){
                ind = i
                min = arr[i]
            }
        }
        return ind
    }

    private fun getImageData(num: Int) :ByteBuffer {
        val resized : Bitmap = Bitmap.createScaledBitmap(bitmap, num, num, true)
        val imgData: ByteBuffer = ByteBuffer.allocateDirect(Float.SIZE_BYTES * num * num * 3)
        imgData.order(ByteOrder.nativeOrder())

        val intValues = IntArray(num * num)
        resized.getPixels(intValues, 0, resized.width, 0, 0, resized.width, resized.height)

        var pixel = 0
        for (i in 0 until num) {
            for (j in 0 until num) {
                val `val` = intValues[pixel++]
                imgData.putFloat((`val` shr 16 and 0xFF) / 255f)
                imgData.putFloat((`val` shr 8 and 0xFF) / 255f)
                imgData.putFloat((`val` and 0xFF) / 255f)
            }
        }
        return imgData
    }

    private fun getFileName(name: String): List<String>{
        val inputString = application.assets.open(name).bufferedReader().use { it.readText() }
        return inputString.split("\n")
    }

    companion object {
        const val CODE_CAMERA = 1
        const val CODE_GALLERY = 2
        const val EXTRA_ACT = "extra_data"
    }
}