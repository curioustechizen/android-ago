package com.github.curioustechizen.ago.sample;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
		DummyAdapter(Context context, List<RowItem> items){
			super(context, R.layout.row_item, items);
		}
		
		@NonNull
		@Override
		public View getView(int position, View convertView, @NonNull ViewGroup parent) {
			if(convertView == null){
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
			}
			RowItem item = getItem(position);
			TextView tvMessage = convertView.findViewById(R.id.message);
			RelativeTimeTextView tvDefaultRttv = convertView.findViewById(R.id.defaultrttv);
			RelativeTimeTextView tvPrefixRttv = convertView.findViewById(R.id.prefixrttv);
			RelativeTimeTextView tvCustomRttv = convertView.findViewById(R.id.customrttv);

			tvMessage.setText(item.info);
			tvDefaultRttv.setReferenceTime(item.timestamp);
			tvPrefixRttv.setReferenceTime(item.timestamp);
			tvCustomRttv.setReferenceTime(item.timestamp);
			return convertView;
		}
	}

}
