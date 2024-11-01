package group.kalmykov.safe.functions

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log

@SuppressLint("ObsoleteSdkInt")
fun vibrator(context: Context, milliseconds: Long = 100, vibrationEffect: Int = VibrationEffect.DEFAULT_AMPLITUDE){

    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    val vibrationEffect1: VibrationEffect =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect.createOneShot(milliseconds, vibrationEffect)
        } else {
            Log.e("TAG", "Cannot vibrate device..")
            TODO("VERSION.SDK_INT < O")
        }

    // it is safe to cancel other
    // vibrations currently taking place
    vibrator.cancel()
    vibrator.vibrate(vibrationEffect1)
}