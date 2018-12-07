package com.mmc.lamandys.liba_datapick.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mmc.lamandys.liba_datapick.R;


/**
 * 测试ToolBar，RadioGroup和CompoundButton
 */
public class ToolBarActivity extends AppCompatActivity {

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//        mToolbar.setTitle("大马哈");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView == null) {
            return true;
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(ToolBarActivity.this, "搜索中。。。", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.action_search:
                Toast.makeText(this, "点击了搜素", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_notifi:
                Toast.makeText(this, "点击了提示", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_menu_item0:
                Toast.makeText(this, "点击了item0", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_menu_item1:
                Toast.makeText(this, "点击了item1", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
