package com.example.screenbrightness_usingvolumekeys

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.security.Key
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    var brightnessValue = 255

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun decrease(){ // Get app context object.

        val context = applicationContext

        // Check whether has the write settings permission or not.
        val settingsCanWrite = hasWriteSettingsPermission(context)

        // If do not have then open the Can modify system settings panel.
        if (!settingsCanWrite) {
            changeWriteSettingsPermission(context)
        } else {
            if(brightnessValue >= 11) {
                brightnessValue -= 10
                changeScreenBrightness(context, brightnessValue)
                val k = brightnessValue.toDouble()/255
                Toast.makeText(applicationContext,"Brightness : ${round(k*100)}%", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun increase(){
        val context = applicationContext

        // Check whether has the write settings permission or not.
        val settingsCanWrite = hasWriteSettingsPermission(context)

        // If do not have then open the Can modify system settings panel.
        if (!settingsCanWrite) {
            changeWriteSettingsPermission(context)
        } else {
            if (brightnessValue <= 245) {
                brightnessValue += 10
                changeScreenBrightness(context, brightnessValue)
                val k = brightnessValue.toDouble()/255
                Toast.makeText(applicationContext,"Brightness : ${round(k*100)}%", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            decrease()
        }
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            increase()
        }

        return true
    }

    // Check whether this app has android write settings permission.
    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasWriteSettingsPermission(context: Context): Boolean {
        var ret = true
        // Get the result from below code.
        ret = Settings.System.canWrite(context)
        return ret
    }

    // Start can modify system settings panel to let user change the write settings permission.
    private fun changeWriteSettingsPermission(context: Context) {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        context.startActivity(intent)
    }

    // This function only take effect in real physical android device,
    // it can not take effect in android emulator.
    private fun changeScreenBrightness(context: Context, screenBrightnessValue: Int) {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue)

        /*
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = screenBrightnessValue / 255f;
        window.setAttributes(layoutParams);
        */
    }


}