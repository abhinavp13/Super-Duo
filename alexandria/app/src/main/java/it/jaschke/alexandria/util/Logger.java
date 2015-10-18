package it.jaschke.alexandria.util;

import android.util.Log;

/**
 * This class can be used to log messages.
 *
 * Created by pabhinav
 */
public class Logger{

    /**
     * Tag which will be also printed in logs.
     */
    private static String Tag;

    /**
     * Singleton instance for this class.
     */
    private static Logger loggerInstance;

    /**
     * Disabling default construction.
     */
    private Logger() {}

    /**
     * Synchronized {@code Logger} access.
     *
     * @return Logger instance.
     */
    public static Logger getInstance(String Tag) {
        if (loggerInstance == null) {
            synchronized (Logger.class){
                if(loggerInstance == null){
                    loggerInstance = new Logger();
                }
            }
        }
        getLogger().setTag(Tag);
        return getLogger();
    }

    /**
     * Used to get logger instance
     *
     * @return {@link Logger}
     */
    private static Logger getLogger(){
        return loggerInstance;
    }

    /**
     * Used to set the tags for logs.
     *
     * @param Tag
     */
    public static void setTag(String Tag){
        Logger.Tag = Tag;
    }

    /**
     * Used to generate info logs.
     *
     * @param msg
     */
    public static void info(String msg){
        Log.i(Tag,msg);
    }

    /**
     * Used to generate info logs
     *
     * @param msg
     * @param thr
     */
    public static void info(String msg, Throwable thr){
        Log.i(Tag,msg,thr);
    }

    /**
     * Used to generate  warning logs.
     *
     * @param msg
     * @param thr
     */
    public static void warn(String msg, Throwable thr){
        Log.w(Tag, msg, thr);
    }

    /**
     * Used to generate warning logs.
     *
     * @param msg
     */
    public static void warn(String msg){
        Log.w(Tag,msg);
    }

    /**
     * Used to generate error logs.
     * @param msg
     * @param thr
     */
    public static void error(String msg, Throwable thr) {
        Log.e(Tag, msg, thr);
    }

}
