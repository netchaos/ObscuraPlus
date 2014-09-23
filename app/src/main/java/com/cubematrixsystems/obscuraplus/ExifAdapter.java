package com.cubematrixsystems.obscuraplus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vimal on 14-09-09.
 */
public class ExifAdapter extends BaseAdapter {
    private final ArrayList exifData;

    ExifAdapter(Map map){
        exifData = new ArrayList();
        exifData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return exifData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int i) {
        return (Map.Entry) exifData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View result;

        if(view == null){

            result = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exif_data_item, viewGroup, false);

        }else{
            result = view;
        }

        Map.Entry<String, String> item = getItem(i);

        ((TextView) result.findViewById(android.R.id.text1)).setText(item.getKey());
        ((TextView) result.findViewById(android.R.id.text2)).setText(item.getValue());

        return result;
    }
}
