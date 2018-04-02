package com.github.curioustechizen.ago.sample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class DummyContent {
	
	private static final long NOW = new Date().getTime();
	
	static class RowItem{
		String info;
		long timestamp;
		
		RowItem(String info, long timestamp){
			this.info = info;
			this.timestamp = timestamp;
		}
	}
	
	static final List<RowItem> DUMMY_ITEMS = new ArrayList<>(4);
	
	static {
		DUMMY_ITEMS.add(new RowItem("A message", NOW));
		DUMMY_ITEMS.add(new RowItem("Another message", NOW - (15 * 60 * 1000)));
		DUMMY_ITEMS.add(new RowItem("Not-so-recent message", NOW - (24 * 60 * 60 * 1000)));
		DUMMY_ITEMS.add(new RowItem("Very old message", NOW - (8 * 24 * 60 * 60 * 1000)));
		DUMMY_ITEMS.add(new RowItem("Near future message", NOW + (4 * 60 * 1000)));
		DUMMY_ITEMS.add(new RowItem("Another future message", NOW + (25 * 60 * 60 * 1000)));
	}

}
