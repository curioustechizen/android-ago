package com.github.curioustechizen.ago.sample;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.github.curioustechizen.ago.sample.DummyContent.RowItem;

public class AgoSampleActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(savedInstanceState == null){
			DummyAdapter adapter = new DummyAdapter(this, DummyContent.DUMMY_ITEMS);
			setListAdapter(adapter);
		}
	}

	private static class DummyAdapter extends ArrayAdapter<RowItem>{
		public DummyAdapter(Context context, List<RowItem> items){
			super(context, R.layout.row_item, items);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
			}
			RowItem item = getItem(position);
			TextView tvMessage = (TextView) convertView.findViewById(R.id.message);
			RelativeTimeTextView tvTimestamp = (RelativeTimeTextView) convertView.findViewById(R.id.timestamp);
			
			tvMessage.setText(item.info);
			tvTimestamp.setReferenceTime(item.timestamp);
			return convertView;
		}
	}

}
