package succulent.jiuge.com.caipiaoview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import succulent.jiuge.com.caipiaoview.R;
import succulent.jiuge.com.caipiaoview.view.helper.DrawChartHelper;
import succulent.jiuge.com.caipiaoview.view.viewbean.Attributes;
import succulent.jiuge.com.caipiaoview.view.viewbean.LineNumberBean;
import succulent.jiuge.com.caipiaoview.view.viewbean.NumberBean;

import static com.orhanobut.logger.Logger.init;

/**
 * Created by wdd on 2017/2/27.
 */

public abstract class BastChartView extends View implements HandleTouchEvent.OnScrollListener {
  protected PaintHolder centerTextpaint;
  /**
   * 选中的背景
   */
  protected Paint selectBgPaint;
  /**
   * 画分割线
   */
  protected Paint linePaint;
  /**
   * 偶数行的背景
   */
  protected Paint ouBgPaint;
  /**
   * 奇数行的背景
   */
  protected Paint jiBgPaint;

  /**
   * 上面背景的颜色
   */
  protected Paint topBgColorpaint;
  /**
   * 小球和小球之间连接线的背景
   */
  protected Paint ballConnectBg;
  /**
   * 被选中的字体画笔
   */
  protected Paint selectTextPaint;
  /**
   * 没有被选中字体的画笔
   */
  protected Paint unselectTextPaint;
  protected Attributes attributesHepler = new Attributes();
  private HandleTouchEvent handleTouchEvent;
  protected int vStartPosion;
  protected int vEndPosition;
  protected int hStartPosition;
  protected int hEndPosition;

  public BastChartView(Context context) {
    super(context);
    attributesHepler.init(context, null);
    initHandleTouchEvent();
    initDefault();
  }

  /**
   * 返回宽高，位置0，对应宽，位置1对应高度
   *
   * @return
   */
  protected List<Float> obtainWH() {
    ArrayList<Float> wh = new ArrayList<Float>();
    if (attributesHepler.isScrollVModel() || attributesHepler.notScrollModel()) {//竖直方向的滑动，lineNumber 一行有多少数字，直接计算，根据宽高
      int line_number = attributesHepler.line_number;
      float width = (getWidth() - line_number * attributesHepler.lineWidth + attributesHepler.lineWidth / 2) / line_number;
      wh.add(width);
      wh.add(width);
    } else if (attributesHepler.isScrollHModel()) {//基本不存在，之后在处理
      int row_number = attributesHepler.row_number;
      float width = getWidth() / row_number;
      wh.add(width);
      wh.add(width);
    } else if (attributesHepler.isScrollAll()) {//宽，高
      wh.add(attributesHepler.nuberWidth);
      wh.add(attributesHepler.nuberHeiht);
    }
    attributesHepler.nuberWidth = wh.get(0);
    attributesHepler.nuberHeiht = wh.get(1);
    top = attributesHepler.nuberHeiht;
    return wh;
  }

  protected void initHandleTouchEvent() {
    handleTouchEvent = new HandleTouchEvent(attributesHepler, this);
    handleTouchEvent.setOnScrollListener(this);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    initPosition();
  }

  protected void initPosition() {
    vStartPosion = getVStartPosion();
    vEndPosition = getVEndPosition(vStartPosion);
    hStartPosition = getHStartPosition();
    hEndPosition = getHEndPosition(hStartPosition);
  }

  ;

  public BastChartView(Context context, AttributeSet attrs) {
    super(context, attrs);
    attributesHepler.init(context, attrs);
    initHandleTouchEvent();
    initDefault();
  }

