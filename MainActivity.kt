package com.kuppa.web3samplekotlin

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.web3j.crypto.CipherException
import org.web3j.crypto.WalletUtils
import java.io.File
import java.io.IOException
import java.security.InvalidAlgorithmParameterException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException

class MainActivity : AppCompatActivity() {

    var newFile1 = null

    @SuppressLint("SdCardPath")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()

        val path = Environment.getDataDirectory().absolutePath.toString() + "/storage/emulated/0/appFolder"
        val mFolder = File(path)
        if (!mFolder.exists()) {
                mFolder.mkdir()
            }
        var walletPathFile = File("/sdcard/EthWallet/")
        walletPathFile.mkdirs()

        CreateWalletBtn.setOnClickListener {
            val walletPath = WalletUtils.generateFullNewWalletFile("12345", walletPathFile)
            Toast.makeText(this, "Wallet created successfully.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions, 101)
            }
        }
    }

    @Throws(CipherException::class, InvalidAlgorithmParameterException::class, NoSuchAlgorithmException::class, NoSuchProviderException::class, IOException::class)
    fun createWallet(view: View) {

    }

}
