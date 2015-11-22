package org.storm.android.ui;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * 首页 设置 fragment
 * @author dewyze
 *
 */
@SuppressLint("SetJavaScriptEnabled")
public class HtmlFragment extends BaseFragment implements OnClickListener {

	private static final String TAG = "HtmlFragment";
	private WebView webView;
	private TextView mTitleTv;

	public static HtmlFragment newInstance() {
		HtmlFragment settingFragment = new HtmlFragment();

		return settingFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	
	}

	@Override
    /**
     * Initialize the {@link CordovaWebView} and load its start URL.
     *
     * The fragment inflator needs to be cloned first to use an instance of {@link CordovaContext} instead.  This
     * alternate context object implements the {@link CordovaInterface} as well and acts as a proxy between the activity
     * and fragment for the {@link CordovaWebView}.  The actual {@link CordovaWebView} is defined in the home_view_frag.xml layout
     * and has an id of <b>aemWebView</b>.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_html, container, false);
        return rootView;
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
		initEvents();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void initViews(View view) {
		mTitleTv = (TextView) view.findViewById(R.id.title_tv);
		mTitleTv.setText("这是一个hTML");
		webView = (WebView) view.findViewById(R.id.webview);
		webView.loadUrl("http://m.baidu.com");
		webView.setWebViewClient(new WebViewClient(){
			
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url)
	        {
	        	view.loadUrl(url);   
	            return true;
	        }
		});
	}
	
	private void initEvents() {

	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	@Override
	public void onClick(View v) {

	}
}
