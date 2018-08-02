package com.soul.bonny.baselibrary.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 应用名称: BaseProject
 * 包 名 称:
 *
 * 文件描述: Closeable工具类
 * 创 建 人: so
 * 创建时间: 2015/11/4 17:26
 */
@SuppressWarnings("WeakerAccess") public final class CloseUtil {

    private CloseUtil() { }

    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Closeable... params) {
        if (null != params) {
            try {
                for (Closeable closeable : params) {
                    closeable.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
