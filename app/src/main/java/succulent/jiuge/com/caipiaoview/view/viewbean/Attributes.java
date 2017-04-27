package succulent.jiuge.com.caipiaoview.view.viewbean;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.List;

import succulent.jiuge.com.caipiaoview.ListUtils;
import succulent.jiuge.com.caipiaoview.R;
import succulent.jiuge.com.caipiaoview.ResuorceHepler;
import succulent.jiuge.com.caipiaoview.StringUtils;

import static android.R.attr.bottomLeftRadius;
import static android.R.attr.format;
import static android.R.attr.taskOpenEnterAnimation;

/**
 * Created by wdd on 2017/3/2.
 * 自定义属性解析类，从这里获取属性值
 */

public class Attributes {
  /**
   * 三种统计颜色的标记
   */
  public static final int STAT_COUNT = 0;
  public static final int STAT_avgMiss = 1;
  public static final int STAT_maxMiss = 2;
  public static final int STAT_maxSeries = 3;

  /**
   * 竖直方向滑动
   */
  public static final int SCROLL_V = 0;
  /**
   * 水平方向滑动
   */
  public static final int SCROLL_H = 1;
  /**
   * 水平竖直都可以滑动
   */
  public static final int SCROLL_HV = 2;
  /**
   * 禁止滑动
   */
  public static final int SCROLL_NO = 3;

  /**
   * 圆形
   */
  public static final int SHAP_CIRE = 0;
  /**
   * 矩形
   */
  public static final int SHAP_RECT = 1;

  /**
   * 滑动模式
   */
  public int scrollModel;

  /**
   * 线的颜色
   */
  public int line_color;

  /**
   * 背景形状
   */
  public int select_bg_shaps;
  /**
   * 选中背景的padding
   */
  public float select_bg_pading;
  /**
   * 选中背景颜色，选中字体颜色
   */
  public int select_bgcolor, select_textcolor;
  /**
   * 数字宽度
   */
  public float nuberWidth;
  /**
   * 数字高度
   */
  public float nuberHeiht;
  /**
   * 线的宽度
   */
  public float lineWidth;
  /**
   * 一行有多少数字
   */
  public int line_number;

  /**
   * 一列有多少数字
   */
  public int row_number;
  /**
   * 正常字体的颜色
   */
  public int normal_textcolor;
  /**
   * 正常字体的大小
   */
  public float normal_textsize;
  /**
   * 奇数行背景颜色
   */
  public int normal_jibg_color;
  /**
   * 偶数行北京颜色
   */
  public int normal_oubg_color;

  /**
   * 上脚标字体的颜色
   */
  public int sup_textcolor;

  /**
   * 上脚标字体的颜色
   */
  public int sup_bgcolor;
  /**
   * 上角标字体大小
   */
  public float sup_textsize;

  /**
   * 上角标半径
   */
  public float sup_radius;
  /**
   * 上面列表背景颜色
   */
  public int top_list_bgcolor;

  public int minScollLength;
  /**
   * 颜色
   */
  public int count_textcolor;
  /**
   * 颜色
   */
  public int avgMiss_textcolor;
  /**
   * 颜色
   */
  public int maxMiss_textcolor;
  /**
   * 颜色
   */
  public int maxSeries_textcolor;
  /**
   * 连接线的宽度
   */
  public float connect_line_width;
  /**
   * 连接线的颜色
   */
  public int connect_line_color;
  private String weight_all;
  /**
   * 是否连线
   */
  public boolean isconnect_line;

