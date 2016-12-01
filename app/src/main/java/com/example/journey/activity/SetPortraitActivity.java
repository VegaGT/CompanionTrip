package com.example.journey.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.R;
import com.example.journey.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Li on 2016/9/10.
 */
public class SetPortraitActivity extends AppCompatActivity {
  private ImageView back_to_personal_portrait;//返回
  private TextView save_portrait;//保存
  private ImageView portrait_in_setting;//头像
  private Button set_portrait_bnt;//设置头像
  public static final int TAKE_PHOTO = 1;
  public static final int CROP_PHOTO = 2;
  public static final int CHOOSE_PHOTO = 3;
  private Uri imageUri;
  public String path;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.portrait_setting);
    back_to_personal_portrait = (ImageView) findViewById(R.id.back_to_personal_portrait);
    save_portrait = (TextView) findViewById(R.id.save_portrait);
    portrait_in_setting = (ImageView) findViewById(R.id.portrait_in_setting);
    set_portrait_bnt = (Button) findViewById(R.id.set_portrait_bnt);
    back_to_personal_portrait.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    User userInfo = User.getCurrentUser(User.class);
    if (userInfo != null) {
      if (userInfo.getUserIcon() != null) {
        Glide.with(this).load(userInfo.getUserIcon().getFileUrl()).centerCrop().into(portrait_in_setting);
      } else {
        portrait_in_setting.setImageResource(R.drawable.ic_account_circle_white_24dp);
      }
    }

    //保存图片
    save_portrait.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

          @Override
          public void done(BmobException e) {
            if (e == null) {
              Toast toast = Toast.makeText(getApplicationContext(), "文件上传成功！", Toast.LENGTH_SHORT);
              toast.show();

              User newUser = new User();
              newUser.setUserIcon(bmobFile);
              User user = User.getCurrentUser(User.class);
              //更新用户资料--头像更改
              newUser.update(user.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                  if (e == null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "头像保存成功！", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                  } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "头像保存失败" + e.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                  }
                }
              });
            } else {
              Toast toast = Toast.makeText(getApplicationContext(), "文件上传失败", Toast.LENGTH_SHORT);
              toast.show();
            }

          }

          @Override
          public void onProgress(Integer value) {
            // 返回的上传进度（百分比）
          }
        });

      }
    });
    //设置头像
    set_portrait_bnt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final String[] items = new String[]{"拍照", "从相册选择"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(SetPortraitActivity.this);
        dialog.setTitle("设置头像");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
              case 0:
                File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                try {
                  if (outputImage.exists()) {
                    outputImage.delete();
                  }
                  outputImage.createNewFile();
                } catch (IOException e) {
                  e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;
              case 1:
                Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
                intent1.setType("image/*");
                startActivityForResult(intent1, CHOOSE_PHOTO);
                break;
            }
          }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
          }
        });
        dialog.show();
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case TAKE_PHOTO:
        if (resultCode == RESULT_OK) {
          Intent intent = new Intent("com.android.camera.action.CROP");
          intent.setDataAndType(imageUri, "image/*");
          intent.putExtra("scale", true);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
          startActivityForResult(intent, CROP_PHOTO);
          path = getImagePath(imageUri, null); //加
        }
        break;
      case CROP_PHOTO:
        if (resultCode == RESULT_OK) {
          try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            portrait_in_setting.setImageBitmap(bitmap);
            path = getImagePath(imageUri, null); //加
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        }
        break;
      case CHOOSE_PHOTO:
        if (resultCode == RESULT_OK) {
          if (Build.VERSION.SDK_INT >= 19) {
            handleImageOnKitKat(data);
          } else {
            handleImageBeforeKitKat(data);
          }
        }
      default:
        break;
    }
  }

  @TargetApi(19)
  private void handleImageOnKitKat(Intent data) {
    String imagePath = null;
    Uri uri = data.getData();
    if (DocumentsContract.isDocumentUri(this, uri)) {
      String docId = DocumentsContract.getDocumentId(uri);
      if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
        String id = docId.split(":")[1];
        String selection = MediaStore.Images.Media._ID + "=" + id;
        imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
      } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
        Uri contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public/public_downloads"), Long.valueOf(docId));
        imagePath = getImagePath(contentUri, null);
      } else if ("content".equalsIgnoreCase(uri.getScheme())) {
        imagePath = getImagePath(uri, null);
      }
      displayImage(imagePath);
      path = imagePath;
    }
  }

  private void handleImageBeforeKitKat(Intent data) {
    Uri uri = data.getData();
    String imagePath = getImagePath(uri, null);
    displayImage(imagePath);
    path = imagePath;
  }

  private String getImagePath(Uri uri, String selection) {
    String path = null;
    Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
      }
      cursor.close();
    }
    return path;
  }

  private void displayImage(String imagePath) {
    if (imagePath != null) {
      Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
      portrait_in_setting.setImageBitmap(bitmap);
    } else {
      Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
    }
  }
}