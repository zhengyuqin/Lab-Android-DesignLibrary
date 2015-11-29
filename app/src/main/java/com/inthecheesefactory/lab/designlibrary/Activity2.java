package com.inthecheesefactory.lab.designlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @author zyq 15-11-29
 */
public class Activity2 extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);

		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new ScrollableFragment())
				.commitAllowingStateLoss();
		getSupportFragmentManager().executePendingTransactions();
	}
}
