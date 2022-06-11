package kr.icclab.kyptowallet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.response.EthAccounts
import org.web3j.protocol.core.methods.response.EthBlockNumber
import org.web3j.protocol.core.methods.response.EthGetBalance
import org.web3j.protocol.core.methods.response.EthGetTransactionCount
import org.web3j.utils.Convert
import java.math.BigInteger


class MainActivity : AppCompatActivity() {
//    val okHttpClient = OkHttpClient.Builder()
//                        .connectTimeout(100, TimeUnit.SECONDS)
//                        .readTimeout(100, TimeUnit.SECONDS)
//                        .writeTimeout(100, TimeUnit.SECONDS)
//                        .build()

    val temp_PRIKey: String = "7179ab4acf2f0b3511beb1be1137587e"
    val temp_PUBKey: String =
        "ec4514c866471a5d1ab5e240f7469344aa811236f708b1bbf732060b02677082f2c41d7990960e346e58d664b4cce456b72c8bd3725a612751c285ffce4c27a8"

    var web3j: Web3j? = null
    var walletJson: JSONObject? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        walletJson = MyApp.prefs.getJson("wallet", JSONObject("{}"))
//
        var getB = getEthBalance(walletJson!!.get("address").toString())

        if (getB != null) {
            val wei: BigInteger = getB!!.balance
            val tokenValue = Convert.fromWei(wei.toString(), Convert.Unit.ETHER)
            val strTokenAmount = tokenValue.toString()

            walletBalTextView.text = strTokenAmount + " ETH"
        }

//        Util.Util.CopyClipboard(applicationContext,walletJson!!.get("address").toString())

        outButton.setOnClickListener{
            MyApp.prefs.clear()

            val i = baseContext.packageManager
                .getLaunchIntentForPackage(baseContext.packageName)
            i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
            startActivity(i)
        }

//        sendButton.setOnClickListener{
//        }

//        WalletUtils.gener
//        web3j.ethAccounts().jsonr
//        sendBalance.setOnClickListener {
////            logRender(getGas()?.gasPrice.toString())
//            var crend : Credentials = Credentials.create(temp_PRIKey,temp_PUBKey)
//            sendEth(crend,address_EditText.text.toString(),0.3,0.0f)
//
//
//
//        }

//        val ethGasPrice = web3j.ethGasPrice().sendAsync().get()
//        textView.text = ethGasPrice.gasPrice.toString()


        //create(String password, ECKeyPair ecKeyPair, int n, int p)
        //Wallet Create
//        val password = "password"
//        createWallet_Button.setOnClickListener {
//            val mnemonicCode: MnemonicCode = MnemonicCode(Mnemonics.WordCount.COUNT_12)
//            val seed: ByteArray = mnemonicCode.toEntropy()
//            for (word in mnemonicCode) {
//                logRender(word.toString()+"\n")
//            }
//            val eck: ECKeyPair = ECKeyPair.create(seed)
//
//
////            val createWallet = Wallet.createLight(password,eck)
////            val walletFile : WalletFile = Wallet.createLight(password,eck)
//            Log.e("JSON",process(eck,password).toString())
////            val creden : Credentials = Credentials.create(eck)
////            val k = ECKeyPair.create(BigInteger(creden.ecKeyPair.privateKey.toString()))
////            k.privateKey.toString()
////            val test = Credentials.create(k)
//
////
////            Log.e("Wallet",k.privateKey.toString());
////            Log.e("Wallet",k.privateKey.toString(16));
//
//
////            logRender(createWallet.address.toString())
//
////            logRender(createWallet.address.toString())
////            logRender(eck.privateKey.toString(16))
////            logRender(eck.publicKey.toString(16))
////
////            Log.e("Wallet",createWallet.address.toString());
////            Log.e("Wallet",eck.privateKey.toString(16));
//
////            logRender(creden.ecKeyPair.publicKey.toString())
//
////            Log.d("Key",createWallet.address.toString())
////            Log.d("CredenKey",creden.address.toString())
////            Log.d("CredenPrivateKey",creden.ecKeyPair.privateKey.toString(16))
////            Log.d("CredenPublicKey",creden.ecKeyPair.publicKey.toString(16))
////            Log.d("HashKey",hashCode().toString() )
//        //            logRender(eck.privateKey.toString())
////            val create = Wallet.create(password,eck ,2,1)
////            logRender(create.address.toString())
////            ECKeyPair.create(ByteArray)
//        }
//
//
//
//        //MnemonicCode Start
//
//
//


    }


//        fun sendEth(
//            credentials: Credentials,
//            withKey: String,
//            withEth: Double,
//            gas: Float
//        ): RemoteCall<TransactionReceipt>? {
////        WalletUtils.
//            val result = Transfer.sendFunds(
//                web3j,
//                credentials,
//                withKey,
//                BigDecimal.valueOf(withEth),
//                Convert.Unit.ETHER
//            )
//            result.sendAsync()
//            return result
//        }

//        fun getGas(): EthGasPrice? {
//            val result = EthGasPrice()
//            web3j!!.ethGasPrice().sendAsync().get()
//            return result
//        }

        fun getEthBalance(str: String): EthGetBalance? {
            val result = MyApp.web3j.ethGetBalance(
                str,
                DefaultBlockParameter.valueOf("latest")
            )
                .sendAsync()
                .get()

//        val balance: BigInteger = result.balance


            Log.d("balance", result.balance.toString())

            return result
        }

        fun getTransactionCount(str: String): EthGetTransactionCount? {
            var result: EthGetTransactionCount? = EthGetTransactionCount()
            result = web3j!!.ethGetTransactionCount(
                str.toString(),
                DefaultBlockParameter.valueOf("latest")
            )
                .sendAsync()
                .get()
            return result
        }

        fun getBlockNumber(): EthBlockNumber {
            var result: EthBlockNumber = EthBlockNumber()
            result = web3j!!.ethBlockNumber()
                .sendAsync()
                .get()
            return result
        }

        fun getEthAccounts(): EthAccounts? {

            var result: EthAccounts? = EthAccounts()
            result = web3j!!.ethAccounts()
                .sendAsync()
                .get()
            return result
        }

        fun logRender(str: String) {
//        log_TextView.append(str)
        }
    }


