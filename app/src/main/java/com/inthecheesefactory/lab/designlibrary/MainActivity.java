package com.inthecheesefactory.lab.designlibrary;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	Toolbar toolbar;
	CollapsingToolbarLayout collapsingToolbarLayout;


	CoordinatorLayout rootLayout;
	private View headerView;

	private SearchViewLayout mSearchViewLayout;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		headerView = findViewById(R.id.header);
		mSearchViewLayout = (SearchViewLayout) findViewById(R.id.search_expanded_root);
		initToolbar();
		initInstances();

		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new ViewPagerFragment())
				.commitAllowingStateLoss();
		getSupportFragmentManager().executePendingTransactions();

	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	private void initInstances() {

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);


		collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
		collapsingToolbarLayout.setTitle("Design Library");
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	public void pushFragmentToActivityFragmentManager(Fragment fragment) {
		if (fragment == null) {
			return;
		}
		final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).
				replace(R.id.detail, fragment).addToBackStack(null);
		ft.commitAllowingStateLoss();
		getSupportFragmentManager().executePendingTransactions();
	}

	public void showHeaderView(boolean show) {
		if (show) {
			headerView.setVisibility(View.VISIBLE);
		} else {
			headerView.setVisibility(View.GONE);
		}
	}

	public void showSearchViewLayout(boolean show){
		if (show) {
			mSearchViewLayout.setVisibility(View.VISIBLE);
		} else {
			mSearchViewLayout.setVisibility(View.GONE);
		}
	}
}
