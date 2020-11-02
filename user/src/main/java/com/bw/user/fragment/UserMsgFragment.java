package com.bw.user.fragment;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bw.common.FinanCilalConstant;
import com.bw.net.service.DownloadService;
import com.bw.user.manager.InvestUserManager;
import com.bw.framwork.base.BaseFragment;
import com.bw.net.mode.UpLoadBean;
import com.bw.user.R;
import com.bw.user.RegiLoginActivity;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class UserMsgFragment extends BaseFragment implements View.OnClickListener {
    private ImageView ivHead;
    private PopupWindow popupWindow;
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final String IMAGE_FILE_NAME = "icon.jpg";

    @Override
    protected int bandlayout() {
        return R.layout.usermsgfrag;
    }

    @Override
    protected void initview() {
        if (!InvestUserManager.getInstance().isUserLogin()) {
            launchActivity(RegiLoginActivity.class, null);
            getActivity().finish();
            return;
        }
        ivHead = (ImageView) findViewById(R.id.iv_head);
        findViewById(R.id.tv_sethead).setOnClickListener(this);
        findViewById(R.id.out_user).setOnClickListener(this);
        Glide.with(getContext()).load(FinanCilalConstant.BaseUrl + InvestUserManager.getInstance().getUserImage()).transform(new CircleCrop())
                .into((ImageView) findViewById(R.id.iv_head));

    }

    @Override
    protected void initdata() {

    }

    @Override
    public void onLeftClick() {
        super.onLeftClick();
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_sethead) {
            showheadPopWindow();
        } else if (v.getId() == R.id.out_user) {
            //退出登录
            InvestUserManager.getInstance().processLogOut();
        } else if (v.getId() == R.id.bu_xc) {
            //打开相册
            OpenPhotos();
            popupWindow.dismiss();
        } else if (v.getId() == R.id.bu_pz) {
            //拍照
            Takephotos();
            popupWindow.dismiss();
        } else if (v.getId() == R.id.bu_bc) {
            //保存该图片
            DownLoadPhoto();
            popupWindow.dismiss();
        }
    }

    private void Takephotos() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    private void OpenPhotos() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_GET);
    }


    private void DownLoadPhoto() {
        final String headurl = FinanCilalConstant.BaseUrl + InvestUserManager.getInstance().getUserImage();
        String userImage = InvestUserManager.getInstance().getUserImage();
        String[] split = userImage.split("/");
        final String filename = split[2];

        Intent intent = new Intent();
        intent.setClass(getContext(), DownloadService.class);
        getContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                binder.getSerice().downloaduserimage(headurl, filename, new DownloadService.DoLoadUseImageListener() {
                    @Override
                    public void success() {
                        Toast.makeText(getContext(), "下载成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail() {
                        Toast.makeText(getContext(), "下载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);


    }


    private void showheadPopWindow() {
        popupWindow = new PopupWindow();
        View headpopview = LinearLayout.inflate(getContext(), R.layout.pop_userhrad, null);
        popupWindow.setContentView(headpopview);
        headpopview.findViewById(R.id.bu_xc).setOnClickListener(this);
        headpopview.findViewById(R.id.bu_pz).setOnClickListener(this);
        headpopview.findViewById(R.id.bu_bc).setOnClickListener(this);
        popupWindow.setHeight(GridLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(GridLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popmenu_animation);
        popupWindow.showAtLocation(headpopview, Gravity.BOTTOM, 0, 0);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    startSmallPhotoZoom(Uri.fromFile(temp));
                    break;
                case REQUEST_IMAGE_GET:
                    startSmallPhotoZoom(data.getData());
                    break;
                case REQUEST_SMALL_IMAGE_CUTTING://裁剪  不管怎么选的图裁剪结果都是同一个文件 直接uri读取好了 省事
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
            }
        }
    }


    /**
     * 小图模式切割图片
     * 此方式直接返回截图后的 bitmap，由于内存的限制，返回的图片会比较小
     */
    public void startSmallPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }

    /**
     * 小图模式中，保存图片后，设置到视图中
     */
    private void setPicToView(Intent data) {
        File file = null;
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data"); // 直接获得内存中保存的 bitmap
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(storage + "/smallIcon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        printLog("文件夹创建失败");
                    } else {
                        printLog("文件夹创建成功");
                    }
                }
                file = new File(dirFile, System.currentTimeMillis() + ".jpg");
                // 保存图片
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //进行采样
            Bitmap bitmap = GetNewBitmap(ivHead.getMeasuredWidth(), ivHead.getMeasuredHeight(), photo);
            if (photo != null) {
                photo.recycle();
            }
            // 在视图中显示图片
            Glide.with(getContext()).load(bitmap).transform(new CircleCrop()).into(ivHead);
            lunBanPress(file.getPath());
        }
    }


    private Bitmap GetNewBitmap(int measuredWidth, int measuredHeight, Bitmap photobitmap) {
        int picWidth = photobitmap.getWidth();
        int picHeight = photobitmap.getHeight();
        //计算出缩放比例
        int sampleSize = 1;
        while (picHeight / sampleSize > measuredHeight || picWidth / sampleSize > measuredWidth) {
            sampleSize = sampleSize * 2;
        }
        //第一次采样结束
        //第二次采样，就是按照这个比例采集像素
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;//不是采集边框，而是按比例采集像素
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //将originalbitmap转换成byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photobitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] bytes = baos.toByteArray();
        Bitmap samplaeBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        return samplaeBitmap;
    }


    private void lunBanPress(String path) {
        String pressPath = Environment.getExternalStorageDirectory().getPath();
        Luban.with(getContext())
                .load(path)                                   // 传人要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .setTargetDir(pressPath)                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        printLog("开始鲁班压缩");
                    }

                    @Override
                    public void onSuccess(File file) {
                        printLog("鲁班压缩成功");
                        //进行上传
                        startServicetoUpload(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("鲁班压缩出错");

                    }
                }).launch();    //启动压缩
    }

    private void startServicetoUpload(final File file) {
        Intent intent = new Intent();
        intent.setClass(getContext(), DownloadService.class);
        getContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DownloadService.DownloadBinder investUserBinder = (DownloadService.DownloadBinder) service;
                investUserBinder.getSerice().upload_pictures(file, new DownloadService.UserImageDownloadListener() {
                    @Override
                    public void success(UpLoadBean upLoadBean) {
                        printLog("上传成功");
                        Glide.with(getContext()).load(FinanCilalConstant.BaseUrl + upLoadBean.getResult())
                                .transform(new CircleCrop())
                                .into(ivHead);
                    }

                    @Override
                    public void fail() {
                        showMsg("上传失败");
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }


}