  public BastChartView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    attributesHepler.init(context, attrs);
    initHandleTouchEvent();
    initDefault();
  }

  protected void initPaint() {
    selectBgPaint = new Paint();
    selectBgPaint.setStyle(Paint.Style.FILL);
    selectBgPaint.setAntiAlias(true);
    selectBgPaint.setColor(attributesHepler.select_bgcolor);

    linePaint = new Paint();
    linePaint.setColor(attributesHepler.line_color);
    linePaint.setAntiAlias(true);
    linePaint.setStrokeWidth(attributesHepler.lineWidth);
    linePaint.setStyle(Paint.Style.FILL);

    ballConnectBg = new Paint();
    ballConnectBg.setColor(attributesHepler.connect_line_color);
    ballConnectBg.setAntiAlias(true);
    ballConnectBg.setStrokeWidth(attributesHepler.connect_line_width);
    ballConnectBg.setStyle(Paint.Style.STROKE);

    ouBgPaint = new Paint();
    ouBgPaint.setStyle(Paint.Style.FILL);
    ouBgPaint.setAntiAlias(true);
    ouBgPaint.setColor(attributesHepler.normal_oubg_color);

    jiBgPaint = new Paint();
    jiBgPaint.setColor(attributesHepler.normal_jibg_color);
    jiBgPaint.setAntiAlias(true);
    jiBgPaint.setStyle(Paint.Style.FILL);

    topBgColorpaint = new Paint();
    topBgColorpaint.setColor(attributesHepler.top_list_bgcolor);
    topBgColorpaint.setAntiAlias(true);
    topBgColorpaint.setStyle(Paint.Style.FILL);

    selectTextPaint = new Paint();
    selectTextPaint.setColor(Color.WHITE);
    selectTextPaint.setTextAlign(Paint.Align.CENTER);
    selectTextPaint.setAntiAlias(true);
    selectTextPaint.setTextSize(attributesHepler.normal_textsize);
    selectTextPaint.setStyle(Paint.Style.STROKE);

    unselectTextPaint = new Paint();
    unselectTextPaint.setColor(getResources().getColor(R.color.gray_text));
    unselectTextPaint.setTextAlign(Paint.Align.CENTER);
    unselectTextPaint.setAntiAlias(true);
    unselectTextPaint.setTextSize(attributesHepler.normal_textsize);
    unselectTextPaint.setStyle(Paint.Style.STROKE);
  }

  /**
   * 离左边的距离
   */
  protected float left;
  /**
   * 离上边的距离
   */
  protected float top;
  /**
   * 当前的x
   */
  protected float currentX;
  /**
   * 当前的Y
   */
  protected float currentY;

  /**
   * 水平方向滑动的距离
   */
  protected float TrancelentX;
  /**
   * 竖直方向滑动的距离
   */
  protected float TrancelentY;

  /**
   * 成员变量值初始化
   */
  private void initDefault() {

  }

  /**
   * 监听
   */
  protected List<OnTranslationListener> onTranslationListeners = new ArrayList<OnTranslationListener>();

  /**
   * 绘制矩形背景
   *
   * @param canvas
   * @param bean
   * @param paint
   */
  protected void drawRectBg(Canvas canvas, NumberBean bean, Paint paint) {
    RectF rectF = new RectF(bean.x, bean.y, bean.x + attributesHepler.nuberWidth, bean.y + attributesHepler.nuberHeiht);
    canvas.drawRect(rectF, paint);
  }

  /**
   * 绘制数字
   *
   * @param canvas
   * @param bean
   * @param whileTextPaint
   */
  protected void drawText(Canvas canvas, NumberBean bean, Paint whileTextPaint) {
    whileTextPaint.setTextAlign(Paint.Align.CENTER);
    Paint.FontMetrics fontMetrics = whileTextPaint.getFontMetrics();
    float fontHeiht = fontMetrics.bottom - fontMetrics.top;
    float textBaseY = attributesHepler.nuberHeiht - (attributesHepler.nuberHeiht - fontHeiht) / 2 - fontMetrics.bottom;
    canvas.drawText(bean.data, bean.x + attributesHepler.nuberWidth / 2, bean.y + textBaseY, whileTextPaint);
  }

  /**
   * 画分割线,根据bean数据绘制
   *
   * @param canvas
   * @param bean
   */
  protected void drawDivedLine(Canvas canvas, NumberBean bean, Paint linePaint) {
    DrawChartHelper.drawVDeivedLine(canvas, bean, linePaint);
  }

  /**
   * 绘制表的中间部分，会根据状态，绘制，主背景，绘制文字，绘制选中背景，绘制背景字体，绘制sup 背景，绘制sup字体
   *
   * @param canvas
   * @param bean
   * @param hang
   */
  protected void drawNumberByNumber(Canvas canvas, NumberBean bean, int hang, boolean isHaveData) {
    drawRectBg(canvas, bean, hang % 2 == 0 ? ouBgPaint : jiBgPaint);
    if (isHaveData) {//在有数据的情况下绘制
      if (bean.isSelect) {
        DrawChartHelper.drawSelectBg(canvas, bean, selectBgPaint);
        DrawChartHelper.drawText(canvas, bean, selectTextPaint, centerTextpaint.getTextBaseY());
        if (bean.selectBg.supTop != null) {
          DrawChartHelper.drawSupTop(canvas, bean, selectBgPaint, selectTextPaint);
        }
      } else {
        DrawChartHelper.drawText(canvas, bean, unselectTextPaint, centerTextpaint.getTextBaseY());
      }
      linePaint.setColor(attributesHepler.line_color);//设置线的颜色
    } else {//在没有数据的情况下不绘制
      //在没有数据的时候，改变线的颜色
      linePaint.setColor(hang % 2 == 0 ? attributesHepler.normal_oubg_color : attributesHepler.normal_jibg_color);
    }
    drawDivedLine(canvas, bean, linePaint);
    if (bean.isHaveBottomLine) {
      DrawChartHelper.drawHDeivedLine(canvas, bean, linePaint);
    }
  }

  /**
   * 画一行数字
   *
   * @param canvas
   * @param numberBeen
   * @param hang
   */
  protected void drawLineByNumber(Canvas canvas, LineNumberBean numberBeen, int hang) {
    for (int i = getHStartPosition(); i < getHEndPosition(getHStartPosition()); i++) {
      drawNumberByNumber(canvas, numberBeen.numbersData.get(i), hang, numberBeen.isHaveData);
    }
  }

  /**
   * 绘制一个数字，结尾有分割线，按照的是bean中的宽高绘制,bean中的字体颜色绘制
   *
   * @param canvas
   * @param bean
   * @param hang
   */
  public void drawNumberWithLine(Canvas canvas, NumberBean bean, int hang) {
    DrawChartHelper.drawRectBg(canvas, bean, hang % 2 == 0 ? ouBgPaint : jiBgPaint);
    if (bean.isSelect) {
      DrawChartHelper.drawSelectBg(canvas, bean, selectBgPaint);
      DrawChartHelper.drawText(canvas, bean, selectTextPaint, centerTextpaint.getTextBaseY());
    } else {
      DrawChartHelper.drawText(canvas, bean, unselectTextPaint, centerTextpaint.getTextBaseY());
    }
    DrawChartHelper.drawVDeivedLine(canvas, bean, linePaint);
  }

  /**
   * 画一行数字,行数是内部值，主要是绘制topList
   *
   * @param canvas
   * @param numberBeen
   */
  protected void drawLineByNumber(Canvas canvas, List<NumberBean> numberBeen) {
    for (int i = getHStartPosition(); i < getHEndPosition(getHStartPosition()); i++) {
      drawNumberWithLine(canvas, numberBeen.get(i), i);
    }
  }

  /**
   * 计算每个View 应该显示的坐标，在View大小变化的时候，或者是数据变化的时候
   */
  protected abstract void calculateNumberPositon();

  /**
   * 获取，tanrecleentX，最小
   *
   * @return
   */
  protected float getMinTrancelentX() {
    return getChartWidth() - getTotalDataWidth();
  }

  /**
   * 获取，tanreceLentY 的最小值
   *
   * @return
   */
  protected float getMinTrancelentY() {
    return getChartHeith() - getTotalDataHeith();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (handleTouchEvent.onTouchEvent(event)) {
      return true;
    }
    return super.onTouchEvent(event);
  }

  protected void motifyXYByScrollMdel() {
    if (attributesHepler.isScrollAll()) {

    } else if (attributesHepler.notScrollModel()) {
      TrancelentX = 0;
      TrancelentY = 0;
    } else if (attributesHepler.isScrollHModel()) {
      TrancelentY = 0;
    } else if (attributesHepler.isScrollVModel()) {
      TrancelentX = 0;
    }
  }

  /**
   * @param distanceX
   * @param distanceY
   */
  protected void trancelent(float distanceX, float distanceY) {
    trancelentX(distanceX);
    trancelenY(distanceY);
    motifyXYByScrollMdel();
    invalidate();
    dispatchOnTranslationListener(TrancelentX, TrancelentY, distanceX, distanceY);

  }

  /**
   * x方向滑动
   *
   * @param distanceX
   */
  protected boolean trancelentX(float distanceX) {
    //暂时滑动的距离
    float tempTrancelentX = TrancelentX + distanceX;
    if (distanceX > 0) {//向右滑动
      if (!canScrollRight(TrancelentX)) {
        return false;
      }
      if (canScrollRight(tempTrancelentX)) {//这次滑动有效，可以全部消耗
        TrancelentX = tempTrancelentX;
        return true;
      }

      if (canScrollRight(TrancelentX) && !canScrollRight(tempTrancelentX)) {//根据没有移动的TrancelentY，判断是否可以滑动
        TrancelentX = 0;
        return true;
      }
    } else {//向左滑动
      if (!canScrollLeft(TrancelentX)) {//在没有加的时候判断原来是否可以滑动  不可以滑动
        return false;
      }
      if (canScrollLeft(tempTrancelentX)) {
        TrancelentX = tempTrancelentX;
        return true;
      }
      if (canScrollLeft(TrancelentX) && !canScrollLeft(tempTrancelentX)) {
        TrancelentX = getMinTrancelentX();
        return true;
      }
    }
    return false;
  }

  /**
   * y方向滑动
   *
   * @param distanceY
   */
  protected boolean trancelenY(float distanceY) {
    //暂时滑动的距离
    float tempTrancelentY = TrancelentY + distanceY;
    if (distanceY > 0) {//向下滑动
      if (TrancelentY == 0) {
        return false;
      }
      if (canScorllBottom(tempTrancelentY)) {//这次滑动有效，可以全部消耗
        TrancelentY = tempTrancelentY;
        return true;
      }

      if (canScorllBottom(TrancelentY) && !canScorllBottom(tempTrancelentY)) {//根据没有移动的TrancelentY，判断是否可以滑动
        TrancelentY = 0;
        return true;
      }
    } else {//向上滑动
      Log.i("ScrollTOp", "ScrollTOp" + canScrollTop(TrancelentY));
      if (!canScrollTop(TrancelentY)) {//在没有加的时候判断原来是否可以滑动  不可以滑动
        return false;
      }
      Log.i("ScrollTOp", "ScrollTOp" + distanceY);
      if (canScrollTop(tempTrancelentY)) {
        TrancelentY = tempTrancelentY;
        return true;
      }
      if (canScrollTop(TrancelentY) && !canScrollTop(tempTrancelentY)) {
        TrancelentY = getMinTrancelentY();
        return true;
      }
    }
    return false;
  }

  /**
   * 分发滑动位移
   *
   * @param translationX
   * @param translationY
   */
  protected void dispatchOnTranslationListener(float translationX, float translationY, float distanceX, float distanceY) {
    if (onTranslationListeners.size() > 0) {
      for (int i = 0; i < onTranslationListeners.size(); i++) {
        onTranslationListeners.get(i).onTranslationChanger(translationX, translationY);
        onTranslationListeners.get(i).onDistanceChanger(distanceX, distanceY);
      }
    }
  }

  @Override
  public void onDistanceChanger(float distanceX, float distanceY) {
    if(isHaveData()){
      trancelent(distanceX, distanceY);
    }

  }

  protected abstract   boolean isHaveData();

  /**
   * 用于绘制表格数据的宽度
   *
   * @return
   */
  protected float getChartWidth() {
    return getWidth() - left;
  }

  /**
   * 用于绘制表格数据的高度
   *
   * @return
   */
  protected float getChartHeith() {
    return getHeight() - top;
  }

  /**
   * 获取从哪一行开始绘制，竖直方向上
   *
   * @return
   */
  protected int getVStartPosion() {
    float abs = Math.abs(TrancelentY);
    int start;
    float v = abs / attributesHepler.nuberHeiht;
    if (v < 1) {
      start = 0;
    } else {
      start = ((int) v) - 1;
    }
    return start;
//     return 0;
  }

  /**
   * 获取从哪一行截止绘制
   *
   * @param start
   * @return
   */
  protected int getVEndPosition(int start) {
    int end;
    if (canScrollVertical()) {
      float v = getChartHeith() / attributesHepler.nuberHeiht;
      end = start + ((int) v) + 3;
      if (end > getChartTotalVNumber()) {
        end = getChartTotalVNumber();
      }
    } else {
      end = getChartTotalVNumber();
    }
    return end;
    // return getChartTotalVNumber();
  }

  /**
   * 获取从那一列开始绘制
   *
   * @return
   */
  protected int getHStartPosition() {
    float abs = Math.abs(TrancelentX);
    int start;
    float v = abs / (attributesHepler.nuberWidth );
    if (v < 1) {
      start = 0;
    } else {
      start = ((int) v) - 1;
    }
    return start;
    // return 0;
  }

  /**
   * 获取表格显示多少列，要求和数据集合对应
   *
   * @return
   */
  protected abstract int getChartTotalHNumber();

  /**
   * 获取竖直方向上数据显示多少行，和集合数据对应
   *
   * @return
   */
  protected abstract int getChartTotalVNumber();

  /**
   * 获取绘制的结束的列数的位置
   *
   * @param start
   * @return
   */
  protected int getHEndPosition(int start) {
    int end;
    int total = getChartTotalHNumber();
    if (canScrollHorial()) {
      float v = getChartWidth() / (attributesHepler.nuberWidth);
      end = start + ((int) v) + 3;
      if (end > total) {
        end = total;
      }
    } else {
      end = total;
    }
    return end;
    // return getChartTotalHNumber();
  }

  /**
   * 获取总体的显示数据需要高度
   *
   * @return
   */
  protected float getTotalDataHeith() {
    return getChartTotalVNumber() * attributesHepler.nuberHeiht + attributesHepler.lineWidth;
  }

  /**
   * 获取显示总的data需要的宽度,默认的是左边添加线的宽度
   *
   * @return
   */
  protected float getTotalDataWidth() {
    return getChartTotalHNumber() * attributesHepler.nuberWidth + getChartTotalHNumber() * attributesHepler.lineWidth;
  }

  /**
   * 是否可以竖直方向滑动
   *
   * @return
   */
  protected boolean canScrollVertical() {
    return getTotalDataHeith() > getChartHeith();
  }

  /**
   * 是否可以水平方向滑动
   */
  protected boolean canScrollHorial() {
    return getTotalDataWidth() > getChartWidth();
  }

  /**
   * 是否可以向右滑动
   *
   * @return
   */
  protected boolean canScrollRight(float trancelentX) {
    return canScrollHorial() && trancelentX < 0;
  }

  /**
   * 判断是否可以向左滑动
   *
   * @return
   */
  protected boolean canScrollLeft(float trancelentX) {
    return canScrollHorial() && Math.abs(trancelentX) + getChartWidth() < getTotalDataWidth();
  }

  /**
   * 是否可以向上滑动
   *
   * @return
   */
  protected boolean canScrollTop(float trancelentY) {
    return canScrollVertical() && Math.abs(trancelentY) + getChartHeith() < getTotalDataHeith();
  }

  /**
   * 判断是否可以向下滑动
   *
   * @return
   */
  protected boolean canScorllBottom(float trancelentY) {
    return canScrollVertical() && trancelentY < 0;
  }

  /**
   * 滑动到指定位置，x
   *
   * @param trancelentX
   */
  public void moveToX(float trancelentX) {
    TrancelentX = trancelentX;
    invalidate();
  }

  /**
   * 滑动到指定位置，
   *
   * @param trancelentY
   */
  public void moveToY(float trancelentY) {
    TrancelentY = trancelentY;
    invalidate();
  }

  /**
   * 添加一行的高度
   */
  protected void addRowHeiht() {
    currentY = currentY + attributesHepler.nuberHeiht;
  }

  /**
   * 添加一条线的宽度
   */
  protected void addHLineWidth() {
    currentX = currentX + attributesHepler.lineWidth;
  }

  /**
   * 添加一条线的宽度
   */
  protected void addVLineWidth() {
    currentY = currentY + attributesHepler.lineWidth;
  }

  /**
   * 添加一个数字的宽度
   */
  protected void addNumberWidth() {
    currentX = currentX + attributesHepler.nuberWidth;
  }

  /**
   * 重置，当前x
   */
  protected void resetCurrentX() {
    currentX = 0;
  }

  /**
   * 重置当前Y
   */
  protected void resetCurrentY() {
    currentY = 0;
  }

  /**
   * 设置滑动监听
   */
  public interface OnTranslationListener {
    /**
     * 无论设置的哪一种滑动模式，返回的是所有的坐标
     *
     * @param translationX
     * @param translationY
     */
    public void onTranslationChanger(float translationX, float translationY);

    /**
     * 滑动的间距
     *
     * @param distanceX
     * @param distanceY
     */
    public void onDistanceChanger(float distanceX, float distanceY);
  }

  /**
   * 添加监听
   *
   * @param onTranslationListener
   */
  public void addOnTranslationListener(OnTranslationListener onTranslationListener) {
    onTranslationListeners.add(onTranslationListener);
  }

  public void removeOnTranslationListener(OnTranslationListener onTranslationListener) {

    onTranslationListeners.remove(onTranslationListener);
  }

  /**
   * 转化top或者是Left数据
   */
  public interface ConvertDataLeftOrTop {
    /**
     * 上面列表的数据
     *
     * @param attributesHepler
     * @return
     */
    public List<NumberBean> convertTopOrLeftListNumber(Attributes attributesHepler);

  }

}
