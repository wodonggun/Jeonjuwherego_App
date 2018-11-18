package com.example.racos2.jeonju_where_go;


import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.racos2.jeonju_where_go.MainActivity.user_email;
import static com.example.racos2.jeonju_where_go.MypageActivity.item;


/**
 * Created by Racos2 on 2017-11-15.
 */

public class Firebase_CheckDB extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference hiRef = database.getReference();
    private Boolean TF = false;
    private String k ="";
    private String name = "";
    private String e_mail;
    private int count;
    private String cart_list[];

    private int imageView11_count;

    Firebase_CheckDB() {

        if (user_email != null) {
            this.name = user_email.substring(0, user_email.length() - 4);
            hiRef = hiRef.child("유저").child(this.getName()).child("cart");


            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            MainActivity.Scart_count = snapshot.child("TotalCount").child("count").getValue(String.class);


                        }


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    ;
                }
            };
        }
    }





    boolean Firebase_CheckDB(String abc) {
        this.k = abc;
        database.getReference().child("유저").child(user_email.substring(0,user_email.length()-4)).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("name").getValue(String.class)!=null)
                      if(snapshot.child("name").getValue(String.class).equals(k)) {
                           TF = true;
                          break;

                  }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    return TF;

    }

    int inner_CheckDB(String abc){
        try{
        for(int i=0;i<item.size();i++){
            if(MypageActivity.item.get(i).getName()==null)  //왜 카트에담기를 두번 연달아 하면 null인지 모르겠다.........
                return 1;
            if(MypageActivity.item.get(i).getName().equals(abc)){
                return 1;
            }
        }
        return 0;
    }
    catch(Exception E){
       return 1;
    }
    }




    String getName() { return (this.name);}

    int getCount() { return (this.count);}




}


