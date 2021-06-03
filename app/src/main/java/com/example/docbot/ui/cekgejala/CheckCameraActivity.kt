package com.example.docbot.ui.cekgejala

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.docbot.databinding.ActivityCheckCameraBinding
import com.example.docbot.ml.Fruitsvegetable02V5
import com.example.docbot.ui.hasil.ResultActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CheckCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckCameraBinding

    private lateinit var bitmap: Bitmap

    private var status_photo = false

    private var photo : String? = null
    private var rotate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filename = "fruits2.txt"
        val inputString = application.assets.open(filename).bufferedReader().use { it.readText() }
        val list = inputString.split("\n")

        binding.btnAmbil.setOnClickListener {
            startActivityForResult(Intent(this, CameraActivity::class.java), CODE_CAMERA)
        }
        binding.btnPilih.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, CODE_GALLERY)
        }
        binding.btnCek.setOnClickListener {
            if (status_photo){
                val resized : Bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true)
                val imgData: ByteBuffer = ByteBuffer.allocateDirect(Float.SIZE_BYTES * 150 * 150 * 3)
                imgData.order(ByteOrder.nativeOrder())

                val intValues = IntArray(150 * 150)
                resized.getPixels(intValues, 0, resized.width, 0, 0, resized.width, resized.height)

                var pixel = 0
                for (i in 0..149) {
                    for (j in 0..149) {
                        val `val` = intValues[pixel++]
                        imgData.putFloat((`val` shr 16 and 0xFF) / 255f)
                        imgData.putFloat((`val` shr 8 and 0xFF) / 255f)
                        imgData.putFloat((`val` and 0xFF) / 255f)
                    }
                }

                val model = Fruitsvegetable02V5.newInstance(this)

                // Creates inputs for reference.
                val inputFeature0 = TensorBuffer.createFixedSize(
                    intArrayOf(1, 150, 150, 3),
                    DataType.FLOAT32
                )

                val tBuffer = TensorImage.fromBitmap(resized)

                val byteBuffer = TensorImage.createFrom(tBuffer, DataType.FLOAT32).buffer
//                Log.d("CEK", "$byteBuffer -------- $inputFeature0")
                inputFeature0.loadBuffer(imgData)

                // Runs model inference and gets result.
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                val max = getMax(outputFeature0.floatArray)

                val name = list[max]

                Log.d("CEK", "$name")

                // Releases model resources if no longer used.
                model.close()

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_NAME, name)
                intent.putExtra(ResultActivity.EXTRA_IMAGE, photo)
                intent.putExtra(ResultActivity.EXTRA_VIT, "vitamin C")
                startActivity(intent)

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
                if (!rotate){
                    binding.imageView2.rotation = 90F
                    rotate = true
                }
                status_photo = true

                photo = uri
                val u = Uri.parse(uri)
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, u)
            }
        }
        else if (requestCode == CODE_GALLERY && resultCode == Activity.RESULT_OK){
            status_photo = true

            val uri: Uri? =  data?.data
            photo = uri.toString()
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

            if (rotate){
                val matrix = Matrix()
                matrix.postRotate(-90F)
                val rotateBitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    0,
                    bitmap.width,
                    bitmap.height,
                    matrix,
                    true
                )
                Glide.with(this).load(rotateBitmap).into(binding.imageView2)
                rotate = false
            }else Glide.with(this).load(data?.data).into(binding.imageView2)
        }
    }

    private fun getMax(arr: FloatArray): Int{
        var ind = 0
        var min = 0.0F

        for (i in 0..5)
        {
            if(arr[i] > min){
                ind = i
                min = arr[i]
            }
        }
        return ind
    }

    companion object {
        const val CODE_CAMERA = 1
        const val CODE_GALLERY = 2
    }
}