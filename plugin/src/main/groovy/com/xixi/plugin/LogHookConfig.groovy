package com.xixi.plugin

import com.xixi.plugin.bean.LogMethodCell
import jdk.internal.org.objectweb.asm.Opcodes

/**
 * 针对日志采集系统进行专门配置
 */
public class LogHookConfig {

    /**
     * 日志采集埋点入口类
     */
    public static final String LOG_ANALYTICS_BASE = "com/mmc/lamandys/liba_datapick/AutoTrackHelper"


    public final
    static HashMap<String, LogMethodCell> sInterfaceMethods = new HashMap<>()

    static {
        sInterfaceMethods.put('onClick(Landroid/view/View;)V', new LogMethodCell(
                'onClick',
                '(Landroid/view/View;)V',
                'android/view/View$OnClickListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        sInterfaceMethods.put('onCheckedChanged(Landroid/widget/CompoundButton;Z)V', new LogMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/CompoundButton;Z)V',
                'android/widget/CompoundButton$OnCheckedChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        sInterfaceMethods.put('onRatingChanged(Landroid/widget/RatingBar;FZ)V', new LogMethodCell(
                'onRatingChanged',
                '(Landroid/widget/RatingBar;FZ)V',
                'android/widget/RatingBar$OnRatingBarChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        sInterfaceMethods.put('onStopTrackingTouch(Landroid/widget/SeekBar;)V', new LogMethodCell(
                'onStopTrackingTouch',
                '(Landroid/widget/SeekBar;)V',
                'android/widget/SeekBar$OnSeekBarChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        sInterfaceMethods.put('onCheckedChanged(Landroid/widget/RadioGroup;I)V', new LogMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/RadioGroup;I)V',
                'android/widget/RadioGroup$OnCheckedChangeListener',
                'trackRadioGroup',
                '(Landroid/widget/RadioGroup;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        sInterfaceMethods.put('onClick(Landroid/content/DialogInterface;I)V', new LogMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;I)V',
                'android/content/DialogInterface$OnClickListener',
                'trackDialog',
                '(Landroid/content/DialogInterface;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        sInterfaceMethods.put('onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V', new LogMethodCell(
                'onItemClick',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'android/widget/AdapterView$OnItemClickListener',
                'trackListView',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        sInterfaceMethods.put('onItemSelected(Landroid/widget/AdapterView;Landroid/view/View;IJ)V', new LogMethodCell(
                'onItemSelected',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'android/widget/AdapterView$OnItemSelectedListener',
                'trackListView',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        sInterfaceMethods.put('onGroupClick(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z', new LogMethodCell(
                'onGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z',
                'android/widget/ExpandableListView$OnGroupClickListener',
                'trackExpandableListViewOnGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        sInterfaceMethods.put('onChildClick(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z', new LogMethodCell(
                'onChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z',
                'android/widget/ExpandableListView$OnChildClickListener',
                'trackExpandableListViewOnChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;II)V',
                1, 4,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD]))
        sInterfaceMethods.put('onTabChanged(Ljava/lang/String;)V', new LogMethodCell(
                'onTabChanged',
                '(Ljava/lang/String;)V',
                'android/widget/TabHost$OnTabChangeListener',
                'trackTabHost',
                '(Ljava/lang/String;)V',
                1, 1,
                [Opcodes.ALOAD]))

        sInterfaceMethods.put('onNavigationItemSelected(Landroid/view/MenuItem;)Z', new LogMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'android/support/design/widget/NavigationView$OnNavigationItemSelectedListener',
                'trackMenuItem',
                '(Ljava/lang/Object;Landroid/view/MenuItem;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD]))

        sInterfaceMethods.put('onTabSelected(Landroid/support/design/widget/TabLayout$Tab;)V', new LogMethodCell(
                'onTabSelected',
                '(Landroid/support/design/widget/TabLayout$Tab;)V',
                'android/support/design/widget/TabLayout$OnTabSelectedListener',
                'trackTabLayoutSelected',
                '(Ljava/lang/Object;Ljava/lang/Object;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD]))

//        sInterfaceMethods.put('onPageSelected(I)V', new LogMethodCell(
//                'onPageSelected',
//                '(I)V',
//                'android/support/v4/view/ViewPager$OnPageChangeListener',
//                'trackViewPageSelected',
//                '(Ljava/lang/Object;I)V',
//                0, 2,
//                [Opcodes.ALOAD, Opcodes.ALOAD]))

        // Todo: 扩展
    }

    /**
     * Fragment中的方法
     */
    public final
    static HashMap<String, LogMethodCell> sFragmentMethods = new HashMap<>()
    static {
        sFragmentMethods.put('onResume()V', new LogMethodCell(
                'onResume',
                '()V',
                '',// parent省略，均为 android/app/Fragment 或 android/support/v4/app/Fragment
                'trackFragmentResume',
                '(Ljava/lang/Object;)V',
                0, 1,
                [Opcodes.ALOAD]))
        sFragmentMethods.put('setUserVisibleHint(Z)V', new LogMethodCell(
                'setUserVisibleHint',
                '(Z)V',
                '',// parent省略，均为 android/app/Fragment 或 android/support/v4/app/Fragment
                'trackFragmentSetUserVisibleHint',
                '(Ljava/lang/Object;Z)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        sFragmentMethods.put('onHiddenChanged(Z)V', new LogMethodCell(
                'onHiddenChanged',
                '(Z)V',
                '',
                'trackOnHiddenChanged',
                '(Ljava/lang/Object;Z)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        sFragmentMethods.put('onViewCreated(Landroid/view/View;Landroid/os/Bundle;)V', new LogMethodCell(
                'onViewCreated',
                '(Landroid/view/View;Landroid/os/Bundle;)V',
                '',
                'onFragmentViewCreated',
                '(Ljava/lang/Object;Landroid/view/View;Landroid/os/Bundle;)V',
                0, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ALOAD]))
        sFragmentMethods.put('onDestroy()V', new LogMethodCell(
                'onDestroy',
                '()V',
                '',// parent省略，均为 android/app/Fragment 或 android/support/v4/app/Fragment
                'trackFragmentDestroy',
                '(Ljava/lang/Object;)V',
                0, 1,
                [Opcodes.ALOAD]))
    }

    /**
     * android.gradle 3.2.1 版本中，针对 Lambda 表达式处理
     */
    public final
    static HashMap<String, LogMethodCell> sLambdaMethods = new HashMap<>()

    static {
        sLambdaMethods.put('(Landroid/view/View;)V', new LogMethodCell(
                'onClick',
                '(Landroid/view/View;)V',
                'android/view/View$OnClickListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        sLambdaMethods.put('(Landroid/widget/CompoundButton;Z)V', new LogMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/CompoundButton;Z)V',
                'android/widget/CompoundButton$OnCheckedChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        sLambdaMethods.put('(Landroid/widget/RatingBar;FZ)V', new LogMethodCell(
                'onRatingChanged',
                '(Landroid/widget/RatingBar;FZ)V',
                'android/widget/RatingBar$OnRatingBarChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        sLambdaMethods.put('(Landroid/widget/SeekBar;)V', new LogMethodCell(
                'onStopTrackingTouch',
                '(Landroid/widget/SeekBar;)V',
                'android/widget/SeekBar$OnSeekBarChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        sLambdaMethods.put('(Landroid/widget/RadioGroup;I)V', new LogMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/RadioGroup;I)V',
                'android/widget/RadioGroup$OnCheckedChangeListener',
                'trackRadioGroup',
                '(Landroid/widget/RadioGroup;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        sLambdaMethods.put('(Landroid/content/DialogInterface;I)V', new LogMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;I)V',
                'android/content/DialogInterface$OnClickListener',
                'trackDialog',
                '(Landroid/content/DialogInterface;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        sLambdaMethods.put('(Landroid/widget/AdapterView;Landroid/view/View;IJ)V', new LogMethodCell(
                'onItemClick',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'android/widget/AdapterView$OnItemClickListener',
                'trackListView',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        sLambdaMethods.put('(Landroid/widget/AdapterView;Landroid/view/View;IJ)V', new LogMethodCell(
                'onItemSelected',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'android/widget/AdapterView$OnItemSelectedListener',
                'trackListView',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        sLambdaMethods.put('(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z', new LogMethodCell(
                'onGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z',
                'android/widget/ExpandableListView$OnGroupClickListener',
                'trackExpandableListViewOnGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        sLambdaMethods.put('(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z', new LogMethodCell(
                'onChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z',
                'android/widget/ExpandableListView$OnChildClickListener',
                'trackExpandableListViewOnChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;II)V',
                1, 4,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD]))
        sLambdaMethods.put('(Ljava/lang/String;)V', new LogMethodCell(
                'onTabChanged',
                '(Ljava/lang/String;)V',
                'android/widget/TabHost$OnTabChangeListener',
                'trackTabHost',
                '(Ljava/lang/String;)V',
                1, 1,
                [Opcodes.ALOAD]))

        sLambdaMethods.put('(Landroid/view/MenuItem;)Z', new LogMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'android/support/design/widget/NavigationView$OnNavigationItemSelectedListener',
                'trackMenuItem',
                '(Ljava/lang/Object;Landroid/view/MenuItem;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD]))

        sLambdaMethods.put('(Landroid/support/design/widget/TabLayout$Tab;)V', new LogMethodCell(
                'onTabSelected',
                '(Landroid/support/design/widget/TabLayout$Tab;)V',
                'android/support/design/widget/TabLayout$OnTabSelectedListener',
                'trackTabLayoutSelected',
                '(Ljava/lang/Object;Ljava/lang/Object;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD]))

        // Todo: 扩展
    }
}