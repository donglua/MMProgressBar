package com.meimeifa.lib.progressbar.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.meimeifa.lib.progressbar.MMProgressBar;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

  private MMProgressBar mmProgressBar;
  private Disposable disposable;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mmProgressBar = findViewById(R.id.progressbar);

    mmProgressBar.setProgress(0);

    disposable = Flowable.interval(2000,200, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Long>() {
          @Override public void accept(Long aLong) throws Exception {
            if (aLong >= 80) {
              disposable.dispose();
            }
            float progress = ((float) aLong) / 100;
            mmProgressBar.setProgress(progress);
          }
        });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }
}
