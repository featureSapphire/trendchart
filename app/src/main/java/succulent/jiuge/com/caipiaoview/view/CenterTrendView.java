package succulent.jiuge.com.caipiaoview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import succulent.jiuge.com.caipiaoview.ListUtils;
import succulent.jiuge.com.caipiaoview.view.helper.CalculateHepler;
import succulent.jiuge.com.caipiaoview.view.helper.DrawChartHelper;
import succulent.jiuge.com.caipiaoview.view.viewbean.Attributes;
import succulent.jiuge.com.caipiaoview.view.viewbean.LineNumberBean;
import succulent.jiuge.com.caipiaoview.view.viewbean.NoDataBean;
import succulent.jiuge.com.caipiaoview.view.viewbean.NumberBean;
import succulent.jiuge.com.caipiaoview.view.viewbean.StatisNumbers;

import static android.media.CamcorderProfile.get;

/**
 * Created by wdd on 2017/2/24.
 */

public class CenterTrendView extends BastChartView {
  private Bitmap bitmap = null;

  public List<LineNumberBean> getMyNumberBeans() {
    return numberBeens;
  }

  /**
   * 统计数据
   */
  private StatisNumbers statisNumbers;

  private List<LineNumberBean> numberBeens;
//  private List<LineNumberBean> statisNumbersList;

/*  private List<LineNumberBean> getNumberBeens() {
    Random random = new Random(100);

    statisNumbers = new StatisNumbers();
    statisNumbers.isHaveData = true;
    statisNumbers.viewShowWidth = getChartWidth();
    //   statisNumbersList = new ArrayList<>();

    LineNumberBean numberBeen = null;
    numberBeens = new ArrayList<>();
    NumberBean bean = null;
    int valueRandom = 0;
    List<Float> floats = obtainWH();
    for (int i = 0; i < 50; i++) {
      numberBeen = new LineNumberBean();
      numberBeen.viewShowWidth = getChartWidth();
      valueRandom = random.nextInt();
      numberBeen.isHaveData = (random.nextInt() % 2 == 0);
      // numberBeen.isHaveData = true;
      for (int j = 0; j < 17; j++) {
        bean = new NumberBean();
        bean.data = (i + j) + "";
        bean.isSelect = (random.nextInt() % 4 == 0);
        bean.width = floats.get(0);
        bean.heith = floats.get(1);
        bean.normalTextSize = attributesHepler.normal_textsize;

        //测试
        bean.normalTextColor = getResources().getColor(R.color.gray_text);

        if (bean.isSelect) {
          bean.selectBg = new NumberBean.SelectBgBean();
          bean.selectBg.bgType = NumberBean.SelectBgBean.Bg_TYPE_CIRE;
          bean.selectBg.paddingLeft = attributesHepler.select_bg_pading;
          bean.selectBg.paddingRight = attributesHepler.select_bg_pading;
          bean.selectBg.paddingTop = attributesHepler.select_bg_pading;
          bean.selectBg.paddingBottom = attributesHepler.select_bg_pading;
          bean.selectBg.bgColor = getResources().getColor((i + j) % 2 == 0 ? R.color.select_bg : R.color.colorPrimaryDark);
          bean.selectBg.textColor = getResources().getColor((i + j) % 2 != 0 ? R.color.select_bg : R.color.colorPrimaryDark);
          bean.selectBg.supTop = new NumberBean.SupTop();
          bean.selectBg.supTop.r = attributesHepler.nuberWidth / 5;
          bean.selectBg.supTop.data = "2";
          bean.selectBg.supTop.textSize = attributesHepler.sup_textsize;
          bean.selectBg.supTop.bgColor = getResources().getColor(R.color.colorPrimaryDark);
          bean.selectBg.supTop.textColor = getResources().getColor(R.color.whilte_text);
          // bean.selectBg.supTop = null;
        }

        //  bean.isSelect = false;
        numberBeen.numbersData.add(bean);
      }
      *//**
       * 截获统计数据
       *//*
      if (i >= 45) {
        numberBeen.isHaveData = true;
        numberBeen.dataType = LineNumberBean.DATA_TYPE_STATICS;
        // statisNumbersList.add(numberBeen);
      }
      numberBeens.add(numberBeen);
    }

    numberbeanTopData = DataConvertHelper.getTopListTestData(200, attributesHepler.nuberWidth, top, getResources().getColor(R.color.gray_text), attributesHepler.normal_textsize);

    return numberBeens;
  }*/

