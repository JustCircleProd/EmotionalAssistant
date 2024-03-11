package com.example.bd.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.db.R
import com.example.db.ml.Model
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


class MainActivity : AppCompatActivity() {
    private var camera: Button? = null
    private var gallery: Button? = null
    private var imageView: ImageView? = null
    private var result: TextView? = null
    private var imageSize = 48
    private lateinit var faceDetector: FaceDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        camera = findViewById(R.id.button)
        gallery = findViewById(R.id.button2)
        result = findViewById(R.id.result)
        imageView = findViewById(R.id.imageView)

        camera!!.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 3)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
        gallery!!.setOnClickListener {
            val cameraIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent, 1)
        }

        // High-accuracy landmark detection and face classification
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .build()

        faceDetector = FaceDetection.getClient(options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) {
                var image = data!!.extras!!["data"] as Bitmap?
                val dimension = Math.min(image!!.width, image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                imageView!!.setImageBitmap(image)
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
            } else {
                try {
                    val bitmap = this.contentResolver.openInputStream(data!!.data!!).use { stream ->
                        BitmapFactory.decodeStream(stream)
                    }
                    val image = InputImage.fromBitmap(bitmap, 0)
                    imageView!!.setImageBitmap(bitmap)

                    val result = faceDetector.process(image)
                        .addOnSuccessListener { faces ->
                            cropDetectedFace(bitmap, faces[0])
                        }
                        .addOnFailureListener { e ->
                            Log.d("Tag111", "OnFailureListener")
                        }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun cropDetectedFace(bitmap: Bitmap, face: Face) {
        val rect = face.boundingBox

        val x = rect.left.coerceAtLeast(0)
        val y = rect.top.coerceAtLeast(0)
        val width = rect.width()
        val height = rect.height()

        val croppedBitmap = Bitmap.createBitmap(
            bitmap,
            x,
            y,
            if (x + width > bitmap.width) bitmap.width - x else width,
            if (y + height > bitmap.height) bitmap.height - y else height,
        )

        imageView!!.setImageBitmap(croppedBitmap)
        classifyImage(croppedBitmap)
    }

    private fun Bitmap.toGrayscaleByteBuffer(): ByteBuffer {
        // Every float value needs 4 bytes of memory
        val mImgData: ByteBuffer = ByteBuffer.allocateDirect(4 * width * height)
        mImgData.order(ByteOrder.nativeOrder())

        val pixels = IntArray(width * height)
        this.getPixels(pixels, 0, width, 0, 0, width, height)

        for (pixel in pixels) {
            // After grayscale conversion, every channel shares the same color value
            // but I stay with the original conversion formula (in case grayscale conversion isn't there)
            val grayscaleValue =
                0.2989 * Color.red(pixel) + 0.5870 * Color.green(pixel) + 0.1140 * Color.blue(pixel)
            // Normalize color range

            mImgData.putFloat(grayscaleValue.toFloat() / 255f)
        }

        return mImgData
    }

    private fun classifyImage(imageS: Bitmap) {
        try {
            val image = Bitmap.createScaledBitmap(imageS, imageSize, imageSize, false)
            val model = Model.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(
                    intArrayOf(1, imageSize, imageSize, 1),
                    DataType.FLOAT32
                )
            val byteBuffer = image.toGrayscaleByteBuffer()

            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray.toList()
            // find the index of the class with the biggest confidence.

            val temp = mutableMapOf<String, Float>()
            val classes = arrayOf("Angry", "Disgust", "Fear", "Happy", "Neutral", "Sad", "Surprise")
            for (i in 0..6) {
                temp[classes[i]] = confidences[i]
            }
            Log.d("Tag111", confidences.toString())
            Log.d("Tag111", confidences.sortedDescending().toString())
            Log.d("Tag111", temp.toString())

            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            result!!.text = classes[maxPos]

            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }
}