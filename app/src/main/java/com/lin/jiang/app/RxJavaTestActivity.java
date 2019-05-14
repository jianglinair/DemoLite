package com.lin.jiang.app;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);

        //        baseUsage();
        //        schedule();
        //        map();
        //        zip();
        //        concat();
        //        flatMap();
        //        concatMap();
        distinct();
    }

    /**
     * 基本用法
     */
    private void baseUsage() {
        Log.d("[jianglin]", "RxJavaTestActivity.baseUsage: ==============");
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
        Log.d("[jianglin]", "RxJavaTestActivity.baseUsage: ================");
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

    /**
     * 线程调度
     */
    private void schedule() {
        /*=========================================================================
         * 1. Observer（观察者）的onSubscribe()方法运行在当前线程中。
         * 2. Observable（被观察者）中的subscribe()运行在subscribeOn()指定的线程中。
         * 3. Observer（观察者）的onNext()和onComplete()等方法运行在observeOn()指定的线程中。
         *========================================================================*/
        Log.d("[jianglin]", "RxJavaTestActivity.schedule: ================");
        new Thread("thread-schedule") {
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

    /**
     * map 变换
     */
    private void map() {
        Log.d("[jianglin]", "RxJavaTestActivity.map: ===================");
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

    private String format = "%1$s%2$d";

    /**
     * zip 变换
     */
    private void zip() {
        /*=========================================================================
         * zip 专用于合并事件，该合并不是连接（concat），而是两两配对，也就意味着，
         * 最终配对出的 Observable 发射事件数目只和少的那个相同。
         *========================================================================*/
        Log.d("[jianglin]", "RxJavaTestActivity.zip: =============");
        Observable.zip(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable1 emit A");
                emitter.onNext("A");
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable1 emit B");
                emitter.onNext("B");
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable1 emit C");
                emitter.onNext("C");
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable1 emit D");
                emitter.onNext("D");
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable1 emit E");
                emitter.onNext("E");
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable1 Complete");
                emitter.onComplete();
            }
        }), Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable2 emit 1");
                emitter.onNext(1);
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable2 emit 2");
                emitter.onNext(2);
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable2 emit 3");
                emitter.onNext(3);
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable2 emit 4");
                emitter.onNext(4);
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: Observable2 Complete");
                emitter.onComplete();
            }
        }), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                Log.d("[jianglin]", "RxJavaTestActivity.apply: s=" + s + ", i=" + integer);
                return String.format(format, s, integer);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("[jianglin]", "RxJavaTestActivity.onSubscribe: Observer subscribed.");
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

    /**
     * concat 变换
     */
    private void concat() {
        Log.d("[jianglin]", "RxJavaTestActivity.concat: ==========");
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("[jianglin]", "RxJavaTestActivity.accept: " + integer);
                    }
                });
    }

    /**
     * flatMap 变换
     */
    private void flatMap() {
        /*=========================================================================
         * 初始 Observable 发送一组事件，每个事件在 flatMap 中都被变换成一组事件（即一个新的Observable）。
         * flatMap 并不能保证事件的顺序，如果需要保证，需要用到 concatMap
         *========================================================================*/
        Log.d("[jianglin]", "RxJavaTestActivity.flatMap: ===========");
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: emit 1");
                emitter.onNext(1);
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: emit 2");
                emitter.onNext(2);
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: emit 3");
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    Log.d("[jianglin]", "RxJavaTestActivity.apply: " + integer);
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
                //                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("[jianglin]", "RxJavaTestActivity.accept: " + s);
            }
        });
    }

    /**
     * concatMap 变换
     */
    private void concatMap() {
        /*=========================================================================
         * 与 flatMap 不同的是，concatMap 能够保证事件顺序
         *========================================================================*/
        Log.d("[jianglin]", "RxJavaTestActivity.concatMap: ===========");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: emit A");
                emitter.onNext("A");
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: emit B");
                emitter.onNext("B");
                Log.d("[jianglin]", "RxJavaTestActivity.subscribe: emit C");
                emitter.onNext("C");
                emitter.onComplete();
            }
        }).concatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    Log.d("[jianglin]", "RxJavaTestActivity.apply: " + integer);
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
                //                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("[jianglin]", "RxJavaTestActivity.accept: " + s);
            }
        });
    }

    /**
     * distinct 变换
     */
    private void distinct() {
        /*=========================================================================
         * distinct 变换即去重。
         *========================================================================*/
        Log.d("[jianglin]", "RxJavaTestActivity.distinct: =============");
        Observable.just(1, 1, 1, 2, 2, 3, 3, 4, 4, 5).distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("[jianglin]", "RxJavaTestActivity.onNext: " + integer);
                    }
                });
    }
}
