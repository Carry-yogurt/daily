package com.example.daliy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CostAdapter extends BaseAdapter {

    String TAG="CostAdapter";
    private List<CostBean> mlist;
    private LayoutInflater minflater;
    private Context mcontext;

    public CostAdapter(Context context, List<CostBean> list) {
        mlist = list;
        mcontext = context;
        minflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d(TAG, "getItem: "+mlist.get(position));
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = minflater.inflate(R.layout.list_item, null);
            viewHolder.mTvcostTitle = convertView.findViewById(R.id.tv_costTitle);
            viewHolder.mTvcostMoney = convertView.findViewById(R.id.tv_costMoney);
            viewHolder.mTvcostDate = convertView.findViewById(R.id.tv_costDate);
            viewHolder.mTvcostType = convertView.findViewById(R.id.tv_costType);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CostBean bean = mlist.get(position);
        viewHolder.mTvcostTitle.setText(bean.costTitle);
        viewHolder.mTvcostMoney.setText(bean.costMoney);
        viewHolder.mTvcostDate.setText(bean.costDate);
        viewHolder.mTvcostType.setText(bean.costType);

        return convertView;
    }

    private static class ViewHolder {
        public TextView mTvcostTitle;
        public TextView mTvcostDate;
        public TextView mTvcostMoney;
        public TextView mTvcostType;
    }


}
