package succulent.jiuge.com.caipiaoview.view.viewbean;

import static android.R.attr.textSize;

/**
 * Created by wdd on 2017/2/24.
 */

public class NumberBean {
  public boolean isHaveBottomLine;

  /**
   * 背景bean
   */
  public SelectBgBean selectBg;


  /**
   * x坐标
   */
  public float x;
  /**
   * y坐标
   */
  public float y;
  /**
   * 绘制的data
   */
  public String data;
  /**
   * 正常情况下字体的颜色
   */
  public int normalTextColor;
  /**
   * 是否选中状态
   */
  public boolean isSelect;
  /**
   * 字体大小
   */
  public float normalTextSize;
  /**
   * 矩形宽度
   */
  public float width;
  /**
   * 矩形高
   */
  public float heith;



  /**
   * 背景bean
   */
  public static class SelectBgBean {

    /**
     * 圆形背景
     */
    public static final int Bg_TYPE_CIRE = 0;
    /**
     * 矩形背景
     */
    public static final int Bg_TYPE_RECT = 1;
    /**
     * 左上角的脚标bean
     */
    public SupTop supTop;

    /**
     * 类型，现在有圆形，矩形
     */
    public int bgType;

    /**
     * 背景颜色
     */
    public int bgColor;
    /**
     * 数据字体的颜色
     */
    public int textColor;

    /**
     * 左边padding
     */
    public float paddingLeft;
    /**
     * 右边padding
     */
    public float paddingRight;
    /**
     * 上边padding
     */
    public float paddingTop;
    /**
     * 下边padding
     */
    public float paddingBottom;

  }

  /**
   * 上脚标
   */
  public static class SupTop {
    public float textSize;
    /**
     * 背景颜色
     */
    public int bgColor;
    /**
     * 数据字体的颜色
     */
    public int textColor;
    /**
     * sup数据
     */
    public String data;
    /**
     * sup半径
     */
    public float r;
    /**
     * 百分比布局相对于父numberbean,x，圆心位置
     * 小于1
     */
    public float percentX;
    /**
     * 百分比布局相对于父numberbean,Y，圆心位置
     * 小于1
     */
    public float percentY;
  }
}
