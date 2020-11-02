package com.bw.net.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bw.common.FinanCilalConstant;
import com.bw.net.manager.RetrofitManager;
import com.bw.net.mode.UpLoadBean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class DownloadService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new DownloadBinder();
    }


    public class DownloadBinder extends Binder {
        public DownloadService getSerice() {
            return DownloadService.this;
        }
    }


    /**
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String saveDir, final OnDownloadListener listener) {

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        final Request request = new Request.Builder()
                .url(FinanCilalConstant.DownLoadUrl)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                BufferedOutputStream bos = null;
                BufferedInputStream bis = null;
                try {
                    byte[] buf = new byte[45];
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    bis = new BufferedInputStream(is);
                    listener.onFileLength(total);
                    File file = new File(saveDir, FinanCilalConstant.FineName);
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    long sum = 0;
                    int len = 0;
                    while ((len = bis.read(buf)) != -1) {
                        sum += len;
                        listener.onDownloadingnum(sum);
                        int progress = (int) (sum * 1.0f / total * 100);
                        listener.onDownloading(progress);
                        bos.write(buf, 0, len);
                    }
                    bos.flush();
                    // 下载完成
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onDownloadFailed();
                } finally {
                    try {
                        is.close();
                        bis.close();
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void upload_pictures(final File file, final UserImageDownloadListener userImageDownloadListener) {
        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RetrofitManager.getFinancialService().uploadOneFile(fileRQ)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpLoadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpLoadBean upLoadBean) {
                        if (upLoadBean.getCode().equals("200")) {
                            userImageDownloadListener.success(upLoadBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        userImageDownloadListener.fail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void downloaduserimage(String headurl, final String filename, final DoLoadUseImageListener doLoadUseImageListener) {
        RetrofitManager.getFinancialService().downloadFile(headurl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        InputStream is = responseBody.byteStream();
                        BufferedInputStream bis = null;
                        BufferedOutputStream bos = null;
                        try {
                            bis = new BufferedInputStream(is);
                            File file = new File("/sdcard/DCIM/Camera", filename);
                            bos = new BufferedOutputStream(new FileOutputStream(file));
                            int len = 0;
                            byte bys[] = new byte[1024];
                            while ((len = bis.read(bys)) != -1) {
                                bos.write(bys, 0, len);
                            }
                            bis.close();
                            bos.close();
                            doLoadUseImageListener.success();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            doLoadUseImageListener.fail();
                        } catch (IOException e) {
                            e.printStackTrace();
                            doLoadUseImageListener.fail();
                        } finally {
                            try {
                                if (bis != null) {
                                    bis.close();
                                }
                                if (bos != null) {
                                    bos.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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








    public interface DoLoadUseImageListener {
        void success();

        void fail();
    }


    public interface UserImageDownloadListener {
        void success(UpLoadBean upLoadBean);

        void fail();
    }


    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(File file);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);


        void onDownloadingnum(long num);

        void onFileLength(long length);


        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}