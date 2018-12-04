/**Created by wangzhuozhou on 2015/08/01.
 * Copyright © 2015－2018 Sensors Data Inc. All rights reserved. */
 
package com.mmc.lamandys.liba_datapick;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import java.util.HashMap;

public class AutoTrackHelper {
    private static HashMap<Integer, Long> eventTimestamp = new HashMap<>();

    private static boolean isDeBounceTrack(Object object) {
        boolean isDeBounceTrack = false;
        long currentOnClickTimestamp = System.currentTimeMillis();
        Object targetObject = eventTimestamp.get(object.hashCode());
        if (targetObject != null) {
            long lastOnClickTimestamp = (long) targetObject;
            if ((currentOnClickTimestamp - lastOnClickTimestamp) < 500) {
                isDeBounceTrack = true;
            }
        }

        eventTimestamp.put(object.hashCode(), currentOnClickTimestamp);
        return isDeBounceTrack;
    }

    private static void traverseView(String fragmentName, ViewGroup root) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onFragmentViewCreated(Object object, View rootView, Bundle bundle) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackRN(Object target, int reactTag, int s, boolean b) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void trackFragmentAppViewScreen(android.support.v4.app.Fragment fragment) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackFragmentResume(Object object) {

    }

    public static void trackFragmentSetUserVisibleHint(Object object, boolean isVisibleToUser) {

    }

    public static void trackOnHiddenChanged(Object object, boolean hidden) {

    }

    public static void trackExpandableListViewOnGroupClick(ExpandableListView expandableListView, View view,
                                                           int groupPosition) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackExpandableListViewOnChildClick(ExpandableListView expandableListView, View view,
                                                           int groupPosition, int childPosition) {
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackTabHost(String tabName) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackTabLayoutSelected(Object object, Object tab) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackMenuItem(Object object, MenuItem menuItem) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackRadioGroup(RadioGroup view, int checkedId) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackDialog(DialogInterface dialogInterface, int whichButton) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackListView(AdapterView<?> adapterView, View view, int position) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackDrawerOpened(View view) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackDrawerClosed(View view) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackViewOnClick(View view) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackViewOnClick(Object anything) {
        try {
            if (anything == null) {
                return;
            }

            if (!(anything instanceof View)) {
                return;
            }

            trackViewOnClick((View) anything);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
