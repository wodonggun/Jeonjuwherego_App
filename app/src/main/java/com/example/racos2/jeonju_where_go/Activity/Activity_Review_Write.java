package com.example.racos2.jeonju_where_go.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.racos2.jeonju_where_go.R;
import com.example.racos2.jeonju_where_go.Util.Dialog_custom;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_Review_Write extends AppCompatActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE =  1;
    private static final int PERMISSIONS_REQUEST_CAMERA = 0;
    public static Uri mImageCaptureUri;
    public static Uri outputFileUri;

    private Dialog_custom custom_dial;
    private ImageView btn_add;
    private RelativeLayout img_layout;
    private TextView txt1, txt2, txt3, confirm;
    private boolean is_setphoto;
    private StorageReference mStorageRef;
    private FirebaseStorage storage;
    private Bitmap bitmap;


    /* 업로드 사진 Url, 제목 title, 내용 content */
    private Uri downloadUrl;
    private String title, content;
    private EditText edit_title, edit_content;
    String getTime;
    String url;




    //intent getExtra값
    String email,name;

    //파이어베이스 데이터베이스
    //파이어베이스
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference hiRef = database.getReference();
    Obj_Review obj_review ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");




        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView toolbar_textview = (TextView)findViewById(R.id.toolbar_textview);
        toolbar_textview.setText("  리뷰작성하기");

        btn_add = (ImageView)findViewById(R.id.add_photo);
        btn_add.setOnClickListener(listener);
        img_layout = (RelativeLayout)findViewById(R.id.img_layout);

        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
        txt3 = (TextView)findViewById(R.id.txt3);

        confirm = (TextView)findViewById(R.id.confirm);
        confirm.setOnClickListener(listener);

        edit_title = (EditText)findViewById(R.id.edit_title);
        edit_content = (EditText)findViewById(R.id.edit_content);




    }

    void uploadfile(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        getTime = sdf.format(date);

        StorageReference riverRef = mStorageRef.child("게시판").child(name).child(name+getTime+".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = riverRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(),"게시판 작성 실패하였습니다.",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                downloadUrl = taskSnapshot.getDownloadUrl();
                Log.e("uri", downloadUrl+"");
                url = downloadUrl.toString();

                //obj_review = new Obj_Review(); 객체를 이용한 파이어베이스 데이터 삽입 잘 안됨. 이유모름.
                hiRef.child("게시판").child(name).child(email.substring(0,email.length()-4)).child("title").setValue(title);
                hiRef.child("게시판").child(name).child(email.substring(0,email.length()-4)).child("body").setValue(content);
                hiRef.child("게시판").child(name).child(email.substring(0,email.length()-4)).child("date").setValue(getTime);
                hiRef.child("게시판").child(name).child(email.substring(0,email.length()-4)).child("url").setValue(url);
                hiRef.child("게시판").child(name).child(email.substring(0,email.length()-4)).child("writer").setValue(email);




                Toast.makeText(getApplicationContext(),"성공적으로 작성되었습니다.",Toast.LENGTH_SHORT).show();
                finish();






                //    email.substring(0,email.length()-4) 아이디 추출
                // hiRef = database.getReference("게시판").child(name).push();



            }
        });


    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_photo:
                    addPhoto(img_layout);
                    break;
                case R.id.confirm:
                    title = edit_title.getText().toString();
                    content = edit_content.getText().toString();
                    // 조건 주기
                    // 사진업로드 안되었거나, 제목, 내용이 없거나
                    if(!is_setphoto){
                        custom_dial = new Dialog_custom(Activity_Review_Write.this, getResources().getDrawable(R.drawable.icon_warning), "사진 업로드가 필요합니다. ", "확인","dismiss");
                        custom_dial.show();
                    }else{
                        if(title.equals("")|| title == null){
                            custom_dial = new Dialog_custom(Activity_Review_Write.this, getResources().getDrawable(R.drawable.icon_warning), "제목을 입력해주세요. ", "확인","dismiss");
                            custom_dial.show();
                        }else{
                            if(content.equals("") || content == null){
                                custom_dial = new Dialog_custom(Activity_Review_Write.this, getResources().getDrawable(R.drawable.icon_warning), "내용을 입력해주세요. ", "확인","dismiss");
                                custom_dial.show();
                            }else{
                                uploadfile();


                            }
                        }
                    }





                    break;

            }
        }
    };


    private void addPhoto(View v){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        // 폴더명 및 파일명
        String folderPath = path + File.separator + "chungapp";
        String filePath = path + File.separator + "chungapp" + File.separator +  "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File fileFolderPath = new File(folderPath);
        fileFolderPath.mkdir();

        // 파일 이름 지정
        File file = new File(filePath);
        outputFileUri = Uri.fromFile(file);

        final CharSequence[] items = new CharSequence[]{"사진촬영", "앨범선택", "취소"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("사진촬영")) {

                    if(ContextCompat.checkSelfPermission(Activity_Review_Write.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_Review_Write.this, Manifest.permission.CAMERA)) {

                            // ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            // 사용자가 임의로 권한을 취소
                            // 다이어로그같은것을 띄워서 사용자에게 해당 권한이 필요한 이유에 대해 설명합니다
                            // 해당 설명이 끝난뒤 requestPermissions()함수를 호출하여 권한허가를 요청해야 합니다
                            ActivityCompat.requestPermissions(Activity_Review_Write.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    PERMISSIONS_REQUEST_CAMERA);
                        } else {
                            // 최초 권한 요청
                            ActivityCompat.requestPermissions(Activity_Review_Write.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    PERMISSIONS_REQUEST_CAMERA);
                            // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다
                        }
                    }else{
                        // 권한이 존재하면
                        doTakePhotoAction();
                    }
                } else if (items[which].equals("앨범선택")) {
                    if(ContextCompat.checkSelfPermission(Activity_Review_Write.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_Review_Write.this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            // ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            // 사용자가 임의로 권한을 취소
                            // 다이어로그같은것을 띄워서 사용자에게 해당 권한이 필요한 이유에 대해 설명합니다
                            // 해당 설명이 끝난뒤 requestPermissions()함수를 호출하여 권한허가를 요청해야 합니다
                            ActivityCompat.requestPermissions(Activity_Review_Write.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE);
                        } else {
                            // 최초 권한 요청
                            ActivityCompat.requestPermissions(Activity_Review_Write.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE);
                            // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다
                        }
                    }else{
                        // 권한이 존재하면
                        doTakeAlbumAction();
                    }

                } else
                    dialog.dismiss();
            }
        });
        dialog.show();
    }

    // 카메라에서 가져오기
    private void doTakePhotoAction() {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        // String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";

        //mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));


        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
        //intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    // 앨범에서 가져오기
    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CROP_FROM_CAMERA: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                Log.e("tdd"," "+  outputFileUri.toString());

                bitmap = BitmapFactory.decodeFile(outputFileUri.getPath());
                Drawable drawable = new BitmapDrawable(bitmap);
                //btn_add.setImageBitmap(tmp);
                img_layout.setBackground(drawable);
                is_setphoto = true;
                if(is_setphoto){
                    btn_add.setVisibility(View.GONE);
                    txt1.setVisibility(View.INVISIBLE);
                    txt2.setVisibility(View.INVISIBLE);
                    txt3.setVisibility(View.INVISIBLE);
                }


                break;
            }
            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();

                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 400);
                intent.putExtra("outputY", 400);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                Log.e("td"," "+  outputFileUri.toString());


                break;

            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.

                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(outputFileUri, "image/*");

                intent.putExtra("outputX", 400);
                intent.putExtra("outputY", 400);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
            }
        }
    }

    /** 뒤로 가기 버튼 선택될때*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();            //뒤로가기 기능
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /** 뒤로 가기 버튼 선택할때*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA :
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                    doTakePhotoAction();
                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                    custom_dial = new Dialog_custom(Activity_Review_Write.this, getResources().getDrawable(R.drawable.icon_warning), "동의해주셔야 이용이 가능합니다. ", "확인","dismiss");
                    custom_dial.show();
                }
                return;
            case PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE:
                for(int i=0; i<permissions.length; i++){
                    String permi = permissions[i];
                    int result = grantResults[i];

                    if (permi.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if(result == PackageManager.PERMISSION_GRANTED){
                            doTakeAlbumAction();
                        }else{

                        }
                    } else {
                        // 권한 거부
                        // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                        custom_dial = new Dialog_custom(Activity_Review_Write.this, getResources().getDrawable(R.drawable.icon_warning), "동의해주셔야 이용이 가능합니다. ", "확인","dismiss");
                        custom_dial.show();
                    }
                    return;
                }

        }




        }


}