  public CenterTrendView(Context context) {
    super(context);
    init();
  }

  public CenterTrendView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CenterTrendView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    initPaint();
    left = 0;
    top = attributesHepler.nuberHeiht;
  }

  long sratTime;
  private List<NumberBean> numberbeanTopData = null;
  private Paint paint = new Paint();

  @Override
  protected void onDraw(Canvas canvas) {
    if(!ListUtils.isHaveData(numberBeens)){
      return;
    }
    super.onDraw(canvas);
    sratTime = System.currentTimeMillis();
    canvas.save();
    canvas.clipRect(0, 0, getChartWidth(), getChartHeith() + top, Region.Op.REPLACE);//设置显示范围

    canvas.translate(TrancelentX + left, TrancelentY + top);
    /*
     * 绘制没有连接线，但是绘制sup的
     * */

    //  drawNoBreakLineByNumber(canvas);
    //newDraw(canvas);
    //canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), null);
    //canvas.drawBitmap(bitmap,0,0,null);
    if(attributesHepler.isconnect_line){
      newDrawHaveConnect(canvas);
    }else{
      newDraw(canvas);
    }

    /*
     * 绘制带有折现的，连接
     * */

    // drawLineChartByNumber(canvas);
    //newDrawHaveConnect(canvas);
    if (top != 0) {
      canvas.restore();
      canvas.translate(TrancelentX + left, 0);
      //drawLineByNumber(canvas, numberbeanTopData);
      newDrawTop(canvas);
    }

    //
    Log.i("onDraw", "花费的时间" + (System.currentTimeMillis() - sratTime) + "是否加速" + canvas.isHardwareAccelerated());
  }

  /**
   * 计算每个数字应该显示的位置
   */
  protected void calculateNumberPositon() {
    LineNumberBean lineNumberBeen = null;
    NumberBean bean = null;
    for (int i = 0; i < numberBeens.size(); i++) {
      lineNumberBeen = numberBeens.get(i);
      for (int j = 0; j < lineNumberBeen.numbersData.size(); j++) {
        bean = lineNumberBeen.numbersData.get(j);
        bean.x = currentX;
        bean.y = currentY;
        addNumberWidth();
        addHLineWidth();
      }
      if (statisNumbers != null && numberBeens.size() - i == statisNumbers.numbersData.size()) {
        addVLineWidth();
      }
      addRowHeiht();
      resetCurrentX();
    }
    resetCurrentY();
    resetCurrentX();
  }

  /**
   * 对于有连线的控件的绘制
   * 1.绘制未选中的背景和文字
   * 2.绘制折线图
   * 3.绘制选中的背景和文字
   */



  /*------------根据小球的位置来绘制操作.只绘制小图，绘制折线-------------------------------------------------------------------------*/

  /**
   * 绘制没有连线的走势图
   *
   * @param canvas
   */
  protected void drawNoBreakLineByNumber(Canvas canvas) {
    LineNumberBean lineNumberBeen = null;
    for (int i = vStartPosion; i < vEndPosition; i++) {
      lineNumberBeen = numberBeens.get(i);
      drawLineByNumber(canvas, lineNumberBeen, i);
      //TODO  根据这一行是否有数据绘制，根据从是否是统计数据，如果是统计数据就先不处理
      if (isNormalNOdata(lineNumberBeen)) {
        DrawChartHelper.drawNODataText(canvas, lineNumberBeen, unselectTextPaint, TrancelentX, TrancelentY, Math.min(getTotalDataWidth(), getChartWidth()));
      }
    }
    //TODO  如果统计数据是没有数据的状态，就绘制统计数据
    if (statisNumbers != null && !statisNumbers.isHaveData) {//在统计数据是暂无数据的时候，绘制
      unselectTextPaint.setTextSize(50);
      DrawChartHelper.drawNODataText(canvas, statisNumbers, unselectTextPaint, TrancelentX, TrancelentY, Math.min(getTotalDataWidth(), getChartWidth()));
    }
  }






