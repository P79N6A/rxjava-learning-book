package com.aimeow;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class RxJavaDemos {
    RxJavaDemos RxjavaTest() {
        return this;
    }

    RxJavaDemos rxtest1() {
        Flowable.range(1, 10)
                .parallel(8)
                .runOn(Schedulers.computation())
                .subscribe(ReactiveUtil.generateSubscriber(CustomSubscriber.class));
        return this;
    }

    RxJavaDemos rxtest2() {
        Flowable a = Flowable.range(1,10).filter(num -> num < 5)
            .switchIfEmpty(Flowable.just(5)).defaultIfEmpty(6);

        a.subscribe(num -> {
            print(num);
        });
        return this;
    }

    <T> RxJavaDemos print(T str) {
        ReactiveUtil.print(str);
        return this;
    }

    RxJavaDemos printAllThreads() {
        ReactiveUtil.printAllThreads();
        return this;
    }

    RxJavaDemos delay(Long ms) {
        try {
            sleep(ms);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return this;
    }
}
