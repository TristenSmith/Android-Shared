package com.example.week06;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity
{

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        MenuItem toggleItem = null;

        if(menu != null)
        {
            toggleItem = menu.findItem(R.id.menu_item_toggle_service);
            if(ChatService.bRun == false)
            {
                toggleItem.setTitle(getResources().getString(R.string.menu_item_start_service));
            }else{
                toggleItem.setTitle(getResources().getString(R.string.menu_item_stop_service));
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_item_toggle_service:
            {
                if(ChatService.bRun == false){
                    startService(new Intent(this, ChatService.class) );
                    Log.d(TAG, "starting service");
                }else{
                    stopService(new Intent(this, ChatService.class));
                    Log.d(TAG, "stopping service");
                }
                break;
            }
            case R.id.menu_item_display_chatter:
            {
                startActivity(new Intent(this, DisplayActivity.class) );
                Log.d(TAG, "Display Chatter");
                break;
            }
            case R.id.menu_item_view_preferences:
            {
                startActivity(new Intent(this, PrefsActivity.class));
                Log.d(TAG, "prefs");
                break;
            }
            case R.id.menu_item_view_cursor:
            {
                startActivity(new Intent(this, CursorActivity.class));
                Log.d(TAG,"view cursor");
                break;
            }
            case R.id.menu_item_home:
            {
                startActivity(new Intent(this, MainActivity.class));
                Log.d(TAG, "home");
                break;
            }
            case R.id.menu_item_idk:
            {
                startActivity(new Intent(this, IdkActivity.class));
                Log.d(TAG, "idk");
                break;
            }
        }
        return true;
    }
}
