package ko.hshun.mysololife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import ko.hshun.mysololife.R
import ko.hshun.mysololife.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)

        val webView = binding.storeWebView
        webView.loadUrl("https://www.mangoplate.com/")


        return binding.root
    }
}