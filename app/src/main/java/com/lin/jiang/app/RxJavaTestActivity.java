package com.lin.jiang.app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);

        //        example01();
        example02();
        //        example03();
    }

    private void example01() {
        Log.d("[jianglin]", "RxJavaTestActivity.example01: ========");
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: thread=" + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
                // onComplete() 之后还是会发送事件，但不会再接收
                emitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("[jianglin]", "RxJavaTestActivity.onSubscribe: thread=" + Thread.currentThread().getName());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("[jianglin]", "RxJavaTestActivity.onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("[jianglin]", "RxJavaTestActivity.onError: ");
            }

            @Override
            public void onComplete() {
                Log.d("[jianglin]", "RxJavaTestActivity.onComplete: ");
            }
        });
        Log.d("[jianglin]", "RxJavaTestActivity.example01: ========");
        Observable.just(1, 2, 3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("[jianglin]", "RxJavaTestActivity.onSubscribe: thread=" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("[jianglin]", "RxJavaTestActivity.onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("[jianglin]", "RxJavaTestActivity.onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("[jianglin]", "RxJavaTestActivity.onComplete: ");
                    }
                });
    }

    private void example02() {
        /*=========================================================================
         * 1. Observer（观察者）的onSubscribe()方法运行在当前线程中。
         * 2. Observable（被观察者）中的subscribe()运行在subscribeOn()指定的线程中。
         * 3. Observer（观察者）的onNext()和onComplete()等方法运行在observeOn()指定的线程中。
         *========================================================================*/
        new Thread("thread-example02") {
            @Override
            public void run() {
                Log.d("[jianglin]", "RxJavaTestActivity.run: 当前线程 " + Thread.currentThread().getName());
                Observable
                        .create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable#subscribe() running in " + Thread.currentThread().getName());
                                emitter.onNext("第一条消息");
                                emitter.onNext("第二条消息");
                                emitter.onNext("第三条消息");
                                emitter.onComplete();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d("[jianglin]", "RxJavaTestActivity.onSubscribe: Observer#onSubscribe() running in " + Thread.currentThread().getName());
                            }

                            @Override
                            public void onNext(String s) {
                                Log.d("[jianglin]", "RxJavaTestActivity.onNext: s=" + s + " Observer#onNext() running in " + Thread.currentThread().getName());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("[jianglin]", "RxJavaTestActivity.onError: Observer#onError() running in " + Thread.currentThread().getName());
                            }

                            @Override
                            public void onComplete() {
                                Log.d("[jianglin]", "RxJavaTestActivity.onComplete: Observer#onComplete() running in " + Thread.currentThread().getName());
                            }
                        });
            }
        }.start();
    }

    private void example03() {
        Log.d("[jianglin]", "RxJavaTestActivity.example03: ========");
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: thread=" + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
                // onComplete() 之后还是会发送事件，但不会再接收
                emitter.onNext(4);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "Observable emitted " + integer;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("[jianglin]", "RxJavaTestActivity.onSubscribe: thread=" + Thread.currentThread().getName());
                Log.d("[jianglin]", "RxJavaTestActivity.onSubscribe: Disposable=" + d.isDisposed());
            }

            @Override
            public void onNext(String s) {
                Log.d("[jianglin]", "RxJavaTestActivity.onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("[jianglin]", "RxJavaTestActivity.onError: ");
            }

            @Override
            public void onComplete() {
                Log.d("[jianglin]", "RxJavaTestActivity.onComplete: ");
            }
        });
    }
}
