package org.storm.android.ui;

import org.storm.android.config.ConstantValues;
import org.storm.android.widget.MainTabWidget;
import org.storm.android.widget.MainTabWidget.OnTabSelectedListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class MainActivity extends FragmentActivity implements OnTabSelectedListener{

	public static MainActivity instance = null;
	private static final String TAG = "AlfmMain";
	private MainTabWidget mTabWidget;
	private int mIndex = ConstantValues.MAIN_BOTTOM_MSG;
	private FragmentManager mFragmentManager;
	private LinearLayout mClose;
    private LinearLayout mCloseBtn;
    private View layout;	
    private LayoutInflater inflater;
	private HtmlFragment htmlFragment;
	private ListViewFrament listViewFrament;
	private FileFrament fileFrament;

	
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        instance = this;
		init();
		initEvents();
	}
    
	@Override
	public void onTabSelected(int index) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		hideFragments(transaction);
		System.out.println("index::::"+index);
		
		switch (index) {
		case 0:	
			if (null == htmlFragment) {
				htmlFragment = new HtmlFragment();
				transaction.add(R.id.center_layout, htmlFragment);
			} else {
				transaction.show(htmlFragment);
			}
			break;
		case 1:	
			if (null == listViewFrament) {
				listViewFrament = new ListViewFrament();
				transaction.add(R.id.center_layout, listViewFrament);
			} else {
				transaction.show(listViewFrament);
			}
			break;
		case 2:
			if(null == fileFrament){
				fileFrament = new FileFrament();
				transaction.add(R.id.center_layout, fileFrament);
			}else{
				transaction.show(fileFrament);
			}
			default:
			break;
			
		}
		
		mIndex = index;
		transaction.commitAllowingStateLoss();
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		onTabSelected(mIndex);
		mTabWidget.setTabsDisplay(this, mIndex);
		mTabWidget.setIndicateDisplay(this, 3, true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题
		// super.onSaveInstanceState(outState);
		outState.putInt("index", mIndex);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// super.onRestoreInstanceState(savedInstanceState);
		mIndex = savedInstanceState.getInt("index");
	}
	
	private void init() {
		mFragmentManager = getSupportFragmentManager();
		mTabWidget = (MainTabWidget) findViewById(R.id.tab_widget);
	}

	private void initEvents() {
		mTabWidget.setOnTabSelectedListener(this);
	}

	
	private void hideFragments(FragmentTransaction transaction) {
		if (null != htmlFragment) {
			transaction.hide(htmlFragment);
		}
		if (null != listViewFrament) {
			transaction.hide(listViewFrament);
		}
		if (null != fileFrament) {
			transaction.hide(fileFrament);
		}
	}
	
}
