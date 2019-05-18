package com.example.picture;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    float dy=0;
    float uy=0;
    public Image[] image=new Image[10];
    public Image[]images=new Image[3];
    int size=10;
    private SwipeRefreshLayout swipeRefreshLayout;
    public Adapic ada;
    int leng=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add(R.drawable.image3 );
        add(R.drawable.image4);
        add(R.drawable.image2 );
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                inint();
                refresh();
            }
        });
        inint();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view1);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(MainActivity.this, "888", Toast.LENGTH_SHORT).show();
                        dy = event.getY();
                    case MotionEvent.ACTION_UP:
                        uy = event.getY();
                }
                if (dy != 0 && uy != 0) {
                    if ((uy - dy) > 0) {
                        Image[] image2 = new Image[size + 10];
                        for (int i = 0; i < size; i++) {
                            image2[i] = image[i];
                        }
                        for (int i = 0; i < 10; i++) {
                            image2[size + i] = new Image(R.drawable.image2);
                        }
                        ada = new Adapic(image2);
                        refresh();


                    }
                }
                return true;

            }
        });


        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ada=new Adapic(image);
        recyclerView.setAdapter(ada);
    }


    public void add(int imageid){
        Image image1=new Image(imageid);
        images[leng]=image1;
        leng++;
    }
    public void inint(){
        for(int i=0;i<10;i++){
            Random random=new Random();
            int index=random.nextInt(3);
            if (index>0) {
                image[i] = images[index-1];
            }else{
                image[i] = images[index];
            }
        }


    }
    public void refresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ada.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
