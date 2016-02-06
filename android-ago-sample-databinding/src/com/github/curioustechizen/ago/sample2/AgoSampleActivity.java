package com.github.curioustechizen.ago.sample2;

import java.util.Date;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.curioustechizen.ago.sample2.DummyContent.RowItem;
import com.github.curioustechizen.ago.sample2.databinding.RowItemBinding;

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
            RowItemBinding rib;
            if(convertView == null){
				rib = RowItemBinding.inflate(LayoutInflater.from(getContext()));
			} else {
                rib = DataBindingUtil.findBinding(convertView);
            }

            RowItem item = getItem(position);
			rib.setDate(new Date());

			rib.message.setText(item.info);
			rib.timestamp.setReferenceTime(item.timestamp);
			return rib.getRoot();
		}
	}

}