  public void init(Context context, AttributeSet attrs) {
    minScollLength = ViewConfiguration.get(context).getScaledTouchSlop();
    TypedArray localTypedArray = context.obtainStyledAttributes(attrs, R.styleable.chart);
    scrollModel = localTypedArray.getInt(R.styleable.chart_scroll_model, SCROLL_HV);

    nuberWidth = localTypedArray.getDimension(R.styleable.chart_number_width
        , ResuorceHepler.getDimension(context, R.dimen.numberWidth));

    nuberHeiht = localTypedArray.getDimension(R.styleable.chart_number_heith
        , ResuorceHepler.getDimension(context, R.dimen.numberHeith));
    //在横向不可以滑动的模式下是没有效果的
    lineWidth = localTypedArray.getDimension(R.styleable.chart_line_width
        , ResuorceHepler.getDimension(context, R.dimen.lineWith));

    line_color = localTypedArray.getColor(R.styleable.chart_line_color
        , ResuorceHepler.getColor(context, R.color.line));

    select_textcolor = localTypedArray.getColor(R.styleable.chart_select_textcolor
        , ResuorceHepler.getColor(context, R.color.whilte_text));

    select_bgcolor = localTypedArray.getColor(R.styleable.chart_select_bgcolor
        , ResuorceHepler.getColor(context, R.color.select_bg));

    select_bg_shaps = localTypedArray.getInt(R.styleable.chart_select_bg_shap
        , SHAP_CIRE);

    select_bg_pading = localTypedArray.getDimension(R.styleable.chart_select_bg_padding
        , ResuorceHepler.getDimension(context, R.dimen.select_bg_padding));

    line_number = localTypedArray.getInt(R.styleable.chart_line_number
        , 11);
    row_number = localTypedArray.getInt(R.styleable.chart_row_number
        , 10);

    normal_textcolor = localTypedArray.getColor(R.styleable.chart_normal_textcolor
        , ResuorceHepler.getColor(context, R.color.gray_text));

    normal_textsize = localTypedArray.getDimension(R.styleable.chart_normal_textsize
        , ResuorceHepler.getDimension(context, R.dimen.normal_text_size));

    normal_jibg_color = localTypedArray.getColor(R.styleable.chart_normal_jibg_color
        , ResuorceHepler.getColor(context, R.color.gray_bg));

    normal_oubg_color = localTypedArray.getColor(R.styleable.chart_normal_oubg_color
        , ResuorceHepler.getColor(context, R.color.whilte_text));

    sup_textcolor = localTypedArray.getColor(R.styleable.chart_sup_textcolor
        , ResuorceHepler.getColor(context, R.color.whilte_text));

    sup_bgcolor = localTypedArray.getColor(R.styleable.chart_sup_bgcolor
        , ResuorceHepler.getColor(context, R.color.colorPrimaryDark));

    sup_textsize = localTypedArray.getDimension(R.styleable.chart_sup_textsize
        , ResuorceHepler.getDimension(context, R.dimen.sup_text_size));
    /**
     * 脚标圆角半径
     */
    sup_radius = localTypedArray.getDimension(R.styleable.chart_sup_radius
        , ResuorceHepler.getDimension(context, R.dimen.sup_radius));

    top_list_bgcolor = localTypedArray.getColor(R.styleable.chart_top_list_bgcolor
        , ResuorceHepler.getColor(context, R.color.colorAccent));

    /** 出现次数 */
    count_textcolor = localTypedArray.getColor(R.styleable.chart_count_textcolor
        , ResuorceHepler.getColor(context, R.color.purple_sky));
    /** 平均遗漏 */
    avgMiss_textcolor = localTypedArray.getColor(R.styleable.chart_avgMiss_textcolor
        , ResuorceHepler.getColor(context, R.color.green_sky));
    /** 最大遗漏 */
    maxMiss_textcolor = localTypedArray.getColor(R.styleable.chart_maxMiss_textcolor
        , ResuorceHepler.getColor(context, R.color.brown));
    /** 最大连出 */
    maxSeries_textcolor = localTypedArray.getColor(R.styleable.chart_maxSeries_textcolor
        , ResuorceHepler.getColor(context, R.color.blue_sky));

    connect_line_width = localTypedArray.getDimension(R.styleable.chart_connect_line_width
        , ResuorceHepler.getDimension(context, R.dimen.connect_line_width));

    connect_line_color = localTypedArray.getColor(R.styleable.chart_connect_line_color
        , ResuorceHepler.getColor(context, R.color.connect_line_color));

    weight_all = localTypedArray.getString(R.styleable.chart_weight_all);
    isconnect_line = localTypedArray.getBoolean(R.styleable.chart_isconnect_line, false);
  }

  public int getTotalWeight() {
    List<Integer> allWeight = getAllWeight();
    if (ListUtils.isHaveData(allWeight)) {
      int total = 0;
      for (int i = 0; i < allWeight.size(); i++) {
        total = total + allWeight.get(i);
      }
      return total;
    }
    return 0;
  }

  /**
   * 使用了权重模式
   *
   * @return
   */
  public boolean isWeihtMode() {
    return (scrollModel == SCROLL_V || scrollModel == SCROLL_NO) && getTotalWeight() > 0;
  }

  private List<Integer> weight;

  public List<Integer> getAllWeight() {
    if (StringUtils.isEmpty(weight_all)) {
      return null;
    }
    if (weight == null) {
      weight = new ArrayList<>();
      String[] split = weight_all.split(",");
      for (int i = 0; i < split.length; i++) {
        weight.add(Integer.valueOf(split[i]));
      }
    }
    return weight;
  }

  @Override
  public String toString() {
    return "AttributesHepler{" +
        "scrollModel=" + scrollModel +
        ", line_color=" + line_color +
        ", select_bg_shaps=" + select_bg_shaps +
        ", select_bg_pading=" + select_bg_pading +
        ", select_bgcolor=" + select_bgcolor +
        ", select_textcolor=" + select_textcolor +
        ", nuberWidth=" + nuberWidth +
        ", nuberHeiht=" + nuberHeiht +
        ", lineWidth=" + lineWidth +
        ", line_number=" + line_number +
        ", row_number=" + row_number +
        ", normal_textcolor=" + normal_textcolor +
        ", normal_textsize=" + normal_textsize +
        ", normal_jibg_color=" + normal_jibg_color +
        ", normal_oubg_color=" + normal_oubg_color +
        ", sup_textcolor=" + sup_textcolor +
        ", sup_bgcolor=" + sup_bgcolor +
        ", sup_textsize=" + sup_textsize +
        ", sup_radius=" + sup_radius +
        ", top_list_bgcolor=" + top_list_bgcolor +
        '}';
  }

  /**
   * 可以横向滑动
   *
   * @return
   */
  public boolean isScrollHModel() {
    return scrollModel == SCROLL_H;
  }

  /**
   * 可以横向滑动
   *
   * @return
   */
  public boolean isScrollVModel() {
    return scrollModel == SCROLL_V;
  }

  /**
   * 都可以滑动
   *
   * @return
   */
  public boolean isScrollAll() {
    return scrollModel == SCROLL_HV;
  }

  /**
   * 都不可以滑动
   *
   * @return
   */
  public boolean notScrollModel() {
    return scrollModel == SCROLL_NO;
  }

  public int getStatColor(int statType) {
    int color = count_textcolor;
    switch (statType) {
      case STAT_COUNT:
        color = count_textcolor;
        break;
      case STAT_avgMiss:
        color = avgMiss_textcolor;
        break;
      case STAT_maxMiss:
        color = maxMiss_textcolor;
        break;
      case STAT_maxSeries:
        color = maxSeries_textcolor;
        break;
    }
    return color;
  }

}
