package com.wechat.testdemo;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.lsjwzh.test.AutoScrollHandler;
import com.lsjwzh.test.StaticLayoutManager;
import com.lsjwzh.test.Util;
import com.lsjwzh.widget.text.FastTextLayoutView;

public class StaticLayoutCacheTestFragment extends Fragment {

  private static final String TAG = "StaticLayoutUI";

  private ListView listview;

  private StaticListAdapter adapter;

  private AutoScrollHandler autoScrollHandler;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View viewRoot = inflater.inflate(R.layout.static_layout_ui, container, false);
    listview = (ListView) viewRoot.findViewById(R.id.test_list);

    adapter = new StaticListAdapter(getActivity());

    listview.setAdapter(adapter);

    autoScrollHandler = new AutoScrollHandler(listview, Util.TEST_LIST_ITEM_COUNT);

    viewRoot.findViewById(R.id.scroll_down_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        com.lsjwzh.test.FastTextLayoutView.TEST_STATS.reset();
        autoScrollHandler.startAutoScrollDown(new AutoScrollHandler.Callback() {
          @Override
          public void callback(int avgFps) {
            Toast.makeText(getActivity(), "Average FPS: " + avgFps, Toast.LENGTH_LONG).show();
            Log.e("drawFps", "FastTextLayoutView.avgFps" + avgFps);
            Log.e("bindCost", "bindCost" + adapter.bindCost);
            Log.e("FastTextLayoutViewStats", com.lsjwzh.test.FastTextLayoutView.TEST_STATS
                .toString());
          }
        });
      }
    });

    viewRoot.findViewById(R.id.scroll_up_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        com.lsjwzh.test.FastTextLayoutView.TEST_STATS.reset();
        autoScrollHandler.startAutoScrollUp(new AutoScrollHandler.Callback() {
          @Override
          public void callback(int avgFps) {
            Toast.makeText(getActivity(), "Average FPS: " + avgFps, Toast.LENGTH_LONG).show();
            Log.e("drawFps", "FastTextLayoutView.avgFps" + avgFps);
            Log.e("bindCost", "bindCost" + adapter.bindCost);
            Log.e("FastTextLayoutViewStats", com.lsjwzh.test.FastTextLayoutView.TEST_STATS
                .toString());
          }
        });
      }
    });

    return viewRoot;
  }

  private static class StaticListAdapter extends TestListAdapter {

    private StaticListAdapter(Context context) {
      super(context);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.static_list_item, parent,
            false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.staticLayoutView = (FastTextLayoutView) convertView.findViewById(R.id
            .static_layout_view);

        convertView.setTag(viewHolder);
      }

      ViewHolder holder = (ViewHolder) convertView.getTag();
      holder.staticLayoutView.setTextLayout(StaticLayoutManager.getInstance().getLayout(position));
      holder.staticLayoutView.requestLayout();
      return convertView;
    }

    private class ViewHolder {
      FastTextLayoutView staticLayoutView;
    }
  }
}
