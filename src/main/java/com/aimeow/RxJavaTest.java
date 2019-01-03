package com.aimeow;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class RxJavaTest {
    RxJavaTest RxjavaTest() {
        return this;
    }

    RxJavaTest rxtest1() {
        Flowable.range(1, 10)
                .parallel(8)
                .runOn(Schedulers.computation())
                .subscribe(generateSubscriber(CustomSubscriber.class));
        return this;
    }

    private CustomSubscriber[] getSubscriber() {
        try {
            int num = Runtime.getRuntime().availableProcessors();
            List<CustomSubscriber> list = new ArrayList<>();

            for (int i = 0; i < num; i++ ) {
                list.add(new CustomSubscriber());
            }
            CustomSubscriber[] array = new CustomSubscriber[list.size()];
            return list.toArray(array);
        } catch (Exception ex) {
            return new CustomSubscriber[0];
        }
    }

    private <T extends Subscriber> T[] generateSubscriber(Class<T> cls) {
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

    <T> RxJavaTest print(T str) {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " thread : " + str);
        return this;
    }

    RxJavaTest printAllThreads() {

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
        return this;
    }

    RxJavaTest delay(Long ms) {
        try {
            sleep(ms);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return this;
    }
}
