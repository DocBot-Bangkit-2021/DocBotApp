package com.example.docbot.ui.hasil

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.docbot.R
import com.example.docbot.databinding.ActivityResultDiseasesBinding
import com.example.docbot.ml.Diseases01V8Best
import com.example.docbot.ui.cekgejala.CameraActivity
import com.example.docbot.ui.cekgejala.CheckCameraActivity
import com.example.docbot.ui.cekgejala.CheckViewModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ResultDiseasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultDiseasesBinding

    private lateinit var bitmap: Bitmap
    private var statusPhoto = false
    private var photo : String? = null
    private var name : String? = null
    private var penanganan : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultDiseasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra(EXTRA_NAME)
        photo = intent.getStringExtra(EXTRA_IMAGE)
        penanganan = intent.getStringExtra(EXTRA_DATA)
        setData()

        //dummy artikel
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[CheckViewModel::class.java]
        val artikel = viewModel.getNews()
        val newsAdapter = ResultNewsAdapter()
        newsAdapter.setNews(artikel.take(2))
        with(binding.rvNewsRs){
            layoutManager = LinearLayoutManager(this@ResultDiseasesActivity)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

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
                val inputString = application.assets.open("diseases.txt").bufferedReader().use { it.readText() }
                val list =  inputString.split("\n")

                val model = Diseases01V8Best.newInstance(this)

                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                inputFeature0.loadBuffer(getImageData(224))

                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                val max = getMax(outputFeature0.floatArray)
                name = list[max]
                model.close()

                setData()

            } else Toast.makeText(this, "Ambil gambar dulu", Toast.LENGTH_SHORT).show()
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
        else if (requestCode == CheckCameraActivity.CODE_GALLERY && resultCode == Activity.RESULT_OK){
            statusPhoto = true

            val uri: Uri? =  data?.data
            photo = uri.toString()
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

            Glide.with(this).load(data?.data).into(binding.imageView2)
        }
    }

    private fun getMax(arr: FloatArray): Int{
        var ind = 0
        var min = 0.0F

        for (i in 0..3)
        {
            if(arr[i] > min){
                ind = i
                min = arr[i]
            }
        }
        return ind
    }

    private fun getImageData(num: Int) : ByteBuffer {
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

    private fun setData(){
        binding.tvAnalisis.text = resources.getString(R.string.hasil_analisis_2, name)
        binding.tvPenanganan.text = resources.getString(R.string.pengananan, penanganan)
        Glide.with(this).load(photo).into(binding.imageView2)
    }

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_DATA = "extra_data"
        const val CODE_CAMERA = 1
        const val CODE_GALLERY = 2
    }
}