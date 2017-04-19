package succulent.jiuge.com.caipiaoview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by wdd on 2017/3/9.
 */

public class DiatchFrameLayout extends FrameLayout {
  public DiatchFrameLayout(Context context) {
    super(context);
  }

  public DiatchFrameLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public DiatchFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    MotionEvent obtain = MotionEvent.obtain(ev);
    obtain.setLocation(scrollView.getWidth()/2,obtain.getY());

    MotionEvent obtain1=null;
    View view=null;
   // lineLayoutParent.dispatchTouchEvent(ev);
   // lineLayoutParent.dispatchTouchEvent(ev);
    obtain1=MotionEvent.obtain(ev);
    obtain1.setLocation(ev.getX(),h.getHeight()/2);
    h.dispatchTouchEvent(obtain1);
    for (int i=0;i<lineLayoutParent.getChildCount();i++){
      view=  lineLayoutParent.getChildAt(i);
      obtain1=MotionEvent.obtain(ev);
      obtain1.setLocation(ev.getX(),i*view.getHeight()+view.getHeight()/2);
      view.dispatchTouchEvent(obtain1);
     // view.getParent().requestDisallowInterceptTouchEvent(false);
    }
    scrollView.onTouchEvent(obtain);


    return true;
  }
  LinearLayout lineLayoutParent;
  ScrollView scrollView;

  public void setLineLayoutParent(LinearLayout lineLayoutParent){
    this.lineLayoutParent=lineLayoutParent;
  }
  public void setScrollView(ScrollView scrollView){
    this.scrollView=scrollView;
  }
  HorizontalScrollView h;
  public void setTopHScrollView(HorizontalScrollView h){
    this.h=h;

  }
}
