package com.mmc.lamandys.liba_datapick.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.mmc.lamandys.liba_datapick.R;
import com.mmc.lamandys.liba_datapick.adapter.SecondExpandableBaseAdapter;
import com.mmc.lamandys.liba_datapick.bean.SecondChildBean;
import com.mmc.lamandys.liba_datapick.bean.SecondGroupBean;

import java.util.ArrayList;


public class ExpendListviewFragment extends Fragment implements ExpandableListView.OnGroupClickListener, AdapterView.OnItemLongClickListener, ExpandableListView.OnChildClickListener {


    private static final String ARG_CONTENT = "content";
    private Context context;
    private ArrayList<SecondGroupBean> groupBeans;
    private ArrayList<SecondChildBean> childBeans;
    private ExpandableListView mExpandableListView;
    private SecondExpandableBaseAdapter mSecondExpandableBaseAdapter;
    private String mContent;

    public static ExpendListviewFragment newInstance(String content) {
        ExpendListviewFragment fragment = new ExpendListviewFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mExpandableListView = (ExpandableListView) getActivity().findViewById(R.id.expandablelistview_second);
        groupBeans = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            groupBeans.add(new SecondGroupBean("我是第" + i + "老爹"));
        }
        childBeans = new ArrayList<>();
        for (int j = 0; j < 30; j++) {
            childBeans.add(new SecondChildBean("我是第" + j + "儿子", "我是第" + j + "小儿子"));
        }
        mSecondExpandableBaseAdapter = new SecondExpandableBaseAdapter(groupBeans, childBeans, context);
        mExpandableListView.setAdapter(mSecondExpandableBaseAdapter);

        //guroup组添加监听
        mExpandableListView.setOnGroupClickListener(this);
        //长按监听
        mExpandableListView.setOnItemLongClickListener(this);
        //子项添加监听
        mExpandableListView.setOnChildClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expendlistview, null);
        Button button = (Button) view.findViewById(R.id.item_button);
        button.setText(mContent);
        return view;
    }


    ////返回false则子项可以打开,返回true可以获取焦点,但是子项不能打开
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        Toast.makeText(context, "group" + groupPosition, Toast.LENGTH_SHORT).show();
        return false;
    }


    //长按监听事件中,返回true为正确  若同时有短按监听和长按监听,返回false则都会相应
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Toast.makeText(context, "child" + childPosition, Toast.LENGTH_SHORT).show();

        return true;
    }
}
