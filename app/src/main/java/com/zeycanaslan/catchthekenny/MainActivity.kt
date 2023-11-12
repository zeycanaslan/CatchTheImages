package com.zeycanaslan.catchthekenny

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.zeycanaslan.catchthekenny.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var score = 0  // global
    var imageArray = ArrayList<ImageView>()
    private lateinit var runnable: Runnable
    var handler = Handler(Looper.getMainLooper())
    private lateinit var alertDialogBuilder: AlertDialog.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //imageArray
        imageArray.add(binding.imageView9)
        imageArray.add(binding.imageView12)
        imageArray.add(binding.imageView13)
        imageArray.add(binding.imageView14)
        imageArray.add(binding.imageView15)
        imageArray.add(binding.imageView16)
        imageArray.add(binding.imageView17)
        imageArray.add(binding.imageView18)
        imageArray.add(binding.imageView19)

    }



    fun start(view: View) {

        // SAYAÇ
        object : CountDownTimer(10000, 100) {
            override fun onTick(p0: Long) {  // her saniyede napim
                binding.textView.text = "Time:${p0 / 1000} "
            }

            override fun onFinish() {  // bitince napim
                binding.textView.text = "Time:0"
                handler.removeCallbacks(runnable)  // runnable yi durdurur

                for(image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                // alert dialog

                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("GAME OVER")
                alert.setMessage("Restart The Game?")
                alert.setPositiveButton("Yes", {dialogInterface , i ->

                    // restart
                    val intentFromMain = intent
                    finish()
                    startActivity(intentFromMain)
                } )


                alert.setNegativeButton("No", {dialogInterface , i ->
                    Toast.makeText(this@MainActivity ,  "Game Over", Toast.LENGTH_LONG).show()

                }).show()


            }

        }.start()

        binding.button.visibility = View.GONE


        runnable = object : Runnable {
            override fun run() {

                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable, 500)
            }
        }
        handler.post(runnable)


    }

    fun increaseScore (view: View){
        score = score + 1
        binding.textView1.text = "Score: ${score} "
    }
}