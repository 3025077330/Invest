package com.bw.framwork.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import com.bw.common.util.EncryptUtil;
import com.bw.net.manager.RetrofitManager;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class GlideUntils {
    private File fileDir;
    private Context context;
    private static GlideUntils instance;
    private DiskLruCache diskLruCache;
    private static Handler handler = new Handler();

    private GlideUntils() {

    }

    public static GlideUntils getInstance() {
        if (instance == null) {
            synchronized (GlideUntils.class) {
                if (instance == null) {
                    instance = new GlideUntils();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;

        fileDir = new File(context.getExternalCacheDir().getAbsolutePath() + "/1801/");
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        try {
//申请500MB资源
            diskLruCache = DiskLruCache.open(fileDir, 1, 5, 100 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(final TaskBean taskBean) {

        RetrofitManager.getFinancialService().downloadFile(taskBean.getStrurl())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        InputStream inputStream = responseBody.byteStream();//输入流

                        Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);
                        if (originalBitmap == null) {
                            return;
                        }

                        final Bitmap sampleBitmap = sampleBitmap(taskBean.getImageView().getMeasuredWidth(), taskBean.getImageView().getMeasuredHeight(), originalBitmap);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                taskBean.imageView.setImageBitmap(sampleBitmap);
                            }
                        });
                        originalBitmap.recycle();//把原图的bitmap释放掉


                        String key = EncryptUtil.enrypByMd5(taskBean.getStrurl() + taskBean.imageView.getTag());
                        try {
                            synchronized (diskLruCache) {
                                DiskLruCache.Editor edit = diskLruCache.edit(key);
                                OutputStream outputStream = edit.newOutputStream(0);//disklrucache的文件输出流
                                sampleBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);//将采集后bitmap存放到disklrucache里
                                edit.commit();
                                diskLruCache.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    public void getBitmapFromDiskLrucache(final String url, final int position) {
        //先获取key
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {

                String key = EncryptUtil.enrypByMd5(url + position);
                try {
                    synchronized (diskLruCache) {
                        DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                        InputStream inputStream = snapshot.getInputStream(0);
                        Bitmap sampleBitmap = BitmapFactory.decodeStream(inputStream);
                        e.onNext(sampleBitmap);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    e.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {

                    }

                    @Override
                    public void onError(Throwable e) {


                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private Bitmap samplePic(int width, int height, String filePath) {

        //第一次采样，主要采集图片边框，算出图片的尺寸
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//通过该标志位，确定第一次采样只采集边框
        BitmapFactory.decodeFile(filePath, options);
        //计算出图片的宽度和高度
        int picWidth = options.outWidth;
        int picHeight = options.outHeight;
        //计算出缩放比例
        int sampleSize = 1;
        while (picHeight / sampleSize > height || picWidth / sampleSize > width) {
            sampleSize = sampleSize * 2;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;


        return BitmapFactory.decodeFile(filePath, options);

    }


    private Bitmap sampleBitmap(int width, int height, Bitmap originalBitmap) {

        int picWidth = originalBitmap.getWidth();
        int picHeight = originalBitmap.getHeight();
        //计算出缩放比例
        int sampleSize = 1;
        while (picHeight / sampleSize > height || picWidth / sampleSize > width) {
            sampleSize = sampleSize * 2;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;//不是采集边框，而是按比例采集像素
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Bitmap samplaeBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        return samplaeBitmap;
    }


    public static class TaskBean {
        ImageView imageView;
        String strurl;

        public TaskBean(ImageView imageView, String strurl) {
            this.imageView = imageView;
            this.strurl = strurl;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public String getStrurl() {
            return strurl;
        }

        public void setStrurl(String strurl) {
            this.strurl = strurl;
        }
    }


}
