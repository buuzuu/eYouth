package com.example.hritik.e_youth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Portfolio extends Fragment {


    View view;
    WebView webView ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.port_folio,container,false);
        webView = view.findViewById(R.id.webview);
        webView.loadUrl("http://youthelancer.com/portfolio/");
       // webView.loadUrl("http://www.google.com");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().getJavaScriptCanOpenWindowsAutomatically();
        webView.clearCache(false);
        WebViewClient webViewClient =new WebViewClient();


        webView.setWebViewClient(webViewClient);

        return view;
    }

}
