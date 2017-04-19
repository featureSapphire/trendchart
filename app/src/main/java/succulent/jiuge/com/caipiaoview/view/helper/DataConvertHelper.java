package succulent.jiuge.com.caipiaoview.view.helper;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import succulent.jiuge.com.caipiaoview.R;
import succulent.jiuge.com.caipiaoview.ResuorceHepler;
import succulent.jiuge.com.caipiaoview.view.viewbean.NumberBean;

/**
 * Created by wdd on 2017/3/1.
 * 把数据转化为可以显示的数据
 */

public class DataConvertHelper {

  /**
   * 获取左边list，需要绘制的数据
   * @param number
   * @param width
   * @param height
   * @return
   */
  public static List<NumberBean> getLeftListTestData(int number, float width, float height, int normalTextColor, float normalTextSize, float lineWidth){
    ArrayList<NumberBean> numberBeens = new ArrayList<>();
    NumberBean numberBean=null;
    for (int i=0;i<number;i++){
      numberBean=new NumberBean();
      numberBean.data=i+"";
      numberBean.normalTextColor=normalTextColor;
      numberBean.normalTextSize=normalTextSize;
      numberBean.width=width-lineWidth;
      numberBean.heith=height;
      numberBeens.add(numberBean);
    }
    return numberBeens;
  }

  public static int getStaticsColor(Context context,int positon){
    switch(positon){
      case 0:
      return ResuorceHepler.getColor(context, R.color.purple_sky);
      case 1:
      return ResuorceHepler.getColor(context, R.color.green_sky);
      case 2:
      return ResuorceHepler.getColor(context, R.color.brown);
      case 3:
      return ResuorceHepler.getColor(context, R.color.blue_sky);
    }
    return ResuorceHepler.getColor(context, R.color.gray_bg);

  }

  /**
   * 获取统计数据
   * @param context
   * @param number
   * @param width
   * @param height
   * @param normalTextSize
   * @return
   */
  public static List<List<NumberBean>> getStatisData(Context context,int number,float width,float height,float normalTextSize){
    ArrayList<List<NumberBean>> numberBeenss = new ArrayList<>();
    ArrayList<NumberBean> numberBeens=null;
    NumberBean numberBean=null;
    for (int j=0;j<4;j++){
      numberBeens = new ArrayList<>();
      numberBeenss.add(numberBeens);
      int color=getStaticsColor(context,j);
      for (int i=0;i<number;i++){
        numberBean=new NumberBean();
        numberBean.data="1"+i;
        numberBean.normalTextColor=color;
        numberBean.normalTextSize=normalTextSize;
        numberBean.width=width;
        numberBean.heith=height;
        numberBeens.add(numberBean);
      }
    }

    return numberBeenss;

  }

  public static List<List<NumberBean>> getNoStatisData(Context context,int number,float width,float height,float normalTextSize) {
    ArrayList<List<NumberBean>> numberBeenss = new ArrayList<>();
    ArrayList<NumberBean> numberBeens = null;
    NumberBean numberBean = null;
    for (int j = 0; j < 4; j++) {
      numberBeens = new ArrayList<>();
      numberBeenss.add(numberBeens);
      int color = getStaticsColor(context, j);
        numberBean = new NumberBean();
        numberBean.data = "1" + 2;
        numberBean.normalTextColor = color;
        numberBean.normalTextSize = normalTextSize;
        numberBean.width = width;
        numberBean.heith = height;
        numberBeens.add(numberBean);

    }

    return numberBeenss;
  }




  /**
   * 获取左边list，需要绘制的数据
   * @param number
   * @param width
   * @param height
   * @return
   */
  public static List<NumberBean> getTopListTestData(int number,float width,float height,int normalTextColor,float normalTextSize){
    ArrayList<NumberBean> numberBeens = new ArrayList<>();
    NumberBean numberBean=null;
    for (int i=0;i<number;i++){
      numberBean=new NumberBean();
      numberBean.data="01"+i;

      numberBean.normalTextColor=normalTextColor;
      numberBean.normalTextSize=normalTextSize;
      numberBean.width=width;
      numberBean.heith=height;
      numberBeens.add(numberBean);
    }
    return numberBeens;
  }
}
