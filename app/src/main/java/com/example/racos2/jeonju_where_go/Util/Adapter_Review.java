package com.example.racos2.jeonju_where_go.Util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.racos2.jeonju_where_go.Activity.Activity_Review_Detail;
import com.example.racos2.jeonju_where_go.Activity.Item_Review;
import com.example.racos2.jeonju_where_go.R;


import java.util.ArrayList;


/**
 * Created by Racos7 on 2017-07-22.
 */

public class Adapter_Review extends RecyclerView.Adapter<Adapter_Review.ViewHolder>{

    private ArrayList<Item_Review> listViewItemList;
    private Context context;
    private RequestManager requestManager;




//    private Util_Dialog dialog;
//    private RequestManager requestManager;

//    int type;
//
    public Adapter_Review(ArrayList<Item_Review> item, Context context, RequestManager requestManager) {
        this.context = context;
        this.listViewItemList = item;
        this.requestManager = requestManager;
    }



    //타입 변환 (지금안쓸꺼임) - 객체생성후에 바로 호출됨.
//    @Override
//    public int getItemViewType(int position) {
//
//
//            return 1;
//    }



    /**
     * 레이아웃을 만들어서 Holer에 저장
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item, viewGroup, false);
        return new ViewHolder(view);

    }


    /**
     * listView getView 를 대체
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     *
     * @param viewHolder
     * @param position
     */


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        requestManager.load(listViewItemList.get(position).getUrl()).asBitmap().into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(context, Activity_Review_Detail.class);
                intent.putExtra("review_data", listViewItemList.get(position));
                context.startActivity(intent);
            }
        });

        viewHolder.title.setText(listViewItemList.get(position).getTitle());

    }


    @Override
    public int getItemCount() {
        return (null != listViewItemList ? listViewItemList.size() : 0);
    }


    /**
     * 뷰 재활용을 위한 viewHolder
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imageView;
        protected TextView title;
        protected RelativeLayout rela;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView)itemView.findViewById(R.id.review_img);
                title = (TextView) itemView.findViewById(R.id.review_title);

                rela = (RelativeLayout)itemView.findViewById(R.id.review_rela);
            }
    }

}
