package com.soul.bonny.baselibrary.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * 应用名称: BaseProject
 * 包 名 称:
 *
 * 文件描述: Toast工具类
 * 创 建 人: so
 * 创建时间: 2015/11/4 17:26
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class ToastUtil {

    private ToastUtil() { }

    public static void showToast(@NonNull Context context, @NonNull String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@NonNull Context context, int resId) {
        Toast.makeText(context, context.getResources().getText(resId), Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(@NonNull Context context, @NonNull String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(@NonNull Context context, int resId) {
        Toast.makeText(context, context.getResources().getText(resId), Toast.LENGTH_LONG).show();
    }
}
