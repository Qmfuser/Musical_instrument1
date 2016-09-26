package activity.musical.hsd.com.musical_instrument;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hsd.musical.adapter.Musical_figure_adapter;
import com.hsd.musical.bean.IntroduceViewHolder;
import com.hsd.musical.view.CHScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicalActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView tv_intro;
    private ScrollView sv;
    private View gridItem;
    private LinearLayout dot_layout;
    private ArrayList<Musical_figure_adapter> list = new ArrayList<Musical_figure_adapter>();
    public HorizontalScrollView mTouchView;
    private LayoutInflater inflater;
    private LayoutInflater inflater1;
    private ListView musical_lv_special;
    private int[] imgid=new int[]{R.drawable.img02,R.drawable.img03,R.drawable.img05};
    private ListView musical_lv_introduce;
    private SimpleAdapter sim_adapter;
    private List<Map<String, Object>> data_list;
    private GridView gridView;
    private int[] icon = {R.drawable.img01, R.drawable.img03,
            R.drawable.img05, R.drawable.img04, R.drawable.img06, R.drawable.img08, R.drawable.img03, R.drawable.img09};
    //private Musical_introduce_adapter
    //装入所有的HScrollView
    protected List<CHScrollView> mHScrollViews =new ArrayList<CHScrollView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musical_activity);
        gridItem = View.inflate(MusicalActivity.this, R.layout.griditem, null);
        gridView = (GridView) findViewById(R.id.yq_grid);
        sv = (ScrollView) findViewById(R.id.sv);
        sv.smoothScrollTo(0, 0);
        //viewpage实现方法
        initView();
        initData();
        initListener();
        //列表横向滑动
        initViews();
        //专辑列表
        musical_lv_special=(ListView)findViewById(R.id.musical_lv_special);
        musical_lv_special.setAdapter(new Musical_special_adapter());

        musical_lv_introduce=(ListView)findViewById(R.id.musical_lv_introduce);
        musical_lv_introduce.setAdapter(new Musical_introduce_adapter());
        //

        data_list = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"image", "nameOfYq", "price", "re_price"};
        int[] to = {R.id.grid_imv, R.id.grid_name_yq, R.id.grid_price, R.id.grid_re_price};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.griditem, from, to);
        //配置适配器
        gridView.setAdapter(sim_adapter);
    }
    //
    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("nameOfYq", "乐器名称乐器名称");
            map.put("price", "¥ 15.00");
            map.put("re_price", "30.00");
            data_list.add(map);
        }
        return data_list;
    }
    /**
     * 用于设置自动轮播
     */
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            handler.sendEmptyMessageDelayed(0, 4000);
        }
    };
    //初始化视图
    private void initView() {
        viewPager = (ViewPager)findViewById(R.id.viewpage);
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        dot_layout = (LinearLayout)findViewById(R.id.dot_layout);
    }
    //初始化数据
    private void initData(){
        list.add(new Musical_figure_adapter(R.drawable.img01,"img01"));
        list.add(new Musical_figure_adapter(R.drawable.img02, "img02"));
        list.add(new Musical_figure_adapter(R.drawable.img03, "img03"));
        list.add(new Musical_figure_adapter(R.drawable.img04, "img04"));
        list.add(new Musical_figure_adapter(R.drawable.img05, "img05"));
        initDots();
        viewPager.setAdapter(new MyPagerAdapter());
        int centerValue = Integer.MAX_VALUE/2;
        int value = centerValue % list.size();
        //设置viewPager的第一页为最大整数的中间数，实现伪无限循环
        viewPager.setCurrentItem(centerValue-value);
        updateIntroAndDot();
        handler.sendEmptyMessageDelayed(0,4000);
    }
    //初始化文字下方的圆点
    private void initDots() {
        for (int i=0; i< list.size(); i++)
        {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,8);
            if(i!=0) {
                params.leftMargin = 5;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.select_dot);
            dot_layout.addView(view);
        }
    }
    //初始化监听器，当页面改变时，更新其相应数据
    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updateIntroAndDot();
            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    //更新数据与圆点
    private void updateIntroAndDot(){
        int currentPage = viewPager.getCurrentItem() % list.size();
        tv_intro.setText(list.get(currentPage).getIntro());
        for (int i = 0; i < dot_layout.getChildCount(); i++)
            dot_layout.getChildAt(i).setEnabled(i==currentPage);
    }
    //ViewPager的主体部分
    class MyPagerAdapter extends PagerAdapter {
        /**
         * 返回多少page
         */
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        /**view滑动到一半时是否创建新的view
         * true:表示不去创建，使用缓存；false:去重新创建
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        /**
         * 类似于BaseAdapter的getView方法
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(MusicalActivity.this, R.layout.adapter_ad, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            Musical_figure_adapter musicalfigureadapter = list.get(position%list.size());
            imageView.setImageResource(musicalfigureadapter.getIconResId());
            container.addView(view);
            return view;
        }
        /**
         * @param position:当前需要销毁第几个page
         * @param object:当前需要销毁的page
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
    //------------------------------------------------------------------

    //------------------------------------------------------------------
    private void initViews() {
        CHScrollView headerScroll = (CHScrollView) findViewById(R.id.item_scroll_title);
        //添加头滑动事件
        mHScrollViews.add(headerScroll);
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt){
        for(CHScrollView scrollView : mHScrollViews) {
            //防止重复滑动
            if(mTouchView != scrollView)
                scrollView.smoothScrollTo(l, t);
        }
    }

private class Musical_special_adapter extends BaseAdapter{

    public Musical_special_adapter() {
        super();
        inflater = LayoutInflater.from(MusicalActivity.this);
    }
    @Override
    public int getCount() {
        return imgid.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.special_list, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView=(ImageView)view.findViewById(R.id.special_list_iv);
            viewHolder.imageView.setAdjustViewBounds(true);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.imageView.setImageResource(imgid[position]);
        return view;
    }
}
    private class ViewHolder {
        private ImageView imageView;
    }
    public class Musical_introduce_adapter extends BaseAdapter {
        int [] img1_inId=new int[]{R.drawable.img03,R.drawable.img01};
        int [] img2_inId=new int[]{R.drawable.img06,R.drawable.img07};
        int [] img3_inId=new int[]{R.drawable.img08,R.drawable.img09};
        ArrayList<String>tv1_list;
        ArrayList<String>tv2_list;
        ArrayList<String>tv3_list;
        public Musical_introduce_adapter() {
            super();
            inflater1 = LayoutInflater.from(MusicalActivity.this);
            tv1_list=new ArrayList<>();
            for(int i=0;i<2;i++){
                tv1_list.add("乐器名称乐器名称");
            }
            tv2_list=new ArrayList<>();
            for(int i=0;i<2;i++){
                tv2_list.add("¥318.00");
            }
            tv3_list=new ArrayList<>();
            for (int i=0;i<2;i++){
                tv3_list.add("音质优美 配置齐全 手工实木 音质优美 配置齐全 手工实木 音质优美 配置齐全 手工实木 音质优美 配置齐全 手工实木 音质优美");
            }
        }
        @Override
        public int getCount() {
            return tv1_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater1.inflate(R.layout.introduce_list, parent, false);
                IntroduceViewHolder introduceViewHolder = new IntroduceViewHolder();
                introduceViewHolder.imageView1=(ImageView)view.findViewById(R.id.introduce_lsit_iv1);
                introduceViewHolder.imageView1.setAdjustViewBounds(true);
                introduceViewHolder.imageView2=(ImageView)view.findViewById(R.id.introduce_lsit_iv2);
                introduceViewHolder.imageView2.setAdjustViewBounds(true);
                introduceViewHolder.imageView3=(ImageView)view.findViewById(R.id.introduce_lsit_iv3);
                introduceViewHolder.imageView3.setAdjustViewBounds(true);
                introduceViewHolder.content1 = (TextView) view
                        .findViewById(R.id.introduce_lsit_tv1);
                introduceViewHolder.content2 = (TextView) view
                        .findViewById(R.id.introduce_lsit_tv2);
                introduceViewHolder.content3 = (TextView) view
                        .findViewById(R.id.introduce_lsit_tv3);
                view.setTag(introduceViewHolder);
            }
            IntroduceViewHolder introduceViewHolder = (IntroduceViewHolder) view.getTag();

            introduceViewHolder.content1.setText(tv1_list.get(position));
            introduceViewHolder.content2.setText(tv2_list.get(position));
            introduceViewHolder.content3.setText(tv3_list.get(position));
            introduceViewHolder.imageView1.setImageResource(img1_inId[position]);
            introduceViewHolder.imageView2.setImageResource(img2_inId[position]);
            introduceViewHolder.imageView3.setImageResource(img3_inId[position]);
            return view;
        }
    }
}
