package com.devyk.common.ext

import android.util.Log
import java.io.File

private const val TAG = "FileExt"


fun File.ensureDir(): Boolean {
    try {
        isDirectory.no {
            isFile.yes {
                delete()
            }
            return mkdirs()
        }
    } catch (e: Exception) {
        Log.w(TAG, e.message)
    }
    return false
}