package com.soul.bonny.baselibrary.util;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * 应用名称: BaseProject
 * 包 名 称:
 *
 * 文件描述: bitmap工具类
 * 创 建 人: so
 * 创建时间: 2015/11/4 17:26
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class BitmapUtil {

    private BitmapUtil() { }

    /**
     * 获取图片尺寸
     *
     * @param file File对象
     * @return 尺寸数组. 0:width,1:height
     */
    public static float[] getBitmapSize(@NonNull File file) {
        float[] size = new float[2];
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);
        // 获取图片的原始宽度高度
        size[0] = opts.outWidth;
        size[1] = opts.outHeight;
        return size;
    }

    /**
     * Byte[]转Bitmap
     */
    public static Bitmap bytes2Bitmap(@NonNull byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * Bitmap转Byte[]
     *
     * @param needRecycle 转换完毕后是否需要回收bitmap
     */
    public static byte[] bitmap2Bytes(@NonNull Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        CloseUtil.close(output);
        return result;
    }

    /**
     * Bitmap转Drawable
     */
    public static Drawable bitmap2Drawable(@NonNull Resources res, @NonNull Bitmap bitmap) {
        return new BitmapDrawable(res, bitmap);
    }

    /**
     * Drawable转Bitmap
     */
    public static Bitmap drawable2Bitmap(@NonNull Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * Bitmap对象转换TransitionDrawable对象.
     *
     * @param bitmap 要转化的Bitmap对象 imageView.setImageDrawable(td);
     *               td.startTransition(200);
     * @return Drawable 转化完成的Drawable对象
     */
    @SuppressWarnings("ResourceType")
    public static TransitionDrawable bitmap2TransitionDrawable(@NonNull Bitmap bitmap) {
        TransitionDrawable mBitmapDrawable = null;
        try {
            mBitmapDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(android.R.color.transparent), new BitmapDrawable(null, bitmap)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }

    /**
     * Drawable对象转换TransitionDrawable对象.
     *
     * @param drawable 要转化的Drawable对象 imageView.setImageDrawable(td);
     *                 td.startTransition(200);
     * @return Drawable 转化完成的Drawable对象
     */
    @SuppressWarnings("ResourceType")
    public static TransitionDrawable drawable2TransitionDrawable(@NonNull Drawable drawable) {
        TransitionDrawable mBitmapDrawable = null;
        try {
            mBitmapDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(android.R.color.transparent), drawable});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }

    /**
     * 将ImageView转换为Bitmap.
     *
     * @param view 要转换为bitmap的View
     * @return byte[] 图片的byte[]
     */
    public static Bitmap imageView2Bitmap(@NonNull ImageView view) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 旋转图像
     *
     * @param bmp     Bitmap
     * @param degrees 旋转角度
     */
    public static Bitmap rotateBitmap(@NonNull Bitmap bmp, int degrees) {
        if (degrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees % 360);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }
        return bmp;
    }

    /**
     * 得到bitmap的大小
     */
    @SuppressLint("ObsoleteSdkInt")
    public static int getBitmapSize(@NonNull Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) { //API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight(); //earlier version
    }

    /**
     * 转换图片转换成圆形.
     *
     * @param bitmap 传入Bitmap对象
     * @return the bitmap
     */
    public static Bitmap toRoundBitmap(@NonNull Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int roundPx = (width <= height) ? (width / 2) : (height / 2);
        return toRoundBitmap(bitmap, roundPx);
    }

    /**
     * 转换图片转换成圆角.
     *
     * @param bitmap 传入Bitmap对象
     * @return the bitmap
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public static Bitmap toRoundBitmap(@NonNull Bitmap bitmap, int roundPx) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float left;
        float top;
        float right;
        float bottom;
        float dstLeft;
        float dstTop;
        float dstRight;
        float dstBottom;
        if (width <= height) {
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dstLeft = 0;
            dstTop = 0;
            dstRight = width;
            dstBottom = width;
        } else {
            float clip = (width - height) / 2F;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dstLeft = 0;
            dstTop = 0;
            dstRight = height;
            dstBottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dstLeft, (int) dstTop, (int) dstRight, (int) dstBottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 转换图片转换成镜面效果的图片.
     *
     * @param bitmap 传入Bitmap对象
     * @return the bitmap
     */
    public static Bitmap toReflectionBitmap(@NonNull Bitmap bitmap) {
        try {
            int reflectionGap = 1;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            // This will not scale but will flip on the Y axis
            Matrix matrix = new Matrix();
            matrix.preScale(1, -1);

            // Create a Bitmap with the flip matrix applied to it.
            // We only want the bottom half of the image
            Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);

            // Create a new bitmap with same width but taller to fit
            // reflection
            Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

            // Create a new Canvas with the bitmap that's big enough for
            // the image plus gap plus reflection
            Canvas canvas = new Canvas(bitmapWithReflection);
            // Draw in the original image
            canvas.drawBitmap(bitmap, 0, 0, null);
            // Draw in the gap
            Paint defaultPaint = new Paint();
            canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
            // Draw in the reflection
            canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
            // Create a shader that is a linear gradient that covers the
            // reflection
            Paint paint = new Paint();
            LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
            // Set the paint to use this shader (linear gradient)
            paint.setShader(shader);
            // Set the Transfer mode to be porter duff and destination in
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            // Draw a rectangle using the paint with our linear gradient
            canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

            bitmap = bitmapWithReflection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 按比例取得缩略图
     */
    public static BitmapFactory.Options getOptionsWithInSampleSize(@NonNull String filePath, int maxWidth) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true; // 只取得outHeight(图片原始高度)和
        // outWidth(图片的原始宽度)而不加载图片
        BitmapFactory.decodeFile(filePath, bitmapOptions);
        bitmapOptions.inJustDecodeBounds = false;
        // 应该直接除160的，但这里出16是为了增加一位数的精度
        int inSampleSize = bitmapOptions.outWidth / (maxWidth / 10);
        if (inSampleSize % 10 != 0) {
            inSampleSize += 10; // 尽量取大点图片，否则会模糊
        }
        inSampleSize = inSampleSize / 10;
        if (inSampleSize <= 0) { // 判断200是否超过原始图片高度
            inSampleSize = 1; // 如果超过，则不进行缩放
        }
        bitmapOptions.inSampleSize = inSampleSize;
        return bitmapOptions;
    }

    /**
     * 释放Bitmap
     */
    public static void release(@NonNull Bitmap... bitmaps) {
        try {
            for (Bitmap bitmap : bitmaps) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
