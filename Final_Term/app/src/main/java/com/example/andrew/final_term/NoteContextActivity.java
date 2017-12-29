package com.example.andrew.final_term;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Andrew on 2017/12/29.
 */

public class NoteContextActivity extends AppCompatActivity {
        private RecyclerView mRecyclerView;
        private LinearLayoutManager mLayoutManager;
        private MainActivityRecyclerViewAdapter mAdapter;
        public ArrayList<Info> myData;
        //Image tempImage = new Image();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.note_content);
        }

}
