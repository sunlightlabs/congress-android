package com.sunlightlabs.android.congress;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.sunlightlabs.android.congress.fragments.FloorUpdateFragment;
import com.sunlightlabs.android.congress.utils.Analytics;
import com.sunlightlabs.android.congress.utils.TitlePageAdapter;
import com.sunlightlabs.android.congress.utils.Utils;

public class FloorUpdatePager extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pager_titled);
			
		Analytics.track(this, "/floor_updates");
		
		Utils.setTitle(this, R.string.floor_updates_title);
		setupPager();
	}

	private void setupPager() {
		TitlePageAdapter adapter = new TitlePageAdapter(this);
		adapter.add("house", R.string.tab_house, FloorUpdateFragment.forChamber("house"));
		adapter.add("senate", R.string.tab_senate, FloorUpdateFragment.forChamber("senate"));
		
		String chamber = getIntent().getStringExtra("chamber");
		if (chamber != null && chamber.equals("senate"))
			adapter.selectPage(1);
	}
}