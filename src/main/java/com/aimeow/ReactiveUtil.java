package com.aimeow;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;

/**
 * Created on 2019/1/15 3:18 PM
 *
 * @author zhaoyi.w <zhaoyi.w@alibaba-inc.com>
 */
public class ReactiveUtil {

    public static <T> T getFlowableObject(Flowable<T> flowable) throws Exception {
        List<T> list = new ArrayList<>();
        List<Throwable> throwables = new ArrayList<>();

        flowable.subscribe(t -> list.add(t),
            err -> throwables.add(err));

        if (throwables.size() != 0) {
            throw new Exception(throwables.get(0));
        }

        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public static <T extends Subscriber>  T[] generateSubscriber(Class<T> cls) {
        try {
            int num = Runtime.getRuntime().availableProcessors();
            List<T> list = new ArrayList<>();

            for (int i = 0; i < num; i++ ) {
                list.add(cls.newInstance());
            }

            T[] array = (T[]) Array.newInstance(cls, list.size());
            return list.toArray(array);
        } catch (Exception ex) {
            return (T[]) Array.newInstance(cls, 0);
        }
    }

    public static void printAllThreads() {

        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;

        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }

        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];

        int actualSize = topGroup.enumerate(slackList);

        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        System.out.println("Thread list size == " + list.length);
        for (Thread thread : list) {
            System.out.println(thread.getName());
        }
    }

    public static <T> void print(T str) {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " thread : " + str);
    }
}
