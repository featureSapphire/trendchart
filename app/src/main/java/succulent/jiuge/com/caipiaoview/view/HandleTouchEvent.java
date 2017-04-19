package succulent.jiuge.com.caipiaoview.view;

import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;

import succulent.jiuge.com.caipiaoview.view.viewbean.Attributes;

/**
 * Created by wdd on 2017/3/3.
 * 处理滑动事件
 */

public class HandleTouchEvent {

  /**
   * x方向滑动的间隔距离
   */
  protected float distanceX;
  /**
   * y方向滑动的间隔距离
   */
  protected float distanceY;
  /**
   * 上次x坐标
   */
  protected float lastX;
  /**
   * 上次y坐标
   */
  protected float lastY;

  private float totalX;
  private float totalY;
  private View view;

  private Attributes attributesHepler;

  public HandleTouchEvent(Attributes attributesHepler, View view) {
    this.view = view;

    this.attributesHepler = attributesHepler;
  }

  /**
   * 区分竖直方向滑动，还是水平方向滑动，根据滑动的model 决定是否处理事件
   *
   * @param event
   * @return
   */
  public boolean onTouchEvent(MotionEvent event) {
    if (attributesHepler.notScrollModel()) {
      return false;
    }
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        lastX = event.getRawX();
        lastY = event.getRawY();
        view.getParent().requestDisallowInterceptTouchEvent(true);
        break;
      case MotionEvent.ACTION_MOVE:

        float currentX = event.getRawX();
        float currentY = event.getRawY();
        distanceX = currentX - lastX;
        distanceY = currentY - lastY;
        totalX = totalX + distanceX;
        totalY = totalY + distanceY;
        //水平方向滑动，但是不支持水平方向滑动
        if (attributesHepler.isScrollVModel()) {//可以竖直方向上滑动
          if (Math.abs(totalY) < Math.abs(totalX) && Math.abs(totalX) > 10) {
            view.getParent().requestDisallowInterceptTouchEvent(false);
            return false;
          }
        }
        if (attributesHepler.isScrollHModel()) {//可以竖直方向上滑动
          if (Math.abs(totalY) > Math.abs(totalX) && Math.abs(totalY) > 10) {
            view.getParent().requestDisallowInterceptTouchEvent(false);
            return false;
          }
        }

        Logger.i("MoveY" + distanceY, null);
        //  Log.i("MoveY", "MoveY" + distanceY);
        //可以执行
        if (onScrollListener != null) {
          onScrollListener.onDistanceChanger(distanceX, distanceY);
        }
        lastX = currentX;
        lastY = currentY;
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
        lastX = 0;
        lastY = 0;
        totalX = 0;
        totalY = 0;
        distanceY = 0;
        distanceX = 0;
        break;
    }
    return true;
  }

  private OnScrollListener onScrollListener;

  /**
   * 设置滑动监听
   *
   * @param onScrollListener
   */
  public void setOnScrollListener(OnScrollListener onScrollListener) {
    this.onScrollListener = onScrollListener;
  }

  public interface OnScrollListener {
    /**
     * 滑动的间距
     *
     * @param distanceX
     * @param distanceY
     */
    public void onDistanceChanger(float distanceX, float distanceY);
  }

}
