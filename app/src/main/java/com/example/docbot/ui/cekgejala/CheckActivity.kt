package com.example.docbot.ui.cekgejala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.docbot.R
import com.example.docbot.databinding.ActivityCheckBinding
import com.example.docbot.ml.CovidModel02V2
import com.example.docbot.ui.loadingcek.LoadingCekActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class CheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCekLoading.setOnClickListener {
//            startActivity(Intent(this, LoadingCekActivity::class.java))
//            Toast.makeText(this, "${binding.cbDemam.isChecked}", Toast.LENGTH_SHORT).show()

            val model = CovidModel02V2.newInstance(this)

            var byteBuffer : ByteBuffer = ByteBuffer.allocateDirect(21*4)
//            byteBuffer.putS

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 21), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            model.close()

        }
    }
}