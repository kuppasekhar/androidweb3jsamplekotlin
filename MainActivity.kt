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
import com.kuppa.web3samplekotlin.R.id.CreateWalletBtn
import kotlinx.android.synthetic.main.activity_main.*
import org.slf4j.LoggerFactory
import org.web3j.crypto.CipherException
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.io.File
import java.io.IOException
import java.math.BigDecimal
import java.security.InvalidAlgorithmParameterException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException

class MainActivity : AppCompatActivity() {

    var newFile1 = null
    private val log = LoggerFactory.getLogger(MainActivity::class.java)
    private var web3: Web3j? = null
    private var credentials: Credentials? = null

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
            val walletPath = WalletUtils.generateFullNewWalletFile("Your own password", walletPathFile)
            Toast.makeText(this, "Wallet created successfully.", Toast.LENGTH_SHORT).show()
        }

        connectEthereumClientBtn.setOnClickListener {
            val web3ClientVersion = web3!!.web3ClientVersion().sendAsync().get()
            val clientVersion = web3ClientVersion.getWeb3ClientVersion()
            log.info("Connected to Ethereum client version: $clientVersion")
        }

        loadCredentialsBtn.setOnClickListener {
            credentials = WalletUtils.loadCredentials(
                    "Your wallet password",
                    "Your wallet stored path")
            log.info("Credentials loaded Address is::: " + credentials!!.getAddress())
        }

        transferFundsBtn.setOnClickListener {
            var transactionReceipt: TransactionReceipt? = null
            try {
                log.info("Sending 1 Wei ("
                        + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)")

                transactionReceipt = Transfer.sendFunds(
                        web3, credentials, "0x19e03255f667bdfd50a32722df860b1eeaf4d635",
                        BigDecimal.valueOf(1.0), Convert.Unit.ETHER)
                        .send()
                log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/" + transactionReceipt!!.blockHash)
            } catch (e: Exception) {
                e.printStackTrace()
            }

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
