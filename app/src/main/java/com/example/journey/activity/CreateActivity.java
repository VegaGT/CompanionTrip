package com.example.journey.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.R;
import com.example.journey.model.Post;
import com.example.journey.model.ProvinceAndCity;
import com.example.journey.model.User;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class CreateActivity extends AppCompatActivity {
  private static int REQUEST_CODE_PICKER = 1;

  private Spinner province1;
  private Spinner province2;
  private Spinner province3;
  private Spinner province4;
  private Spinner city1;
  private Spinner city2;
  private Spinner city3;
  private Spinner city4;
  private LinearLayout choose_address2;// 地点2
  private LinearLayout choose_address3;//地点3
  private LinearLayout choose_address4;//地点4
  private EditText etStartTime;//选择开始时间
  private EditText etEndTime;//选择结束时间
  private int mYear;
  private int mMonth;
  private int mDay;
  private int timeFlag;
  private EditText title;
  private EditText description;
  private Spinner numberOfPeople;
  private ImageView addPicture;
  private ImageView picture1;
  private ImageView picture2;
  private ImageView picture3;
  private ImageView picture4;
  private TextView count_of_tags;//用户当前所选的帖子数量
  private TextView add_tags;//添加自定义标签

  List<ProvinceAndCity> Province = new ArrayList<>();
  private List<String> provinceList = new ArrayList<String>(); //省份列表
  private List<String> cityList = new ArrayList<String>();     //城市列表
  private ArrayAdapter<String> provinceAdapter;
  private List<String> selectedProvince = new ArrayList<>(); //已选省份
  private List<String> selectedCity = new ArrayList<>();     //已选城市
  int provinceNumber, cityNumber;

  private int background0[] = new int[14];//默认标签的背景状态，0为灰色，1为蓝色
  private List<TextView> tags = new ArrayList<>();//app默认显示的标签
  private List<TextView> user_tags = new ArrayList<>();//自定义标签
  private List<String> chosenTags = new ArrayList<>();//已选的标签内容
  int tag_count = 0;//当前所选标签数量

  ArrayList<Image> images = new ArrayList<>();
  private List<String> picPath = new ArrayList<>(); //图片路径


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create);
    //各个组件的注册
    province1 = (Spinner) findViewById(R.id.Spinner_province1);
    province2 = (Spinner) findViewById(R.id.Spinner_province2);
    province3 = (Spinner) findViewById(R.id.Spinner_province3);
    province4 = (Spinner) findViewById(R.id.Spinner_province4);
    city1 = (Spinner) findViewById(R.id.Spinner_city1);
    city2 = (Spinner) findViewById(R.id.Spinner_city2);
    city3 = (Spinner) findViewById(R.id.Spinner_city3);
    city4 = (Spinner) findViewById(R.id.Spinner_city4);
    choose_address2 = (LinearLayout) findViewById(R.id.choose_address2);
    choose_address3 = (LinearLayout) findViewById(R.id.choose_address3);
    choose_address4 = (LinearLayout) findViewById(R.id.choose_address4);
    etStartTime = (EditText) findViewById(R.id.et_start_time);
    etEndTime = (EditText) findViewById(R.id.et_end_time);
    title = (EditText) findViewById(R.id.create_trip_title);
    description = (EditText) findViewById(R.id.add_content);
    numberOfPeople = (Spinner) findViewById(R.id.Spinner_numberOfPeople);
    addPicture = (ImageView) findViewById(R.id.choose_trip_picture);
    picture1 = (ImageView) findViewById(R.id.picture1);
    picture2 = (ImageView) findViewById(R.id.picture2);
    picture3 = (ImageView) findViewById(R.id.picture3);
    picture4 = (ImageView) findViewById(R.id.picture4);
    add_tags = (TextView) findViewById(R.id.add_my_tags);
    count_of_tags = (TextView) findViewById(R.id.count_of_tags);


    //以下是关于Spinner的代码
    addProvince();
    Intent intent = getIntent();
    Log.d("create", "省份 " + intent.getIntExtra("provinceNumber", -1) + "城市 "
            + intent.getIntExtra("cityNumber", -1));
    provinceNumber = intent.getIntExtra("provinceNumber", -1);
    cityNumber = intent.getIntExtra("cityNumber", -1);
    if (provinceNumber >= 0 && cityNumber >= 0) {
      province1.setSelection(provinceNumber);
      city1.setSelection(cityNumber);
    }

    etStartTime.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        timeFlag = 0;
        showDialog(0);
      }
    });
    etEndTime.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        timeFlag = 1;
        showDialog(1);
      }
    });
    etStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {
        if (b == true) {
          timeFlag = 0;
          showDialog(0);
        }
      }
    });
    etEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {
        if (b == true) {
          timeFlag = 1;
          showDialog(1);
        }
      }
    });

    //默认标签
    final TextView tag1 = (TextView) findViewById(R.id.tag1);
    final TextView tag2 = (TextView) findViewById(R.id.tag2);
    final TextView tag3 = (TextView) findViewById(R.id.tag3);
    final TextView tag4 = (TextView) findViewById(R.id.tag4);
    final TextView tag5 = (TextView) findViewById(R.id.tag5);
    final TextView tag6 = (TextView) findViewById(R.id.tag6);
    final TextView tag7 = (TextView) findViewById(R.id.tag7);
    final TextView tag8 = (TextView) findViewById(R.id.tag8);
    final TextView tag9 = (TextView) findViewById(R.id.tag9);
    final TextView tag10 = (TextView) findViewById(R.id.tag10);
    final TextView tag11 = (TextView) findViewById(R.id.tag11);
    final TextView tag12 = (TextView) findViewById(R.id.tag12);
    final TextView tag13 = (TextView) findViewById(R.id.tag13);
    final TextView tag14 = (TextView) findViewById(R.id.tag14);

    //自定义标签
    final TextView tag15 = (TextView) findViewById(R.id.tag15);
    final TextView tag16 = (TextView) findViewById(R.id.tag16);
    final TextView tag17 = (TextView) findViewById(R.id.tag17);


    //默认标签
    tags.add(tag1);
    tags.add(tag2);
    tags.add(tag3);
    tags.add(tag4);
    tags.add(tag5);
    tags.add(tag6);
    tags.add(tag7);
    tags.add(tag8);
    tags.add(tag9);
    tags.add(tag10);
    tags.add(tag11);
    tags.add(tag12);
    tags.add(tag13);
    tags.add(tag14);

    //循环添加事件
    for (int i = 0; i < tags.size(); ++i) {
      final TextView tag = tags.get(i);
      final int k = i;
      tag.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (background0[k] == 0) {
            if (tag_count < 3) {
              background0[k] = 1;
              tag.setBackgroundDrawable(getResources().getDrawable(R.drawable.tag_bg_primary));
              tag_count++;
              count_of_tags.setText(String.valueOf(tag_count));
              chosenTags.add(tag.getText().toString());//已选标签添加到list<string>———chosenTags里
            } else {
              Toast.makeText(CreateActivity.this, "所选标签数已达上限", Toast.LENGTH_SHORT).show();
            }
          } else if (background0[k] == 1) {
            background0[k] = 0;
            tag.setBackgroundDrawable(getResources().getDrawable(R.drawable.tag_bg_grey300));
            tag_count--;
            count_of_tags.setText(String.valueOf(tag_count));
            chosenTags.remove(tag.getText().toString());//移出chosenTgas
          }
        }
      });
    }

    //自定义标签
    user_tags.add(tag15);
    user_tags.add(tag16);
    user_tags.add(tag17);

    //循环添加事件
    for (int j = 0; j < user_tags.size(); ++j) {
      final TextView user_tag = user_tags.get(j);
      user_tag.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (user_tag.getVisibility() == View.VISIBLE) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(CreateActivity.this);
            dialog.setTitle("请选择");
            dialog.setMessage("确定要删除吗？");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                user_tag.setVisibility(View.GONE);
                tag_count--;
                count_of_tags.setText(String.valueOf(tag_count));
                chosenTags.remove(user_tag.getText().toString());
              }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {

              }
            });
            dialog.show();
          }
        }
      });
    }


    //添加自定义标签的按钮的事件
    add_tags.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (tag_count < 3) {
          AlertDialog.Builder dialog = new AlertDialog.Builder(CreateActivity.this);
          LayoutInflater factory = LayoutInflater.from(CreateActivity.this);
          final View textEntryView = factory.inflate(R.layout.user_defined_add_tag_dialog, null);

          final EditText user_defined_tag = (EditText) textEntryView.findViewById(R.id.user_defined_tag);
          final TextView user_tag_word_name = (TextView) textEntryView.findViewById(R.id.user_tag_word_num);//字数
          user_defined_tag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
              user_tag_word_name.setText(String.valueOf(editable.toString().length()));
            }
          });

          dialog.setTitle("添加自定义标签");
          dialog.setView(textEntryView);

          dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              //子布局中的控件
              if (user_defined_tag.getText().length() > 0) {
                for (int j = 0; j < 3; ++j) {
                  if (user_tags.get(j).getVisibility() == View.GONE) {
                    user_tags.get(j).setVisibility(View.VISIBLE);
                    user_tags.get(j).setText(user_defined_tag.getText());
                    chosenTags.add(user_tags.get(j).getText().toString());
                    tag_count++;
                    count_of_tags.setText(String.valueOf(tag_count));
                    break;
                  }
                }
              } else {
                Toast.makeText(CreateActivity.this, "无标签内容", Toast.LENGTH_SHORT).show();
              }
            }
          });
          dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
          });
          dialog.show();
        } else {
          Toast.makeText(CreateActivity.this, "所选标签数已达上限", Toast.LENGTH_SHORT).show();
        }
      }
    });

    //当前用户
    final User currentUser = User.getCurrentUser(User.class);
    Button submit = (Button) findViewById(R.id.submitJourney);
    final Post post = new Post();
    //人数限制
    numberOfPeople.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        post.setNumber(Integer.parseInt(numberOfPeople.getSelectedItem().toString().trim()));
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    //添加图片
    addPicture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ImagePicker.create(CreateActivity.this)
                .folderMode(true) // folder mode (false by default)
                .folderTitle("选择相册") // folder selection title
                .imageTitle("选择图片") // image selection title
                .multi() // multi mode (default mode)
                .limit(4) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .start(REQUEST_CODE_PICKER); // start image picker activity with request code
      }
    });
    //发布行程
    submit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (etStartTime.getText().toString().equals("") || etEndTime.getText().toString().equals("") ||
                title.getText().toString().equals("") || description.getText().toString().equals("")) {
          Toast toast = Toast.makeText(getApplicationContext(), "请把信息填写完整哦", Toast.LENGTH_SHORT);
          toast.show();
        } else {
          post.setProvince(selectedProvince);
          post.setCity(selectedCity);
          post.setStartDate(etStartTime.getText().toString());
          post.setEndDate(etEndTime.getText().toString());
          post.setAuthor(currentUser);
          post.setTitle(title.getText().toString());
          post.setDescription(description.getText().toString());
          post.setJoinedNumber(0);
          post.setIsDone(false);
          //上传tag
          if (chosenTags.size() != 0) {
            post.setTag(chosenTags);
          }
//          Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(picPath.size()), Toast.LENGTH_SHORT);
//          toast.show();
          final String[] picture = new String[picPath.size()];
          if (picPath.size() != 0) {

            final ProgressDialog progressDialog = new ProgressDialog(CreateActivity.this);
            progressDialog.setMessage("行程正在发送中，请稍后");
            progressDialog.show();
            BmobFile.uploadBatch(picPath.toArray(picture), new UploadBatchListener() {
              @Override
              public void onSuccess(List<BmobFile> files, List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if (urls.size() == picture.length) {//如果数量相等，则代表文件全部上传完成
                  Toast toast = Toast.makeText(getApplicationContext(), "图片上传成功", Toast.LENGTH_SHORT);
                  toast.show();
                  for (int i = 0; i < files.size(); i++) {
                    post.setImage(files.get(i));
                  }
                  post.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                      if (e == null) {
                        progressDialog.dismiss();
                        Toast toast = Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                      } else {
                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                      }
                    }
                  });

                }
              }

              @Override
              public void onError(int statuscode, String errormsg) {
                progressDialog.dismiss();
//                Toast toast = Toast.makeText(getApplicationContext(), "错误码" + statuscode + ",错误描述："
//                        + errormsg, Toast.LENGTH_SHORT);
//                toast.show();
              }

              @Override
              public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
              }
            });

          } else {
            post.save(new SaveListener<String>() {
              @Override
              public void done(String objectId, BmobException e) {
                if (e == null) {
                  Toast toast = Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT);
                  toast.show();
                  finish();
                } else {
                  Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
              }
            });
          }

        }
      }
    });

  }

  //把34个省加到provinceList里
  public void addProvince() {
    BmobQuery<ProvinceAndCity> query = new BmobQuery<ProvinceAndCity>();
    //返回50条数据，如果不加上这条语句，默认返回10条数据
    query.setLimit(50);
    //执行查询方法
    query.findObjects(new FindListener<ProvinceAndCity>() {
      @Override
      public void done(List<ProvinceAndCity> object, BmobException e) {
        if (e == null) {
          Province = object;
          for (int i = 0; i < object.size(); i++) {
            provinceList.add(object.get(i).getProvince());
          }
          selectLocation();
        } else {
          Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
        }
      }
    });
  }

  private void selectLocation() {
    //为下拉列表定义一个适配器
    provinceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinceList);
    //为适配器设置下拉列表下拉时的菜单样式
    provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    //将适配器添加到下拉列表上
    province1.setAdapter(provinceAdapter);
    province2.setAdapter(provinceAdapter);
    province3.setAdapter(provinceAdapter);
    province4.setAdapter(provinceAdapter);
    //为下拉列表设置各种事件的响应，这个事响应菜单被选中

    if (provinceNumber >= 0 && cityNumber >= 0) {
      province1.setSelection(provinceNumber);
    }
    province1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(CreateActivity.this, "已选 " + provinceAdapter.getItem(position), Toast.LENGTH_SHORT).show();
        addProvince(0, provinceAdapter, position);
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(CreateActivity.this,
                android.R.layout.simple_spinner_item, Province.get(position).getCity());
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city1.setAdapter(cityAdapter);
        if (provinceNumber >= 0 && cityNumber >= 0) {
          city1.setSelection(cityNumber);
        }
        city1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(CreateActivity.this, "已选 " + cityAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            addCity(0, cityAdapter, position);
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
        });
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        //Toast.makeText(CreateActivity.this, "hahaha", Toast.LENGTH_SHORT).show();
      }
    });


    province2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(CreateActivity.this, "已选 " + provinceAdapter.getItem(position), Toast.LENGTH_SHORT).show();
        //selectedProvince.set(1,provinceAdapter.getItem(position));
        addProvince(1, provinceAdapter, position);
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(CreateActivity.this,
                android.R.layout.simple_spinner_item, Province.get(position).getCity());
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city2.setAdapter(cityAdapter);
        city2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(CreateActivity.this, "已选 " + cityAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            //selectedCity.set(1,cityAdapter.getItem(position));
            addCity(1, cityAdapter, position);
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
        });
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        //Toast.makeText(CreateActivity.this, "hahaha", Toast.LENGTH_SHORT).show();
      }
    });

    province3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(CreateActivity.this, "已选 " + provinceAdapter.getItem(position), Toast.LENGTH_SHORT).show();
        //selectedProvince.set(2,provinceAdapter.getItem(position));
        addProvince(2, provinceAdapter, position);
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(CreateActivity.this,
                android.R.layout.simple_spinner_item, Province.get(position).getCity());
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city3.setAdapter(cityAdapter);
        city3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(CreateActivity.this, "已选 " + cityAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            //selectedCity.set(2,cityAdapter.getItem(position));
            addCity(2, cityAdapter, position);
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
        });
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        //Toast.makeText(CreateActivity.this, "hahaha", Toast.LENGTH_SHORT).show();
      }
    });

    province4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(CreateActivity.this, "已选 " + provinceAdapter.getItem(position), Toast.LENGTH_SHORT).show();
        //selectedProvince.set(3,provinceAdapter.getItem(position));
        addProvince(3, provinceAdapter, position);
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(CreateActivity.this,
                android.R.layout.simple_spinner_item, Province.get(position).getCity());
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city4.setAdapter(cityAdapter);
        city4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(CreateActivity.this, "已选 " + cityAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            //selectedCity.set(3,cityAdapter.getItem(position));
            addCity(3, cityAdapter, position);
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
        });
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(CreateActivity.this, "hahaha", Toast.LENGTH_SHORT).show();
      }
    });
  }

  void addProvince(int i, ArrayAdapter<String> adapter, int position) {
    if (selectedProvince.size() > i) {
      selectedProvince.set(i, adapter.getItem(position));
    } else {
      selectedProvince.add(i, adapter.getItem(position));
    }
  }

  void addCity(int i, ArrayAdapter<String> adapter, int position) {
    if (selectedCity.size() > i) {
      selectedCity.set(i, adapter.getItem(position));
    } else {
      selectedCity.add(i, adapter.getItem(position));
    }
  }


  private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
      mYear = year;
      String mm;
      String dd;
      if (monthOfYear < 9) {
        mMonth = monthOfYear + 1;
        mm = "0" + mMonth;
      } else {
        mMonth = monthOfYear + 1;
        mm = String.valueOf(mMonth);
      }
      if (dayOfMonth <= 9) {
        mDay = dayOfMonth;
        dd = "0" + mDay;
      } else {
        mDay = dayOfMonth;
        dd = String.valueOf(mDay);
      }
      mDay = dayOfMonth;
      if (timeFlag == 0) {
        etStartTime.setText(String.valueOf(mYear) + "." + mm + "." + dd);
      } else {
        etEndTime.setText(String.valueOf(mYear) + "." + mm + "." + dd);
      }
    }
  };

  protected Dialog onCreateDialog(int id) {
    Calendar cal = Calendar.getInstance();
    switch (id) {
      case 0:
        DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, myDateSetListener, mYear, mMonth, mDay);
        datePickerDialog1.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog1;
      case 1:
        DatePickerDialog datePickerDialog2 = new DatePickerDialog(this, myDateSetListener, mYear, mMonth, mDay);
        datePickerDialog2.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog2;
    }
    return null;
  }

  public void bntAddNewLocation(View view) {
    if (choose_address2.getVisibility() == View.GONE) {
      choose_address2.setVisibility(View.VISIBLE);
    } else if (choose_address3.getVisibility() == View.GONE) {
      choose_address3.setVisibility(View.VISIBLE);
    } else if (choose_address4.getVisibility() == View.GONE) {
      choose_address4.setVisibility(View.VISIBLE);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
      images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
      for (int i = 0; i < images.size(); i++)
        picPath.add(images.get(i).getPath());
//      Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(images.size()), Toast.LENGTH_SHORT);
//      toast.show();
      //显示选择的图片
      switch (images.size()) {
        case 1: {
          Glide.with(this).load(picPath.get(0)).centerCrop().into(picture1);
          picture1.setVisibility(View.VISIBLE);
          break;
        }
        case 2: {
          Glide.with(this).load(picPath.get(0)).centerCrop().into(picture1);
          Glide.with(this).load(picPath.get(1)).centerCrop().into(picture2);
          picture1.setVisibility(View.VISIBLE);
          picture2.setVisibility(View.VISIBLE);
          break;
        }
        case 3: {
          Glide.with(this).load(picPath.get(0)).centerCrop().into(picture1);
          Glide.with(this).load(picPath.get(1)).centerCrop().into(picture2);
          Glide.with(this).load(picPath.get(2)).centerCrop().into(picture3);
          picture1.setVisibility(View.VISIBLE);
          picture2.setVisibility(View.VISIBLE);
          picture3.setVisibility(View.VISIBLE);
          break;
        }
        case 4: {
          Glide.with(this).load(picPath.get(0)).centerCrop().into(picture1);
          Glide.with(this).load(picPath.get(1)).centerCrop().into(picture2);
          Glide.with(this).load(picPath.get(2)).centerCrop().into(picture3);
          Glide.with(this).load(picPath.get(3)).centerCrop().into(picture4);
          picture1.setVisibility(View.VISIBLE);
          picture2.setVisibility(View.VISIBLE);
          picture3.setVisibility(View.VISIBLE);
          picture4.setVisibility(View.VISIBLE);
          break;
        }
      }
    }
  }

  public void btnBack(View view) {
    finish();
  }
}
