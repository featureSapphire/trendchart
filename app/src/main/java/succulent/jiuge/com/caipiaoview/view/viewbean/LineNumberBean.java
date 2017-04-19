package succulent.jiuge.com.caipiaoview.view.viewbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdd on 2017/3/7.
 * 一行需要展示的数据
 */

public class LineNumberBean extends NoDataBean {
  public static final int DATA_TYPE_NORMAL = 0;
  /**
   * 统计数据类型
   */
  public static final int DATA_TYPE_STATICS = 1;

  /**
   * 数据类型，统计数据类型，正常的数据类型
   */
  public int dataType;

  /**
   * 一行的数据信息
   */
  public List<NumberBean> numbersData;

  public LineNumberBean() {
    numbersData = new ArrayList<>();
  }

  public LineNumberBean(int dataType, List<NumberBean> numbersData) {
    this.dataType = dataType;
    this.numbersData = numbersData;
  }

  public LineNumberBean(float viewShowWidth, int textColor, float textSize, boolean isHaveData, String desc, int dataType, List<NumberBean> numbersData) {
    super(viewShowWidth, textColor, textSize, isHaveData, desc);
    this.dataType = dataType;
    this.numbersData = numbersData;
  }

  @Override
  public float getX(float TrancelentX) {
      x= numbersData.get(0).x;
    return super.getX(TrancelentX);
  }

  @Override
  public float getY(float TrancelentY) {
    y = numbersData.get(0).y;
    return y;
  }

  @Override
  public float getHeight() {
    return height = numbersData.get(0).heith;
  }

}
