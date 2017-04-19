package succulent.jiuge.com.caipiaoview.view.helper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import succulent.jiuge.com.caipiaoview.view.viewbean.NoDataBean;
import succulent.jiuge.com.caipiaoview.view.viewbean.NumberBean;

/**
 * Created by wdd on 2017/2/27.
 */

public class DrawChartHelper {

  /**
   * 绘制选中的背景，不同的形状和不同的颜色
   *
   * @param canvas
   * @param bean
   * @param selectBgPaint
   */
  public static void drawSelectBg(Canvas canvas, NumberBean bean, Paint selectBgPaint) {
    NumberBean.SelectBgBean selectBg = bean.selectBg;
    selectBgPaint.setColor(selectBg.bgColor);
    float nuberWidth = bean.width;
    float nuberHeiht = bean.heith;
    switch (selectBg.bgType) {
      case NumberBean.SelectBgBean.Bg_TYPE_CIRE://绘制圆形背景
        canvas.drawCircle(bean.x + nuberWidth / 2, bean.y + nuberHeiht / 2, Math.min(nuberWidth / 2,nuberHeiht/2) - bean.selectBg.paddingLeft, selectBgPaint);
        break;
      case NumberBean.SelectBgBean.Bg_TYPE_RECT://绘制矩形背景
        canvas.drawRect(bean.x + selectBg.paddingLeft
            , bean.y + selectBg.paddingTop
            , bean.x + bean.width - selectBg.paddingRight
            , bean.y + bean.heith - selectBg.paddingBottom, selectBgPaint);
        break;
    }
  }

  public static float noramltextBaseY = -1;

  /**
   * 绘制数字,可以绘制选中的没有选中，选中的的按照bgSelect中的颜色绘制，没有选中的按照Number中的 normalTextColor
   * 绘制
   *
   * @param canvas
   * @param bean
   * @param whileTextPaint
   */
  public static void drawText(Canvas canvas, NumberBean bean, Paint whileTextPaint, float textBaseY) {
    whileTextPaint.setColor(bean.isSelect ? bean.selectBg.textColor : bean.normalTextColor);
    whileTextPaint.setTextSize(bean.normalTextSize);
    float nuberWidth = bean.width;
    whileTextPaint.setTextAlign(Paint.Align.CENTER);
    canvas.drawText(bean.data, bean.x + nuberWidth / 2, bean.y + textBaseY, whileTextPaint);
  }

  /**
   * 在有上面脚标的时候调用,在有脚标的时候，绘制背景的时候，就不要设置pading否则会很怪
   * 绘制sup  背景  文字
   *
   * @param canvas
   * @param bean
   * @param bgpaint
   * @param textPaint
   */
  public static void drawSupTop(Canvas canvas, NumberBean bean, Paint bgpaint, Paint textPaint) {
    if (bean.selectBg.supTop.percentX == 0f) {//在没有相对位置的时候，直接根据右上角绘制
      /**
       * 圆心X
       */
      float x = bean.x + bean.width - bean.selectBg.supTop.r;
      /**
       * 圆心Y
       */
      float y = bean.y + bean.selectBg.supTop.r;
      /**
       * 绘制背景
       */
      bgpaint.setColor(bean.selectBg.supTop.bgColor);
      canvas.drawCircle(x, y, bean.selectBg.supTop.r, bgpaint);
      /**
       * 绘制字体
       */
      float textX = bean.x + bean.width - 2 * bean.selectBg.supTop.r;
      float textY = bean.y;
      textPaint.setTextSize(bean.selectBg.supTop.textSize);
      textPaint.setColor(bean.selectBg.supTop.textColor);
      float nuberHeiht = 2 * bean.selectBg.supTop.r;
      float nuberWidth = 2 * bean.selectBg.supTop.r;
      textPaint.setTextAlign(Paint.Align.CENTER);
      Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
      float fontHeiht = fontMetrics.bottom - fontMetrics.top;
      float textBaseY = nuberHeiht - (nuberHeiht - fontHeiht) / 2 - fontMetrics.bottom;
      canvas.drawText(bean.selectBg.supTop.data, textX + nuberWidth / 2, textY + textBaseY, textPaint);
    } else {//根据指定坐标，圆心绘制，后期待开发

    }
   /* NumberBean.SupTop supTop = bean.selectBg.supTop;
    float percentX = supTop.percentX;
    float percentY = supTop.percentY;
    *//**
     * 圆心位置X
     *//*
    float x = bean.x + percentX * bean.width;
    *//**
     * 圆心位置
     *//*
    float y = bean.y + percentY * bean.heith;
    bgpaint.setColor(supTop.bgColor);
    *//**
     * 绘制背景
     *//*
    canvas.drawCircle(x, y, supTop.r - bean.selectBg.paddingLeft, bgpaint);
    *//**
     * 绘制字体
     *//*
    textPaint.setColor(bean.selectBg.supTop.textColor);
    float nuberHeiht = bean.selectBg.supTop.r*2;
    float nuberWidth = nuberHeiht;
    float supX=x+bean.selectBg.supTop.r;
    float supY=y+bean.selectBg.paddingTop;
    textPaint.setTextAlign(Paint.Align.CENTER);
    Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
    float fontHeiht = fontMetrics.bottom - fontMetrics.top;
    float textBaseY = nuberHeiht - (nuberHeiht - fontHeiht) / 2 - fontMetrics.bottom;
    canvas.drawText(bean.data, bean.x + nuberWidth / 2, bean.y + textBaseY, whileTextPaint);*/

  }

  /**
   * 绘制矩形背景
   *
   * @param canvas
   * @param bean
   * @param paint
   */
  public static void drawRectBg(Canvas canvas, NumberBean bean, Paint paint) {
    RectF rectF = new RectF(bean.x, bean.y, bean.x + bean.width, bean.y + bean.heith);
    canvas.drawRect(rectF, paint);
  }

  /**
   * 画分割线,根据bean数据绘制
   *
   * @param canvas
   * @param bean
   */
  public static void drawVDeivedLine(Canvas canvas, NumberBean bean, Paint linePaint) {
    float move = linePaint.getStrokeWidth() / 2;
    canvas.drawLine(bean.x + bean.width + move, bean.y, bean.x + bean.width + move, bean.y + bean.heith, linePaint);
  }

  /**
   * 画分割线,根据bean数据绘制
   *
   * @param canvas
   * @param bean
   */
  public static void drawHDeivedLine(Canvas canvas, NumberBean bean, Paint linePaint) {
    float move = linePaint.getStrokeWidth() / 2;
    canvas.drawLine(bean.x, bean.y + bean.heith + move, bean.x + bean.width, bean.y + bean.heith + move, linePaint);
  }

  /**
   * 在外面设置字体的大小和字体的颜色,绘制没有数据的时候的字体
   *
   * @param canvas
   * @param bean
   * @param textPaint
   * @param TrancelentX
   * @param TrancelentY
   * @param width  绘制字体的宽度区域
   */
  public static void drawNODataText(Canvas canvas, NoDataBean bean, Paint textPaint, float TrancelentX, float TrancelentY, float width) {
    float nuberHeiht = bean.getHeight();
    float nuberWidth = width;
    textPaint.setTextAlign(Paint.Align.CENTER);
    Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
    float fontHeiht = fontMetrics.bottom - fontMetrics.top;
    float textBaseY = nuberHeiht - (nuberHeiht - fontHeiht) / 2 - fontMetrics.bottom;
    canvas.drawText(bean.desc, bean.getX(TrancelentX) + nuberWidth / 2, bean.getY(TrancelentY) + textBaseY, textPaint);
  }

}
