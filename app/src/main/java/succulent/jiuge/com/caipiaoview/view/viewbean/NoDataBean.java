package succulent.jiuge.com.caipiaoview.view.viewbean;

import java.util.Observable;

/**
 * Created by wdd on 2017/3/7.
 * 没有数据的时候，需要显示的数据
 */

public class NoDataBean {

  public NoDataBean() {

  }

  /**
   * 整个View显示的宽度
   */
  public float viewShowWidth;

  protected float x;
  protected float y;
  protected float width;
  protected float height;

  /**
   * 描述信息，字体颜色
   */
  public int textColor;
  /**
   * 描述信息字体大小
   */
  public float textSize;
  /**
   * 是否有数据
   */
  public boolean isHaveData;
  /**
   * 没有数据的描述
   */
  public String desc = "暂无本期次数据";

  /**
   * 获取在没有数据的时候，绘制字体的x坐标，根据TrancelentX动态变化
   *
   * @return
   */
  public float getX(float TrancelentX) {
    return x - TrancelentX;
  }

  /**
   * 获取在没有数据的时候，绘制字体的y坐标,根据TrancelentY动态变化
   *
   * @param TrancelentY
   * @return
   */
  public float getY(float TrancelentY) {
    return y ;
  }

  /**
   * 在那个宽度的区域上绘制字体
   * 默认实现是根据viewShowWidth
   *
   * @return
   */
  public float getWidth() {
    width = viewShowWidth;
    return width;
  }

  /**
   * 在那个宽度的区域上绘制字体
   *
   * @return
   */
  public float getHeight() {
    return height;
  }

  public NoDataBean(float viewShowWidth, int textColor, float textSize, boolean isHaveData, String desc) {
    this.viewShowWidth = viewShowWidth;
    this.textColor = textColor;
    this.textSize = textSize;
    this.isHaveData = isHaveData;
    this.desc = desc;
  }
}
