package wellijohn.org.scrolldelrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import wellijohn.org.scrolldelrecyclerview.adapter.RVSwipeAdapter;
import wellijohn.org.scrolldelrecyclerview.delrecyclerview.DelRecyclerView;

public class MainActivity extends AppCompatActivity {

    private DelRecyclerView mDrv;

    private List<Person> mDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        for (int i = 0; i < 25; i++) {
            Person person = new Person();
            mDatas.add(person);
        }

        RVSwipeAdapter rvSwipeAdapter = new RVSwipeAdapter();
        mDrv.setLayoutManager(new LinearLayoutManager(this));
        mDrv.setAdapter(rvSwipeAdapter);
        rvSwipeAdapter.setDatas(mDatas);
    }

    private void initView() {
        mDrv = findViewById(R.id.drv);
    }
}
