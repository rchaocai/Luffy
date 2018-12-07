package com.mmc.lamandys.liba_datapick.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.mmc.lamandys.liba_datapick.R;
import com.mmc.lamandys.liba_datapick.bean.SecondChildBean;
import com.mmc.lamandys.liba_datapick.bean.SecondGroupBean;

import java.util.ArrayList;


/**
 * Author :xishuang
 * Date :2018-12-05
 * Des :
 */
public class SecondExpandableBaseAdapter extends BaseExpandableListAdapter {

    //定义二个实体类承载数据
    //一为groupitem,一为childitem
    private ArrayList<SecondGroupBean> groupBeans;
    private ArrayList<SecondChildBean> childBeans;
    private Context context;

    //构造方法,将实体类的值传到主函数中
    public SecondExpandableBaseAdapter(ArrayList<SecondGroupBean> groupBeans, ArrayList<SecondChildBean> childBeans, Context context) {
        this.groupBeans = groupBeans;
        this.childBeans = childBeans;
        this.context = context;
    }

    //item的数量   为groupitem的size
    @Override
    public int getGroupCount() {
        return groupBeans.size();
    }

    /**
     * 在ExpandableListView中 继承了BaseExpandableListAdapter之后
     * 会复写是个方法  其中前两个方法的返回值需要填写  其他的方法都不需要更改
     * 其次是 getGroupView和getChildView按照ListView的getView填写
     * 需要两个缓存类
     * 缓存类的通用方法都类似   记住模式
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expandablelistview_group_item, parent, false);
            //切记  此处别忘了缓存类的初始化
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.groupName = (TextView) convertView.findViewById(R.id.group_name);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.groupName.setText(groupBeans.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expandablelistview_child_item, parent, false);
            //切记  此处别忘了缓存类的初始化
            childViewHolder = new ChildViewHolder();
            childViewHolder.childNmae = (TextView) convertView.findViewById(R.id.child_name_second);
            childViewHolder.childSex = (TextView) convertView.findViewById(R.id.child_sex_second);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.childNmae.setText(childBeans.get(childPosition).getName());
        childViewHolder.childSex.setText(childBeans.get(childPosition).getSex());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        TextView groupName;
    }

    class ChildViewHolder {
        TextView childNmae, childSex;
    }


}
