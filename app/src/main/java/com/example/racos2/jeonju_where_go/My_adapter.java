package com.example.racos2.jeonju_where_go;

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

import java.util.ArrayList;

import static com.example.racos2.jeonju_where_go.MainActivity.info;


/**
 * Created by Racos7 on 2017-07-22.
 */

public class My_adapter extends RecyclerView.Adapter<My_adapter.ViewHolder> {

    private ArrayList<Item> listViewItemList;
    private Context context;

    private RequestManager requestManager,requestManager2,requestManager3;

    public static final int ITEM_TYPE_ONE = 1;
    public static final int ITEM_TYPE_TWO = 2;
    public static final int ITEM_TYPE_THREE = 3;
    public static final int ITEM_TYPE_FOUR = 4;

//    private Util_Dialog dialog;
//    private RequestManager requestManager;

//    int type;
//
    public My_adapter(ArrayList<Item> item, Context context, RequestManager requestManager) {
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
        if(info.equals("축제")) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list2, viewGroup, false);
            return new ViewHolder(view);
        }
        else
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
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
       //final int itemtype = getItemViewType(position);


        requestManager.load(listViewItemList.get(position).getImage()).asBitmap().into(viewHolder.imageView);
     //   requestManager.load(listViewItemList.get(position).getImage()).asBitmap().into(viewHolder.imageView2);
      //  requestManager.load(listViewItemList.get(position).getImage()).asBitmap().into(viewHolder.imageView3);


        viewHolder.name.setText(listViewItemList.get(position).getName());
        viewHolder.address.setText(listViewItemList.get(position).getAddress());
        viewHolder.like.setText(listViewItemList.get(position).getLike());


//        viewHolder.Latitude.setText(listViewItemList.get(position).getName());
//        viewHolder.Longtitude.setText(listViewItemList.get(position).getName());
//        viewHolder.link.setText(listViewItemList.get(position).getName());
//        viewHolder.menu.setText(listViewItemList.get(position).getName());
//        viewHolder.phone.setText(listViewItemList.get(position).getName());
//        viewHolder.time.setText(listViewItemList.get(position).getName());


        viewHolder.rela.setOnClickListener(new View.OnClickListener() {


        @Override
        public void onClick(View v) {

                Intent intent;
                intent = new Intent(context, lastpage.class);
                intent.putExtra("text", listViewItemList.get(position));
                context.startActivity(intent);


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

        protected ImageView imageView,imageView2,imageView3;
        protected TextView name, address,like,Latitude,Longtitude,link,menu,phone,time;
        protected RelativeLayout rela;

            public ViewHolder(View itemView) {
                super(itemView);

                imageView = (ImageView)itemView.findViewById(R.id.imageviewlist);
//                imageView2 = (ImageView)itemView.findViewById(R.id.imageviewlist2);
//                imageView3 = (ImageView)itemView.findViewById(R.id.imageviewlist3);
                address = (TextView) itemView.findViewById(R.id.address);
                like = (TextView)itemView.findViewById(R.id.like);
                name = (TextView) itemView.findViewById(R.id.title);
//                Latitude = (TextView)itemView.findViewById(R.id.like);
//                Longtitude = (TextView)itemView.findViewById(R.id.like);
//                menu = (TextView)itemView.findViewById(R.id.like);
//                phone = (TextView)itemView.findViewById(R.id.like);
//                time = (TextView)itemView.findViewById(R.id.like);


                rela = (RelativeLayout)itemView.findViewById(R.id.select2_relative);

            }
    }

}
