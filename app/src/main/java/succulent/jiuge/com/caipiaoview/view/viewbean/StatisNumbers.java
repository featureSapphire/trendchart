package succulent.jiuge.com.caipiaoview.view.viewbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdd on 2017/3/6.
 * 统计数据,里面可能会有多列数据，多列统计数据
 * 准备bean的时候，一定是已经测量好的状态
 */

public class StatisNumbers extends NoDataBean {

  /**
   * 统计数据信息
   */
  public List<LineNumberBean> numbersData;

  public StatisNumbers() {

    numbersData = new ArrayList<>();
  }

  public StatisNumbers(float viewShowWidth, int textColor, float textSize, boolean isHaveData, String desc, List<LineNumberBean> numbersData) {
    super(viewShowWidth, textColor, textSize, isHaveData, desc);
    this.numbersData = numbersData;
  }

  /**
   * 设置一次数据就会刷新一次宽高
   *
   * @param numbersData
   */
  public void updataNumberData(List<LineNumberBean> numbersData) {
    this.numbersData = numbersData;
    calculateWH();
  }

  private void calculateWH() {
    width = viewShowWidth;
    int size = numbersData.size();
    height = size * numbersData.get(0).numbersData.get(0).heith;
    x = numbersData.get(0).numbersData.get(0).x;
    y = numbersData.get(0).numbersData.get(0).y;
  }
}
