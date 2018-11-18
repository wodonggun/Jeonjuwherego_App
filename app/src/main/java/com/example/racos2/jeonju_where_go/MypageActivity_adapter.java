package com.example.racos2.jeonju_where_go;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

/**
 * Created by Racos7 on 2017-07-29.
 */

public class MypageActivity_adapter extends RecyclerView.Adapter<MypageActivity_adapter.ViewHolder> {




        private ArrayList<MypageActivity_Item> listViewItemList;
        private Context context;
        private RequestManager requestManager;

        public static final int ITEM_TYPE_ONE = 1;
        public static final int ITEM_TYPE_TWO = 2;
        public static final int ITEM_TYPE_THREE = 3;
        public static final int ITEM_TYPE_FOUR = 4;



        public MypageActivity_adapter(ArrayList<MypageActivity_Item> item, Context context, RequestManager requestManager) {
            this.context = context;
            this.listViewItemList = item;
            this.requestManager = requestManager;
        }



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

//        if (viewType == ITEM_TYPE_ONE) {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
//            return new ViewHolder(view, ITEM_TYPE_ONE);
//        } else if (viewType == ITEM_TYPE_TWO) {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
//            return new ViewHolder(view, ITEM_TYPE_TWO);
//        } else if (viewType == ITEM_TYPE_THREE) {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
//            return new ViewHolder(view, ITEM_TYPE_THREE);
//        } else {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
//            return new ViewHolder(view, ITEM_TYPE_FOUR);
//        }

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mypage_list, viewGroup, false);
            return new ViewHolder(view);

        }


        /**
         * listView getView 를 대체
         * 넘겨 받은 데이터를 화면에 출력하는 역할
         *
         * @param viewHolder
         * @param position
         */


        //아이템 갯수
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            //final int itemtype = getItemViewType(position);


            requestManager.load(listViewItemList.get(position).getImage()).asBitmap().into(viewHolder.imageView);


            viewHolder.title.setText(listViewItemList.get(position).getName());


            viewHolder.rela2.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    if(listViewItemList.get(position).getLike()!=null) {
                        Intent intent;
                        intent = new Intent(context, lastpage_fromMypage.class);
                        intent.putExtra("text2", listViewItemList.get(position));
                        context.startActivity(intent);
                    }
                    else {
                        String homepage = "http://tour.jeonju.go.kr";
                        Intent callhome;
                        callhome = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                        context.startActivity(callhome);
                    }
                }
            });

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
            protected RelativeLayout rela2;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView)itemView.findViewById(R.id.mypage_imageviewlist);
                title = (TextView) itemView.findViewById(R.id.mypage_imageviewlisttitle);
                rela2 = (RelativeLayout)itemView.findViewById(R.id.select2_relative2);
            }
        }

    }