/*-------------------------绘制折线，并且绘制背景按照，小球位置绘制--------------*/

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Logger.i("onSizeChanged" + "是不断的计算");
    upData();
  //  bitmap = makeBitmap();
    dispatchSizeChanger();
  }

  /**
   * 获取表格显示多少列，要求和数据集合对应
   *
   * @return
   */
  @Override
  protected int getChartTotalHNumber() {
    if (attributesHepler.isScrollVModel() || attributesHepler.notScrollModel()) {
      return attributesHepler.line_number;
    }
    return ListUtils.isHaveData(numberBeens) ? numberBeens.get(0).numbersData.size() : 0;
  }

  /**
   * 获取竖直方向上数据显示多少行，和集合数据对应
   *
   * @return
   */
  @Override
  protected int getChartTotalVNumber() {
    return ListUtils.isHaveData(numberBeens) ? numberBeens.size() : 0;

  }

  //分发view的尺寸变化的回掉

  private List<SizeChangeListener> sizeChangeListeners = new ArrayList<>();

  /**
   * 设置size改变监听
   *
   * @param sizeChangeListener
   */
  public void setSizeChangeListener(SizeChangeListener sizeChangeListener) {
    sizeChangeListeners.add(sizeChangeListener);
  }

  private void dispatchSizeChanger() {
    if (ListUtils.isHaveData(sizeChangeListeners)) {
      for (int i = 0; i < sizeChangeListeners.size(); i++) {
        sizeChangeListeners.get(i).onWidthHightChange(attributesHepler.lineWidth, attributesHepler.nuberHeiht);
      }
    }
  }

  /**
   * 清除size改变监听
   *
   * @param sizeChangeListener
   */
  public void removeSizeChangeListener(SizeChangeListener sizeChangeListener) {
    sizeChangeListeners.remove(sizeChangeListener);
  }

  public interface SizeChangeListener {
    public void onWidthHightChange(float numberWith, float numberHeith);
  }

/*---------------------------------------绘制折线图-----------------------------------------------------------------*/

  /**
   * 绘制选中的小球的折线图
   *
   * @param canvas
   */
  private void drawLineChartByNumber(Canvas canvas) {
    drawFistBgAndNumberByNumber(canvas);
    drawConnetLine(canvas);
    drawThridSelectNumberAndBgByNumber(canvas);
  }

  /**
   * 绘制第一层的背景和文字，未选中
   *
   * @param canvas
   */
  private void drawFistBgAndNumberByNumber(Canvas canvas) {
    LineNumberBean lineNumberBeen = null;
    for (int i = vStartPosion; i < vEndPosition; i++) {
      lineNumberBeen = numberBeens.get(i);
      drawRowFistBgAndNumberByNumber(canvas, lineNumberBeen, i);
      //TODO  根据这一行是否有数据绘制，根据从是否是统计数据，如果是统计数据就先不处理
      if (isNormalNOdata(lineNumberBeen)) {
        DrawChartHelper.drawNODataText(canvas, lineNumberBeen, unselectTextPaint, TrancelentX, TrancelentY, Math.min(getTotalDataWidth(), getChartWidth()));
      }
    }
    //TODO  如果统计数据是没有数据的状态，就绘制统计数据
    if (statisNumbers != null && !statisNumbers.isHaveData) {//在统计数据是暂无数据的时候，绘制
      unselectTextPaint.setTextSize(50);
      DrawChartHelper.drawNODataText(canvas, statisNumbers, unselectTextPaint, TrancelentX, TrancelentY, Math.min(getTotalDataWidth(), getChartWidth()));
    }
  }

  /**
   * 绘制一行，只绘制未选中的背景和文字
   *
   * @param canvas
   * @param numberBeen
   * @param hang
   */
  private void drawRowFistBgAndNumberByNumber(Canvas canvas, LineNumberBean numberBeen, int hang) {
    for (int i = hStartPosition; i < hEndPosition; i++) {
      drawOnlyUnselectNumnerByNumber(canvas, numberBeen.numbersData.get(i), hang, numberBeen.isHaveData);
    }
  }

  /**
   * 只绘制没有选中的背景和文字
   *
   * @param canvas
   * @param bean
   * @param hang
   */
  private void drawOnlyUnselectNumnerByNumber(Canvas canvas, NumberBean bean, int hang, boolean isHaveData) {
    DrawChartHelper.drawRectBg(canvas, bean, hang % 2 == 0 ? ouBgPaint : jiBgPaint);
    //有数据，没有选中
    if (isHaveDataNoSelect(bean, isHaveData)) {
      DrawChartHelper.drawText(canvas, bean, unselectTextPaint, centerTextpaint.getTextBaseY());
    }
    //根据数据的状况，改变字体的颜色
    linePaint.setColor(isHaveData ? attributesHepler.line_color
        : (hang % 2 == 0 ? attributesHepler.normal_oubg_color : attributesHepler.normal_jibg_color));
    DrawChartHelper.drawVDeivedLine(canvas, bean, linePaint);
    if (bean.isHaveBottomLine) {
      DrawChartHelper.drawHDeivedLine(canvas, bean, linePaint);
    }
  }

  /**
   * 有数据但是，不是选中状态
   *
   * @param bean
   * @param isHaveData
   * @return
   */
  private boolean isHaveDataNoSelect(NumberBean bean, boolean isHaveData) {
    return !bean.isSelect && isHaveData;
  }

