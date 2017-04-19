package succulent.jiuge.com.caipiaoview.view.helper;

import java.util.List;

import succulent.jiuge.com.caipiaoview.view.viewbean.NumberBean;

/**
 * Created by wdd on 2017/3/1.
 * 计算位置，计算坐标等等
 */

public class CalculateHepler {
  /**
   * 计算位置，计算一列的left的坐标的位置
   * @param numberBeens
   */
  public static void calculateLeftList(List<NumberBean> numberBeens, int linePositon, float lineWidth){
    float curentX=0;
    float curentY=0;
    NumberBean numberBean =null;
    for (int i=0;i<numberBeens.size();i++){
      numberBean =numberBeens.get(i);
      numberBean.x=curentX;
      numberBean.y=curentY;
      curentY=curentY+numberBean.heith+(i!=0&&i==linePositon?lineWidth:0);
    }
  }

  /**
   * 计算toplist
   * @param numberBeens
   * @param lineWidth
   */
  public static void calculateTopList(List<NumberBean> numberBeens,float lineWidth){
    NumberBean numberBean =null;
    float currentX=0f;
    float currentY=0f;
    for (int i=0;i<numberBeens.size();i++){
      numberBean =numberBeens.get(i);
      numberBean.x=currentX;
      numberBean.y=currentY;
      currentX=currentX+numberBean.width+lineWidth;
    }
  }
}
