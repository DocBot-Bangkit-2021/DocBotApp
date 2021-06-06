package com.example.docbot.ui.cekgejala

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.docbot.databinding.ActivityCheckBinding
import com.example.docbot.ml.CovidModel02V2
import com.example.docbot.ui.hasil.ResultCvActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.util.*

class CheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckBinding
//    private lateinit var byteBuffer: ByteBuffer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set action bar
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cek Gejala Covid"

        binding.btnCekLoading.setOnClickListener {

            val genderCheck = binding.radioGroup.checkedRadioButtonId
            val ageCheck = binding.editText.text
            when {
                genderCheck <= 0 -> {
                    Toast.makeText(this, "Pilih gender terlebih dahulu", Toast.LENGTH_SHORT).show()
                }
                ageCheck.isEmpty() -> {
                    binding.editText.error = "Pilih age dulu"
                }
                else -> {
                    //prepare variable
                    val age = (ageCheck.toString().toFloat() - 1) / (96 - 1)

                    val model = CovidModel02V2.newInstance(this)
                    var byteBuffer : ByteBuffer = ByteBuffer.allocateDirect(21 * 4)
                    byteBuffer.putFloat(age)

                    //gender
                    if (binding.radioFemale.isChecked) byteBuffer.putInt(1)
                    else byteBuffer.putInt(0)

                    if (binding.radioMale.isChecked) byteBuffer.putInt(1)
                    else byteBuffer.putInt(0)

                    //demam
                    if (binding.cbDemam.isChecked) byteBuffer.putInt(0).putInt(1)
                    else byteBuffer.putInt(1).putInt(0)

                    //batuk
                    if (binding.cbBatuk.isChecked) {
                        byteBuffer.putInt(0)
                        byteBuffer.putInt(1)
                    }else{
                        byteBuffer.putInt(1)
                        byteBuffer.putInt(0)
                    }
                    //hidung
                    if (binding.cbHidung.isChecked) {
                        byteBuffer.putInt(0)
                        byteBuffer.putInt(1)
                    }else{
                        byteBuffer.putInt(1)
                        byteBuffer.putInt(0)
                    }
                    //nyeri
                    if (binding.cbNyeri.isChecked) {
                        byteBuffer.putInt(0)
                        byteBuffer.putInt(1)
                    }else{
                        byteBuffer.putInt(1)
                        byteBuffer.putInt(0)
                    }
                    //lelah
                    if (binding.cbLelah.isChecked) {
                        byteBuffer.putInt(0)
                        byteBuffer.putInt(1)
                    }else{
                        byteBuffer.putInt(1)
                        byteBuffer.putInt(0)
                    }
                    //diare
                    if (binding.cbDiare.isChecked) {
                        byteBuffer.putInt(0)
                        byteBuffer.putInt(1)
                    }else{
                        byteBuffer.putInt(1)
                        byteBuffer.putInt(0)
                    }
                    //paru
                    if (binding.cbParu.isChecked) {
                        byteBuffer.putInt(0)
                        byteBuffer.putInt(1)
                    }else{
                        byteBuffer.putInt(1)
                        byteBuffer.putInt(0)
                    }
                    //jalan
                    if (binding.cbJalan.isChecked) {
                        byteBuffer.putInt(0)
                        byteBuffer.putInt(1)
                    }else{
                        byteBuffer.putInt(1)
                        byteBuffer.putInt(0)
                    }
                    //isolasi
                    if (binding.cbIsolasi.isChecked) {
                        byteBuffer.putInt(0)
                        byteBuffer.putInt(1)
                    }else{
                        byteBuffer.putInt(1)
                        byteBuffer.putInt(0)
                    }


                    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 21), DataType.FLOAT32)
                    inputFeature0.loadBuffer(byteBuffer)

                    val outputs = model.process(inputFeature0)
                    val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

                    model.close()

                    var result = ""
                    if (outputFeature0[0] > 0.65){
                        result = "positif"
                    }else{
                        result = "negatif"
                    }
                    val intent = Intent(this, ResultCvActivity::class.java)
                    intent.putExtra(ResultCvActivity.EXTRA_DATA, result)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}