//  第三层的绘制按照数字绘制

  /**
   * 绘制一行，选中的数字和背景
   *
   * @param canvas
   */
  private void drawThridSelectNumberAndBgByNumber(Canvas canvas) {
    LineNumberBean numberBeen = null;
    for (int i = vStartPosion; i < vEndPosition; i++) {
      numberBeen = numberBeens.get(i);
      if (numberBeen.isHaveData) {
        drawRowSelectNumBgByNumber(canvas, numberBeen, i);
      }
    }
  }

  /**
   * 绘制一行，属于第三层，只绘制，选中的文字和背景
   *
   * @param canvas
   * @param numberBeen
   */
  private void drawRowSelectNumBgByNumber(Canvas canvas, LineNumberBean numberBeen, int hang) {
    for (int i = hStartPosition; i < hEndPosition; i++) {
      drawOnlySelctNumberByNumber(canvas, numberBeen.numbersData.get(i), hang);
    }
  }

  /**
   * 只绘制，选中的那个数字的背景和数字，还是用原来的坐标
   *
   * @param canvas
   * @param bean
   * @param hang
   */
  private void drawOnlySelctNumberByNumber(Canvas canvas, NumberBean bean, int hang) {
    if (bean.isSelect) {
      DrawChartHelper.drawSelectBg(canvas, bean, selectBgPaint);
      DrawChartHelper.drawText(canvas, bean, selectTextPaint, centerTextpaint.getTextBaseY());
    }
  }

  /**
   * 绘制连接线
   *
   * @param canvas
   */
  protected void drawConnetLine(Canvas canvas) {
    NumberBean selectNumber1;
    NumberBean selectNumber2;
    for (int i = vStartPosion; i < vEndPosition - 1; i++) {
      selectNumber1 = getSelectNumber(numberBeens.get(i));
      selectNumber2 = getSelectNumber(numberBeens.get(i + 1));
      if (selectNumber1 == null || selectNumber2 == null) {
        continue;
      }
      canvas.drawLine(selectNumber1.x + attributesHepler.nuberWidth / 2, selectNumber1.y + attributesHepler.nuberHeiht / 2
          , selectNumber2.x + attributesHepler.nuberWidth / 2
          , selectNumber2.y + attributesHepler.nuberHeiht / 2, ballConnectBg);
    }
  }

  /**
   * 绘制类型是正常类型，并且没有数据
   *
   * @param lineNumberBeen
   * @return
   */
  private boolean isNormalNOdata(LineNumberBean lineNumberBeen) {
    return lineNumberBeen.dataType == LineNumberBean.DATA_TYPE_NORMAL && !lineNumberBeen.isHaveData;
  }

  private NumberBean getSelectNumber(LineNumberBean numberBeen) {
    if (!numberBeen.isHaveData) {
      return null;
    }
    for (int i = 0; i < numberBeen.numbersData.size() - 1; i++) {
      if (numberBeen.numbersData.get(i).isSelect) {
        return numberBeen.numbersData.get(i);
      }
    }
    return null;
  }

  /*-------------使用另外一种方式绘制，大面积绘制，减少移动-----------------------------*/
  private void drawnormalbg(Canvas canvas) {
    LineNumberBean lineNumberBean = null;
    NumberBean numberBean1 = null;
    NumberBean numberBean2 = null;

    for (int i = vStartPosion; i < vEndPosition; i++) {
      lineNumberBean = numberBeens.get(i);
      if (!lineNumberBean.isHaveData) {
        continue;
      }
      numberBean1 = lineNumberBean.numbersData.get(hStartPosition);
      numberBean2 = lineNumberBean.numbersData.get(hEndPosition - 1);
      canvas.drawRect(numberBean1.x, numberBean1.y, numberBean2.x + attributesHepler.nuberWidth, numberBean2.y + attributesHepler.nuberHeiht, i % 2 == 0 ? ouBgPaint : jiBgPaint);
    }
  }

  private void newdrawLineNoDataBg(NoDataBean statisNumbers, Canvas canvas, float width, int hang) {
    float x = statisNumbers.getX(TrancelentX);
    float y = statisNumbers.getY(TrancelentY);
    canvas.drawRect(x, y, x + width - attributesHepler.lineWidth, y + statisNumbers.getHeight(), hang % 2 == 0 ? ouBgPaint : jiBgPaint);

  }

  private void newDraw(Canvas canvas) {
    drawnormalbg(canvas);
    drawAllLine(canvas);
    drawNewSelectBg(canvas);
    newDrawText(canvas);
  }

  private void drawNewSelectBg(Canvas canvas) {
    //TODO  保存状态
    LineNumberBean lineNumberBeen = null;
    for (int i = vStartPosion; i < vEndPosition; i++) {
      lineNumberBeen = numberBeens.get(i);
      drawNewLineSelectBgNumber(lineNumberBeen, canvas, i);
    }
    //TODO  恢复状态
  }

  /**
   * 绘制选中的背景
   *
   * @param lineNumberBeen
   * @param canvas
   * @param hang
   */
  private void drawNewLineSelectBgNumber(LineNumberBean lineNumberBeen, Canvas canvas, int hang) {
    //TODO  在不等于0的时候，向下，移动一个高度，然后保存状态
    for (int i = hStartPosition; i < hEndPosition; i++) {
      //TODO  在
      drawNewSelectNumber(lineNumberBeen.numbersData.get(i), canvas, lineNumberBeen.isHaveData);
    }
    //TODO 恢复向下移动一个高度的状态；

  }

  /**
   * 绘制选中的背景，单数字的北京
   *
   * @param bean
   * @param canvas
   * @param isHaveData
   */
  private void drawNewSelectNumber(NumberBean bean, Canvas canvas, boolean isHaveData) {
    if (isHaveData) {//在有数据的情况下绘制
      if (bean.isSelect) {
        DrawChartHelper.drawSelectBg(canvas, bean, selectBgPaint);
        if (bean.selectBg.supTop != null) {
          DrawChartHelper.drawSupTop(canvas, bean, selectBgPaint, selectTextPaint);
        }
      }
    }
  }

  /**
   * 绘制线段
   *
   * @param canvas
   */
  private void drawAllLine(Canvas canvas) {
    LineNumberBean lineNumberBean1 = numberBeens.get(vStartPosion);
    LineNumberBean lineNumberBean2 = numberBeens.get(vEndPosition - 1);
    NumberBean numberBean1 = null;
    NumberBean numberBean2 = null;
    float move = linePaint.getStrokeWidth() / 2;
    for (int i = hStartPosition; i < hEndPosition; i++) {
      numberBean1 = lineNumberBean1.numbersData.get(i);
      numberBean2 = lineNumberBean2.numbersData.get(i);
      canvas.drawLine(numberBean1.x + numberBean1.width + move, numberBean1.y, numberBean2.x + numberBean2.width, numberBean2.y + numberBean2.heith, linePaint);
    }
    //绘制统计分割线
    if (statisNumbers != null) {
      canvas.drawLine(statisNumbers.getX(TrancelentX), statisNumbers.getY(TrancelentY) - attributesHepler.lineWidth / 2, statisNumbers.getX(TrancelentX) + Math.min(getChartWidth(), getTotalDataWidth()), statisNumbers.getY(TrancelentY) - attributesHepler.lineWidth / 2, linePaint);
    }
  }

  /**
   * 绘制一层，没有选中的文字
   *
   * @param canvas
   */
  private void newDrawOnlyNoSelecttext(Canvas canvas) {
    LineNumberBean lineNumberBeen = null;
    float minWith = Math.min(getTotalDataWidth(), getChartWidth());
    for (int i = vStartPosion; i < vEndPosition; i++) {
      lineNumberBeen = numberBeens.get(i);
      newDrawLineTextUS(canvas, lineNumberBeen, i);
      //TODO  根据这一行是否有数据绘制，根据从是否是统计数据，如果是统计数据就先不处理
      if (isNormalNOdata(lineNumberBeen)) {
        newdrawLineNoDataBg(lineNumberBeen, canvas, minWith, i);
        DrawChartHelper.drawNODataText(canvas, lineNumberBeen, unselectTextPaint, TrancelentX, TrancelentY, minWith);
      }
    }
    //TODO  如果统计数据是没有数据的状态，就绘制统计数据
    if (statisNumbers != null && !statisNumbers.isHaveData) {//在统计数据是暂无数据的时候，绘制
      newdrawLineNoDataBg(statisNumbers, canvas, minWith, 0);
      DrawChartHelper.drawNODataText(canvas, statisNumbers, unselectTextPaint, TrancelentX, TrancelentY, minWith);
    }
  }

  /**
   * 绘制一层，没有选中的文字
   *
   * @param canvas
   */
  private void newDrawOnlySelecttext(Canvas canvas) {
    LineNumberBean lineNumberBeen = null;
    for (int i = vStartPosion; i < vEndPosition; i++) {
      lineNumberBeen = numberBeens.get(i);
      newDrawLineTextS(canvas, lineNumberBeen, i);
    }
  }

  private void newDrawText(Canvas canvas) {
    float minWith = Math.min(getTotalDataWidth(), getChartWidth());
    LineNumberBean lineNumberBeen = null;
    for (int i = vStartPosion; i < vEndPosition; i++) {
      lineNumberBeen = numberBeens.get(i);
      newDrawLineText(canvas, lineNumberBeen, i);
      //TODO  根据这一行是否有数据绘制，根据从是否是统计数据，如果是统计数据就先不处理
      if (isNormalNOdata(lineNumberBeen)) {
        newdrawLineNoDataBg(lineNumberBeen, canvas, minWith, i);
        DrawChartHelper.drawNODataText(canvas, lineNumberBeen, unselectTextPaint, TrancelentX, TrancelentY, minWith);
      }
    }
    //TODO  如果统计数据是没有数据的状态，就绘制统计数据
    if (statisNumbers != null && !statisNumbers.isHaveData) {//在统计数据是暂无数据的时候，绘制
      newdrawLineNoDataBg(statisNumbers, canvas, minWith, 0);
      DrawChartHelper.drawNODataText(canvas, statisNumbers, unselectTextPaint, TrancelentX, TrancelentY, minWith);
    }
  }

  private void newDrawLineText(Canvas cavas, LineNumberBean lineNumberBean, int hang) {
    for (int i = hStartPosition; i < hEndPosition; i++) {
      newDrawNumberText(cavas
          , lineNumberBean.numbersData.get(i)
          , hang
          , lineNumberBean.isHaveData
          , lineNumberBean.dataType == LineNumberBean.DATA_TYPE_STATICS);
    }
  }

  /**
   * 绘制一行选中的文字
   *
   * @param cavas
   * @param lineNumberBean
   * @param hang
   */
  private void newDrawLineTextS(Canvas cavas, LineNumberBean lineNumberBean, int hang) {
    for (int i = hStartPosition; i < hEndPosition; i++) {
      newDrawNumberTextOnlyS(cavas, lineNumberBean.numbersData.get(i), hang, lineNumberBean.isHaveData);
    }
  }

  /**
   * 绘制一行，没有选中的文字
   *
   * @param cavas
   * @param lineNumberBean
   * @param hang
   */
  private void newDrawLineTextUS(Canvas cavas, LineNumberBean lineNumberBean, int hang) {
    for (int i = hStartPosition; i < hEndPosition; i++) {
      newDrawNumberTextOnlyUS(cavas, lineNumberBean.numbersData.get(i), hang, lineNumberBean.isHaveData, lineNumberBean.dataType == LineNumberBean.DATA_TYPE_STATICS);
    }
  }

  @Override
  protected boolean isHaveData() {
    return ListUtils.isHaveData(numberBeens);
  }

  /**
   * 只绘制选中的文字
   *
   * @param canvas
   * @param bean
   * @param hang
   * @param isHaveData
   */
  private void newDrawNumberTextOnlyS(Canvas canvas, NumberBean bean, int hang, boolean isHaveData) {
    if (isHaveData) {//在有数据的情况下绘制
      if (bean.isSelect) {
        DrawChartHelper.drawText(canvas, bean, selectTextPaint, centerTextpaint.getTextBaseY());
        //TODO 这里要设置绘制脚标的字体
        if (bean.selectBg.supTop != null) {
          DrawChartHelper.drawSupTop(canvas, bean, selectBgPaint, selectTextPaint);
        }
      }
    }
  }

  /**
   * 只绘制没有选中的字体
   * @param canvas
   * @param bean
   * @param hang
   * @param isHaveData
   * @param isStatData  是否是统计数据，在隐藏遗漏的时候，还是要绘制统计数据
   */
  private void newDrawNumberTextOnlyUS(Canvas canvas, NumberBean bean, int hang, boolean isHaveData, boolean isStatData) {
    if (isHaveData && !bean.isSelect) {//在有数据的情况下绘制
      if (!onlyDrawSelect || isStatData) {
        DrawChartHelper.drawText(canvas, bean, unselectTextPaint, centerTextpaint.getTextBaseY());
      }
    }
  }

  /**
   * 绘制正常装填的字体，选择中和非选中
   *
   * @param canvas
   * @param bean
   * @param hang        行号
   * @param isHaveData  这一列是否有数据
   * @param isStatsData 是否是统计数据，控制遗漏数据是否绘制，如果onlyDrawSelect=true  isStatsData=true，会绘制统计数据
   */
  private void newDrawNumberText(Canvas canvas, NumberBean bean, int hang, boolean isHaveData, boolean isStatsData) {
    if (isHaveData) {//在有数据的情况下绘制
      if (bean.isSelect) {
        DrawChartHelper.drawText(canvas, bean, selectTextPaint, centerTextpaint.getTextBaseY());
        //TODO 这里要设置绘制脚标的字体
        if (bean.selectBg.supTop != null) {
          DrawChartHelper.drawSupTop(canvas, bean, selectBgPaint, selectTextPaint);
        }
      } else {
        if (!onlyDrawSelect || isStatsData) {
          DrawChartHelper.drawText(canvas, bean, unselectTextPaint, centerTextpaint.getTextBaseY());
        }
      }
    }
  }

  /**
   * 绘制top部分
   *
   * @param canvas
   */
  private void newDrawTop(Canvas canvas) {
    drawTopNormalBg(canvas);
    drawTopText(canvas);
    drawTopLine(canvas);
  }

  /**
   * 绘制top的线
   *
   * @param cavas
   */
  private void drawTopLine(Canvas cavas) {

    for (int i = hStartPosition; i < hEndPosition; i++) {
      DrawChartHelper.drawVDeivedLine(cavas, numberbeanTopData.get(i), linePaint);
    }

  }

  /**
   * 绘制topbg
   *
   * @param canvas
   */
  private void drawTopNormalBg(Canvas canvas) {
    NumberBean numberBean1 = null;
    NumberBean numberBean2 = null;
    numberBean1 = numberbeanTopData.get(hStartPosition);
    numberBean2 = numberbeanTopData.get(hEndPosition - 1);
    canvas.drawRect(numberBean1.x, numberBean1.y, numberBean2.x + attributesHepler.nuberWidth, numberBean2.y + attributesHepler.nuberHeiht, topBgColorpaint);
  }

  /**
   * 绘制top字体
   *
   * @param canvas
   */
  private void drawTopText(Canvas canvas) {
    for (int i = hStartPosition; i < hEndPosition; i++) {
      DrawChartHelper.drawText(canvas, numberbeanTopData.get(i), unselectTextPaint, centerTextpaint.getTextBaseY());
    }
  }

  /**
   * 绘制连接线的
   *
   * @param canvas
   */
  private void newDrawHaveConnect(Canvas canvas) {
    drawnormalbg(canvas);
    drawAllLine(canvas);
    newDrawOnlyNoSelecttext(canvas);
    if (!hideConnetLine) {
      drawConnetLine(canvas);
    }
    drawNewSelectBg(canvas);
    newDrawOnlySelecttext(canvas);
  }

  /**
   *
   */
  private Bitmap makeBitmap() {
    // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    Bitmap bmp = Bitmap.createBitmap((int) 4000, (int) 3000, Bitmap.Config.RGB_565);
    Canvas canvas = new Canvas(bmp);
    //canvas.drawBitmap(bitmap,0,0,null);
    newDraw(canvas);

    canvas.save(Canvas.ALL_SAVE_FLAG);
    canvas.restore();
    return bmp;
  }

  /**
   * 跟新数据，会重新的转化数据，重新的测量，并且刷新ui
   */
  public void upData() {
    //TODO  会在这里重新的测量，重新其他的操作，然后刷新ui
    //在把attributesHepler处理好之后，一定要回调一次
    List<Float> floats = obtainWH();//根据模式决定宽高
    float chartShowWidth = Math.min(getChartWidth(), getTotalDataWidth());
    numberBeens = convertData.convertMissData(attributesHepler, chartShowWidth);
    if(!ListUtils.isHaveData(numberBeens)){
      return;
    }
    statisNumbers = convertData.convertStatisNumbers(attributesHepler, chartShowWidth);
    numberbeanTopData = convertData.convertTopOrLeftListNumber(attributesHepler);
    /**
     * 根据统计数据是否有决定是否展示
     */
    if (statisNumbers != null&&!hideStatData) {
      numberBeens.addAll(statisNumbers.numbersData);
    }
    /**
     * 根据是否友numberbeanTopData 决定是否有顶部列表数据
     */
    if (numberbeanTopData == null) {
      top = 0;
    } else {
      top = attributesHepler.nuberHeiht;
      CalculateHepler.calculateTopList(numberbeanTopData, attributesHepler.lineWidth);
    }


    calculateNumberPositon();

    if (statisNumbers != null) {
      statisNumbers.updataNumberData(statisNumbers.numbersData);
    }
    centerTextpaint = new PaintHolder(unselectTextPaint, attributesHepler.nuberHeiht);
    initPosition();
    TrancelentY = getMinTrancelentY();
    invalidate();
  }

  private ConvertData convertData;

  public void setConvertData(ConvertData convertData) {
    this.convertData = convertData;
  }

  public interface ConvertData extends ConvertDataLeftOrTop {
    /**
     * 把遗漏数据整理成这种格式
     *
     * @param chartWidth
     * @param attributesHepler
     * @return
     */
    public List<LineNumberBean> convertMissData(Attributes attributesHepler, float chartWidth);

    /**
     * 转换统计数据
     *
     * @param attributesHepler
     * @param chartWidth
     * @return
     */
    public StatisNumbers convertStatisNumbers(Attributes attributesHepler, float chartWidth);
  }


/*----------------------以藏显示类型状态控制---------------------------------------------*/

  /**
   * 隐藏遗漏数据
   */
  private boolean onlyDrawSelect;

  /**
   * 隐藏统计数据
   */
  private boolean hideStatData;

  /**
   * 隐藏折现连接先
   */
  private boolean hideConnetLine;

  /**
   * 隐藏遗漏数据
   *
   * @param hideMissData
   */
  public void setHideMissData(boolean hideMissData) {
    this.onlyDrawSelect = hideMissData;
    invalidate();
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

  public boolean isHideConnetLine() {
    return hideConnetLine;
  }

  public boolean isOnlyDrawSelect() {
    return onlyDrawSelect;
  }

  /**
   * 隐藏连接线
   *
   * @param hideConnetLine
   */
  public void setHideConnetLine(boolean hideConnetLine) {
    this.hideConnetLine = hideConnetLine;
    invalidate();
  }

}
