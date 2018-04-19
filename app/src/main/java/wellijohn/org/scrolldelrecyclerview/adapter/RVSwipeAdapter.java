package wellijohn.org.scrolldelrecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import wellijohn.org.scrolldelrecyclerview.Person;
import wellijohn.org.scrolldelrecyclerview.R;
import wellijohn.org.swipevg.SwipeLayout;
import wellijohn.org.swipevg.inter.OnSwipeStateChangeListener;


/**
 * @author: JiangWeiwei
 * @time: 2018/3/30-9:06
 * @email: wellijohn1991@gmail.com
 * @desc:
 */
public class RVSwipeAdapter extends RecyclerView.Adapter<RVSwipeAdapter.ViewHolder> {

    private List<Person> mDatas;


    /**
     * @param photoUrl photoUrl是一系列的url，中间用，号隔开的
     */
    public RVSwipeAdapter(String photoUrl) {
//        mDatas = StringUtil.convertString2List(photoUrl);
    }

    public RVSwipeAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Person person = mDatas.get(position);
        holder.tvName.setText("貂蝉" + position);
        if (position % 2 == 1) {
            holder.iv.setImageResource(R.drawable.diaochan);
        } else {
            holder.iv.setImageResource(R.drawable.zhenji);
        }

        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了内容布局", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvMenuDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了删除", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了添加", Toast.LENGTH_SHORT).show();
            }
        });

        holder.scrollDelLl.setOpen(person.isOpen());

        holder.scrollDelLl.setOnSwipeStateChangeListener(new OnSwipeStateChangeListener() {
            @Override
            public void onSwipeStateChange(boolean open) {
                person.setOpen(open);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    public void setDatas(List<Person> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMenu;
        TextView tvMenuDelete;
        SwipeLayout scrollDelLl;
        LinearLayout llContent;
        TextView tvName;
        TextView tvAdviseNameList;
        TextView tvTime;
        ImageView iv;
        LinearLayout rightMenuContent;

        ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            tvMenu = itemView.findViewById(R.id.tv_menu_2);
            tvMenuDelete = itemView.findViewById(R.id.tv_menu);
            scrollDelLl = itemView.findViewById(R.id.scroll_del_ll);
            llContent = itemView.findViewById(R.id.ll_content);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAdviseNameList = itemView.findViewById(R.id.tv_advise_name_list);
            tvTime = itemView.findViewById(R.id.tv_time);
            iv = itemView.findViewById(R.id.iv);
            rightMenuContent = itemView.findViewById(R.id.right_menu_content);
        }

    }
}

