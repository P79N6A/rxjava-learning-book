package com.aimeow;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.parallel.ParallelFlowable;
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
        Flowable a = Flowable.range(1, 10).filter(num -> num < 5)
            .switchIfEmpty(Flowable.just(5)).defaultIfEmpty(6);

        a.subscribe(num -> {
            print(num);
        });
        return this;
    }

    RxJavaDemos rxtest3() {
        Flowable.range(1, 670)
            .parallel()
            .runOn(Schedulers.computation())
            .flatMap(num -> {
                print(num);
                return Flowable.just(num + 10);
            })
            .sequential()
            .subscribe(num -> {
                print(num);
            });
        return this;
    }

    RxJavaDemos rxtest4() {
        Flowable.range(1, 10)
            .subscribeOn(Schedulers.io())
            .flatMap(num -> {
                print(num);
                return Flowable.just(num);
            })
            .subscribeOn(Schedulers.io())
            .flatMap(num -> {
                print(num + 10);
                return Flowable.just(num);
            })
            .observeOn(Schedulers.computation())
            .subscribe(num -> {
                print("RESULT IS " + num);
            });
        return this;
    }

    RxJavaDemos rxtext5() {
        Flowable.merge(Flowable.just(10),Flowable.just(14),Flowable.just(15), Flowable.just("abc"))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .flatMap(obj -> {
                print(obj);
                return Flowable.just(obj);
            })
            .subscribe(obj -> {
                print(obj);
            });
        return this;
    }

    RxJavaDemos printLine() {
        System.out.println("========================================");
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
