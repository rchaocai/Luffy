package com.mmc.lamandys.liba_datapick.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmc.lamandys.liba_datapick.R;
import com.mmc.lamandys.liba_datapick.dummy.DummyContent;

import java.util.List;


public class ListItemFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private static final String ARG_CONTENT = "content";
    private String mContent;
    private Context context;

    public ListItemFragment() {
    }

    public static ListItemFragment newInstance(String content) {
        ListItemFragment fragment = new ListItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mContent = getArguments().getString(ARG_CONTENT);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Context context = view.getContext();
        Button button = (Button) view.findViewById(R.id.item_button);
        button.setText(mContent);
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(new ListAdapter(getActivity(), DummyContent.ITEMS));
        listView.setOnItemClickListener(this);
        listView.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(context, "单独点击" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(context, "选择" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class ListAdapter extends BaseAdapter {
        private List<DummyContent.DummyItem> dummyItemLists;
        private Context context;

        public ListAdapter(Context context, List<DummyContent.DummyItem> dummyItemLists) {
            this.dummyItemLists = dummyItemLists;
            this.context = context;
        }

        @Override
        public int getCount() {
            //返回集合的数量
            return dummyItemLists.size();
        }

        @Override
        public Object getItem(int position) {
            //获取当条数据
            return dummyItemLists.get(position);
            //return null;
        }

        @Override
        public long getItemId(int position) {
            //获取当前ID
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                //并没有加载View
                convertView = LayoutInflater.from(context).inflate(R.layout.fragment_item, null);
                holder = new ViewHolder();
                holder.numberTv = (TextView) convertView.findViewById(R.id.id);
                holder.contentTv = (TextView) convertView.findViewById(R.id.content);

                holder.numberTv.setText(dummyItemLists.get(position).id);
                holder.contentTv.setText(dummyItemLists.get(position).content);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.numberTv.setText(dummyItemLists.get(position).id);
                holder.contentTv.setText(dummyItemLists.get(position).content);
            }
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "单独点击", Toast.LENGTH_SHORT).show();
//                }
//            });
            return convertView;
        }

    }

    private static class ViewHolder {
        View rootView;
        TextView numberTv;
        TextView contentTv;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
