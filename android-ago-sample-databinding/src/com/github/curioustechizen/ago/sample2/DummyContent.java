package com.github.curioustechizen.ago.sample2;

import android.databinding.DataBindingUtil;

import com.github.curioustechizen.ago.sample2.databinding.RowItemBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DummyContent {
	
	private static final long NOW = new Date().getTime();
	
	public static class RowItem{
		public String info;
		public long timestamp;
		
		RowItem(String info, long timestamp){
			this.info = info;
			this.timestamp = timestamp;
		}

		RowItem(String info){
			this.info = info;
		}
	}
	
	public static final List<RowItem> DUMMY_ITEMS = new ArrayList<DummyContent.RowItem>(4);
	
	static {
		DUMMY_ITEMS.add(new RowItem("Data-bound message"));
	}

}
