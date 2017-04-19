package succulent.jiuge.com.caipiaoview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import java.util.List;

import succulent.jiuge.com.caipiaoview.ListUtils;
import succulent.jiuge.com.caipiaoview.view.helper.CalculateHepler;
import succulent.jiuge.com.caipiaoview.view.helper.DrawChartHelper;
import succulent.jiuge.com.caipiaoview.view.viewbean.NumberBean;

/**
 * Created by wdd on 2017/2/28.
 */

public class LeftListView extends BastChartView implements CenterTrendView.SizeChangeListener {

  private NumberBean topNumberBean;
  /**
   * 隐藏统计
   */
  private boolean hideStatData;

  public LeftListView(Context context) {
    super(context);
    init();
  }

  public LeftListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public LeftListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    initPaint();
    left = 0;
    top = 0;
    getListData();
  }

  List<NumberBean> data;
  int lastlinePositon = 4;

  public void setlastPositonLine(int lastlinePositon) {
    this.lastlinePositon = lastlinePositon;
  }

  public void getListData() {

/*    data = DataConvertHelper.getLeftListTestData(50
        , attributesHepler.nuberWidth
        , attributesHepler.nuberHeiht
        , attributesHepler.normal_textcolor
        , attributesHepler.normal_textsize, attributesHepler.lineWidth);*/
  }

  /**
   * 获取从哪个位置开始，要绘制一条线
   *
   * @return
   */
  private int getStartLinePositon() {
    return data.size() - lastlinePositon;
  }

  @Override
  protected void calculateNumberPositon() {
    CalculateHepler.calculateLeftList(data, getStartLinePositon(), attributesHepler.lineWidth);
  }

  @Override
  protected boolean isHaveData() {
    return ListUtils.isHaveData(data);
  }

  @Override
  protected int getChartTotalHNumber() {
    return 1;
  }

  @Override
  protected int getChartTotalVNumber() {
    return data.size();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    upData();
  }

  public void upData() {
    getTopNumberBean();
    attributesHepler.nuberWidth = getWidth();
    data = convertLeftData.convertTopOrLeftListNumber(attributesHepler);
    if(!ListUtils.isHaveData(data)){
      return;
    }
    if (hideStatData) {
      for (int i = 0; i < lastlinePositon; i++) {
        data.remove(data.size() - 1);
      }
    }
    calculateNumberPositon();
    initPosition();
    TrancelentY = getMinTrancelentY();
    invalidate();
    centerTextpaint = new PaintHolder(unselectTextPaint, attributesHepler.nuberHeiht);
  }

  private void getTopNumberBean() {
    topNumberBean = new NumberBean();
    topNumberBean.data = "期次";
    topNumberBean.x = 0;
    topNumberBean.y = 0;
    topNumberBean.heith = attributesHepler.nuberHeiht;
    topNumberBean.width = attributesHepler.nuberWidth - attributesHepler.lineWidth;
    topNumberBean.normalTextSize = attributesHepler.normal_textsize;
    topNumberBean.normalTextColor = attributesHepler.normal_textcolor;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if(!ListUtils.isHaveData(data)){
      return;
    }
    super.onDraw(canvas);
    canvas.translate(0, TrancelentY + top);
    //drawARowByNumber(canvas, data);
    newDraw(canvas);
    topNumberBean.y = -(TrancelentY + top);
    drawNumberByNumber(canvas, topNumberBean, 1, true);
  }

  /**
   * 画一列数字,计算开始和结束的位置，是按照的是，默认的列表的高度绘制的
   * 绘制左边的可滑动的列
   *
   * @param canvas
   * @param numberBeen
   */
  protected void drawARowByNumber(Canvas canvas, List<NumberBean> numberBeen) {
    for (int i = vStartPosion; i < vEndPosition; i++) {
      drawNumberWithLine(canvas, numberBeen.get(i), i);
    }
  }

  @Override
  public void onWidthHightChange(float numberWith, float numberHeith) {

    attributesHepler.nuberWidth = getWidth();
    attributesHepler.nuberHeiht = numberHeith;
    top = attributesHepler.nuberHeiht;
    upData();

  }

  private void newDraw(Canvas canvas) {
    newDrawBg(canvas);
    newDrawText(canvas);
    newDrawLine(canvas);

  }

  /**
   * 绘制背景
   *
   * @param canvas
   */
  private void newDrawBg(Canvas canvas) {

    for (int i = vStartPosion; i < vEndPosition; i++) {
      DrawChartHelper.drawRectBg(canvas, data.get(i), i % 2 == 0 ? ouBgPaint : jiBgPaint);
    }
  }

  private void newDrawText(Canvas canvas) {
    for (int i = vStartPosion; i < vEndPosition; i++) {
      DrawChartHelper.drawText(canvas, data.get(i), unselectTextPaint, centerTextpaint.getTextBaseY());
    }
  }

  /**
   * 绘制线
   *
   * @param canvas
   */
  private void newDrawLine(Canvas canvas) {
    NumberBean numberBean = data.get(vStartPosion);
    NumberBean numberBean1 = data.get(vEndPosition - 1);
    canvas.drawLine(numberBean.x + attributesHepler.nuberWidth, numberBean.y, numberBean1.x + attributesHepler.nuberWidth, numberBean1.y + attributesHepler.nuberHeiht, linePaint);
    /**
     * 在他的上面绘制一条线
     */
    if (!hideStatData) {
      NumberBean numberBean2 = data.get(getStartLinePositon());
      canvas.drawLine(numberBean2.x, numberBean2.y - attributesHepler.lineWidth / 2, numberBean2.x + getWidth(), numberBean2.y - attributesHepler.lineWidth / 2, linePaint);
    }

  }

  private ConvertDataLeftOrTop convertLeftData;

  public void setConvertLeftData(ConvertDataLeftOrTop convertLeftData) {
    this.convertLeftData = convertLeftData;
  }

  /**
   * 以藏统计数据
   *
   * @param hideStatData
   */
  public void setHideStatData(boolean hideStatData) {
    this.hideStatData = hideStatData;
    upData();
  }

  public boolean isHideStatData() {
    return hideStatData;
  }
}
