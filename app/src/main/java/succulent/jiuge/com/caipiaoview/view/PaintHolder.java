package succulent.jiuge.com.caipiaoview.view;

import android.graphics.Paint;

/**
 * Created by wdd on 2017/3/8.
 * 绘制字体操作，计算字体的大小，只计算一次
 */

public class PaintHolder {
  public float textBaseY = -1;
  Paint paint;
  float nuberHeiht;

  public PaintHolder(Paint paint, float nuberHeiht) {
    this.paint = paint;
    this.nuberHeiht = nuberHeiht;
  }


  /**
   *
   * @return
   */
  public float getTextBaseY() {
    if (textBaseY == -1) {
      Paint.FontMetrics fontMetrics = paint.getFontMetrics();
      float fontHeiht = fontMetrics.bottom - fontMetrics.top;
      float textBaseY = nuberHeiht - (nuberHeiht - fontHeiht) / 2 - fontMetrics.bottom;
      this.textBaseY = textBaseY;
    }
    return textBaseY;
  }

  public Paint getPaint(){
    return paint;
  }

}
