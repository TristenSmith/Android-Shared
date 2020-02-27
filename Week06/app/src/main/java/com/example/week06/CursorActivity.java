package com.example.week06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class CursorActivity extends BaseActivity {

    static final String TAG = "CursorActivity";
    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;
    ListView listView;
    DBAdapter adperter;

    ChatReceiver receiver;
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor);

        listView = findViewById(R.id.list_view_cursor);
        dbManager = new DBManager(this);
        database = dbManager.getReadableDatabase();

        receiver = new ChatReceiver();
        filter = new IntentFilter(ChatService.NEW_MESSAGE_RECIVEVED);
    }

    @Override
    protected void onDestroy()
    {
        database.close();
        super.onDestroy();

    }

    @Override
    protected void onResume()
    {
        cursor = database.query(DBManager.TABLE_NAME, null, null, null, null, null, DBManager.C_ID + " DESC");
        startManagingCursor(cursor);

        adperter = new DBAdapter(this, cursor);
        listView.setAdapter(adperter);

        registerReceiver(receiver, filter);

        Log.d(TAG, "in onResume()");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        unregisterReceiver(receiver);
        super.onPause();
    }

    class ChatReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            cursor.requery();
            adperter.notifyDataSetChanged();
            Log.d(TAG, "onReceive" + ChatService.NEW_MESSAGE_RECIVEVED);
        }
    }
}
