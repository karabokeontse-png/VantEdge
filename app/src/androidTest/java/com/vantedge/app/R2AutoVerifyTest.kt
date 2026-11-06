package com.vantedge.app

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import android.os.SystemClock
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileOutputStream

@RunWith(AndroidJUnit4::class)
class R2AutoVerifyTest {
    private lateinit var device: UiDevice
    private val packageName = "com.vantedge.app"
    private val timeout = 10000L

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), timeout)
    }

    @Test
    fun verifyACU8ACU9EmptySkillsPersistence() {
        val outDir = File(Environment.getExternalStorageDirectory(), "Download/VantEdgeR2Auto")
        outDir.mkdirs()
        
        // Force stop and launch fresh
        device.executeShellCommand("am force-stop $packageName")
        SystemClock.sleep(1000)
        
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeout * 2)
        SystemClock.sleep(3000)
        
        // Try to navigate - look for common elements
        // Take screenshot of initial state
        takeScreenshot(File(outDir, "01_initial.png"))
        
        // Look for elements that might be in empty skills flow
        // This is a best effort - may need adjustment based on actual UI
        var obj = device.findObject(By.textContains("skill", true))
        if (obj.exists()) {
            obj.click()
            SystemClock.sleep(2000)
        }
        
        // Look for continue/start/analyze buttons
        obj = device.findObject(By.textContains("analy", true))
        if (obj.exists()) {
            obj.click()
            SystemClock.sleep(5000)
        }
        obj = device.findObject(By.textContains("continue", true))
        if (obj.exists()) {
            obj.click()
            SystemClock.sleep(3000)
        }
        obj = device.findObject(By.textContains("result", true))
        if (obj.exists()) {
            obj.click()
            SystemClock.sleep(3000)
        }
        
        // Take screenshot before force stop
        takeScreenshot(File(outDir, "02_before_force_stop.png"))
        device.dumpWindowHierarchy(File(outDir, "02_hierarchy.xml"))
        
        // Force stop
        device.executeShellCommand("am force-stop $packageName")
        SystemClock.sleep(2000)
        
        // Relaunch
        val intent2 = context.packageManager.getLaunchIntentForPackage(packageName)
        intent2?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent2)
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeout * 2)
        SystemClock.sleep(3000)
        
        // Try to navigate back to results/history
        obj = device.findObject(By.textContains("history", true))
        if (obj.exists()) {
            obj.click()
            SystemClock.sleep(2000)
        }
        obj = device.findObject(By.textContains("result", true))
        if (obj.exists()) {
            obj.click()
            SystemClock.sleep(2000)
        }
        
        // Take screenshot after relaunch
        takeScreenshot(File(outDir, "03_after_relaunch.png"))
        device.dumpWindowHierarchy(File(outDir, "03_hierarchy.xml"))
    }
    
    private fun takeScreenshot(file: File) {
        try {
            val bitmap = device.takeScreenshot()
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
