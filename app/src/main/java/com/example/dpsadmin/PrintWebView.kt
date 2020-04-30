package com.example.dpsadmin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class PrintWebView : AppCompatActivity() {
    private var mWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_web_view)
        webView()

    }
    fun webView() {
        val webView = WebView(this)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ) = false

            override fun onPageFinished(view: WebView?, url: String?) {
                createWebPrintJob(view!!)
                mWebView = null

            }
        }
        // Generate an HTML document on the fly:
        val htmlDocument = HtmlString.pdfStr
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null)
        mWebView = webView
    }
    private fun createWebPrintJob(webView: WebView) {

        (this.getSystemService(Context.PRINT_SERVICE) as? PrintManager)?.let { printManager ->

            val jobName = "${getString(R.string.app_name)} Document"

            val printAdapter = webView.createPrintDocumentAdapter(jobName)

            printManager.print(
                jobName,
                printAdapter,
                PrintAttributes.Builder().build()
            ).also { printJob ->

                if (printJob.isCompleted){
                    finish()
                }else if (printJob.isCancelled){
                    finish()
                }else if (printJob.isFailed){
                    finish()
                }
            }
        }
    }
}
