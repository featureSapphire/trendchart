package succulent.jiuge.com.caipiaoview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by wdd on 2017/3/9.
 */

public class DemoRecycyler extends AppCompatActivity {

  private DiatchFrameLayout fr;
  private LinearLayout lin_contanter;
  private ScrollView scrollView;
  private HorizontalScrollView header;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.recycleview);
    fr = (DiatchFrameLayout) findViewById(R.id.fr);
    lin_contanter = (LinearLayout) findViewById(R.id.lin_contanter);
    scrollView = (ScrollView) findViewById(R.id.scrollView);
    header = (HorizontalScrollView) findViewById(R.id.header);
    fr.setTopHScrollView(header);
    fr.setLineLayoutParent(lin_contanter);
    fr.setScrollView(scrollView);

  }
}
