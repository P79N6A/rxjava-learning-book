package com.aimeow;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class CustomSubscriber implements Subscriber {
    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onNext(Object t) {
        Integer num = (Integer)t;
        print(num);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        print(subscription);
        subscription.request(1);
    }

    <T> void print(T str) {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " thread : " + str);
    }

}
