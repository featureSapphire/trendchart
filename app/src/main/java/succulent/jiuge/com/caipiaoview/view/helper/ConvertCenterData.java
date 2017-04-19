package succulent.jiuge.com.caipiaoview.view.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import succulent.jiuge.com.caipiaoview.ListUtils;
import succulent.jiuge.com.caipiaoview.StringUtils;
import succulent.jiuge.com.caipiaoview.bean.GetMissNumberResponse;
import succulent.jiuge.com.caipiaoview.bean.MissTrendKey;
import succulent.jiuge.com.caipiaoview.bean.Stat;
import succulent.jiuge.com.caipiaoview.bean.TermData;
import succulent.jiuge.com.caipiaoview.view.viewbean.Attributes;
import succulent.jiuge.com.caipiaoview.view.viewbean.LineNumberBean;
import succulent.jiuge.com.caipiaoview.view.viewbean.NumberBean;
import succulent.jiuge.com.caipiaoview.view.viewbean.StatisNumbers;

/**
 * Created bywdd on 2017/3/13.
 *数据转换
 */

public class ConvertCenterData {

  /**
   * 一行总的有多少数据
   */
  private int lineNumber;
  /**
   * 不带有统计数据的中间显示的数据
   */
  private List<LineNumberBean> centerNoStatData;
  private StatisNumbers statisNumbers;

  public ConvertCenterData(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  /**
   * 获取多少期的数据
   *
   * @param response
   * @param terms    30 50 100 200
   * @return 如果没有数据返回的是空集合
   */
  public List<TermData> getTermsByTerm(GetMissNumberResponse response, int terms) {
    ArrayList<TermData> termDatas = new ArrayList<>();
    if (response == null || response.data == null || response.data.size() < terms) {
      return termDatas;
    }
    for (int i = response.data.size() - terms; i < response.data.size(); i++) {
      termDatas.add(response.data.get(i));
    }
    return termDatas;
  }

  /**
   * @param termDatas        指定期次的数据
   * @param chartWidth       列表的总的宽度
   * @param attributesHepler 自定义属性中传递的值
   * @param missDataKey      根据采种或者是玩法获取遗漏数据的key
   * @return
   */
  public List<LineNumberBean> convertMissData(List<TermData> termDatas, float chartWidth, Attributes attributesHepler, String missDataKey) {
    List<LineNumberBean> data = new ArrayList();
    if (!ListUtils.isHaveData(termDatas)) {
      return data;
    }

    /**
     * 一期的数据
     */
    TermData termData = null;
    /**
     * 一行数据bean
     */
    LineNumberBean lineNumberBean = null;
    /**
     * 一行数据的集合
     */
    List<NumberBean> numberBeenss = null;
    boolean termHaveData = false;
    for (int i = 0; i < termDatas.size(); i++) {
      termData = termDatas.get(i);
      termHaveData = isTermDataHaveData(termData, missDataKey);
      numberBeenss = toListNumbers(termHaveData ? getMissData(missDataKey, termData) : getPlaceholderData(), null, attributesHepler);
      //TODO  根据从什么来判断是否有数据
      lineNumberBean = getLineNumberBean(attributesHepler, chartWidth, numberBeenss, "暂无数据", LineNumberBean.DATA_TYPE_NORMAL, termHaveData);
      data.add(lineNumberBean);
    }
    centerNoStatData = data;
    return data;
  }

  /**
   * 判断一期数据是否有数据，判断依据待协商   //TODO  判断一期数据是否有数据的方法
   *
   * @param termData
   * @param missDataKey
   * @return
   */
  private boolean isTermDataHaveData(TermData termData, String missDataKey) {
    if (termData == null || termData.missNumber == null) {
      return false;
    }
    List<Integer> integers = termData.missNumber.get(missDataKey);
    return ListUtils.isHaveData(integers);
  }

  /**
   * 暂时重复号码没有处理，只是处理了选中的号码
   *
   * @param missdataList
   * @param overNumber
   * @param attributesHepler
   * @return
   */
  private List<NumberBean> toListNumbers(List<Integer> missdataList, List<Integer> overNumber, Attributes attributesHepler) {
    ArrayList<NumberBean> numberBeens = new ArrayList<>();
    NumberBean numberBean = null;
    boolean isSelect = false;
    int data = 0;
    for (int i = 0; i < missdataList.size(); i++) {
      data = missdataList.get(i);
      isSelect = isSelect(data);
      numberBean = getNumberBean(formatData(isSelect ? i : data), null, isSelect, attributesHepler);
      numberBeens.add(numberBean);
    }
    return numberBeens;
  }

  /**
   * 根据key,获取每一期的遗漏数据
   *
   * @param missDataKey
   * @param termData
   * @return
   */
  private List<Integer> getMissData(String missDataKey, TermData termData) {
    return termData.missNumber.get(missDataKey);
  }

  /**
   * 根据key,获取统计数据，这里的统计数据是，包涵 30，50，100，200,期的数据
   *
   * @param statKey
   * @param stringMapMap
   * @return
   */
  private Map<String, Stat> getStatData(String statKey, Map<String, Map<String, Stat>> stringMapMap) {
    return stringMapMap.get(statKey);
  }

  /**
   * 根据key,获取统计数据，这里的统计数据是，包涵 30，50，100，200,期的数据
   *
   * @param statKey      玩法统计key
   * @param termKey      期次key
   * @param stringMapMap 总的统计数据
   * @return 指定的期数的期次
   */
  private Stat getStatData(String statKey, String termKey, Map<String, Map<String, Stat>> stringMapMap) {
    return stringMapMap.get(statKey).get(termKey);
  }

  /**
   * 获取指定长度的占位数据
   *
   * @return
   */
  public List<Integer> getPlaceholderData() {
    ArrayList<Integer> integers = new ArrayList<>();
    for (int i = 0; i < lineNumber; i++) {
      integers.add(-1);
    }
    return integers;
  }

  /**
   * 获取统计数据
   *
   * @param statKey          指定获取统计数据的指定彩种，或者是指定玩法获取的key
   * @param termKey          指定获取指定期次的统计数据key
   * @param stringMapMap     总的所有的统计数据
   * @param attributesHepler 自定义属性bean
   * @param chartWidth       列表显示的宽度
   * @return
   */
  public StatisNumbers convertStatisNumbers(String statKey, String termKey, Map<String, Map<String, Stat>> stringMapMap, Attributes attributesHepler, float chartWidth) {
    Stat statData = getStatData(statKey, termKey, stringMapMap);
    //TODO 根据什么依据来判断是否有数据
    boolean statHaveData = isStatHaveData(statData);
    ArrayList<LineNumberBean> lineNumberBeens = new ArrayList<>();
    List<Integer> avgMiss = statHaveData ? statData.avgMiss : getPlaceholderData();
    List<Integer> count = statHaveData ? statData.count : getPlaceholderData();
    List<Integer> maxMiss = statHaveData ? statData.maxMiss : getPlaceholderData();
    List<Integer> maxSeries = statHaveData ? statData.maxSeries : getPlaceholderData();
    List<NumberBean> numberBeen_count = toStatListNumbers(count, attributesHepler, Attributes.STAT_COUNT);
    List<NumberBean> numberBeen_avgMiss = toStatListNumbers(avgMiss, attributesHepler, Attributes.STAT_avgMiss);
    List<NumberBean> numberBeen_maxMiss = toStatListNumbers(maxMiss, attributesHepler, Attributes.STAT_maxMiss);
    List<NumberBean> numberBeen_maxSeries = toStatListNumbers(maxSeries, attributesHepler, Attributes.STAT_maxSeries);
    LineNumberBean lin_count = getLineNumberBean(attributesHepler, chartWidth, numberBeen_count, "暂无数据", LineNumberBean.DATA_TYPE_STATICS, statHaveData);
    LineNumberBean lin_avgMiss = getLineNumberBean(attributesHepler, chartWidth, numberBeen_avgMiss, "暂无数据", LineNumberBean.DATA_TYPE_STATICS, statHaveData);
    LineNumberBean lin_maxMiss = getLineNumberBean(attributesHepler, chartWidth, numberBeen_maxMiss, "暂无数据", LineNumberBean.DATA_TYPE_STATICS, statHaveData);
    LineNumberBean lin_maxSeries = getLineNumberBean(attributesHepler, chartWidth, numberBeen_maxSeries, "暂无数据", LineNumberBean.DATA_TYPE_STATICS, statHaveData);
    lineNumberBeens.add(lin_count);
    lineNumberBeens.add(lin_avgMiss);
    lineNumberBeens.add(lin_maxMiss);
    lineNumberBeens.add(lin_maxSeries);
    StatisNumbers statisNumbers = new StatisNumbers(chartWidth
        , attributesHepler.normal_textcolor
        , attributesHepler.normal_textsize
        , statHaveData
        , "暂无数据"
        , lineNumberBeens);
    this.statisNumbers = statisNumbers;
    return statisNumbers;
  }

  /**
   * 判断统计是否有数据
   *
   * @param stat
   * @return
   */
  private boolean isStatHaveData(Stat stat) {
    return stat != null && stat.avgMiss != null && stat.avgMiss.size() > 0;
  }

  /**
   * 获取一行数据bean
   *
   * @return
   */
  private LineNumberBean getLineNumberBean(Attributes attributesHepler, float chartWidth, List<NumberBean> numberBeenss, String noDataDesc, int typeData, boolean isHaveData) {
    return new LineNumberBean(chartWidth, attributesHepler.normal_jibg_color, attributesHepler.normal_textsize, isHaveData, noDataDesc, typeData, numberBeenss);
  }

  /**
   * 返回一个号码bean
   *
   * @param noramlData       正常显示的数据，或者是选中显示的数据
   * @param supData          假如有脚标的情况下显示的数据   如果是null,就是没有脚标
   * @param isSelect         是否选中，如果被选中的话就会友selectbg
   * @param attributesHepler 自定义属性bean
   * @return
   */
  public NumberBean getNumberBean(String noramlData, String supData, boolean isSelect, Attributes attributesHepler) {
    NumberBean bean = new NumberBean();
    bean.data = noramlData;
    bean.isSelect = isSelect;
    bean.width = attributesHepler.nuberWidth;
    bean.heith = attributesHepler.nuberHeiht;
    bean.normalTextSize = attributesHepler.normal_textsize;
    bean.normalTextColor = attributesHepler.normal_textcolor;
    if (bean.isSelect) {
      bean.selectBg = getSelectBgBean(attributesHepler, supData);
    }
    return bean;
  }

  /**
   * 获取selectBg，背景bean   如果supData为null,就是没有脚标数据
   *
   * @param attributesHepler
   * @param supData
   * @return
   */
  private NumberBean.SelectBgBean getSelectBgBean(Attributes attributesHepler, String supData) {
    NumberBean.SelectBgBean selectBg = new NumberBean.SelectBgBean();
    selectBg.bgType = attributesHepler.select_bg_shaps;
    selectBg.paddingLeft = attributesHepler.select_bg_pading;
    selectBg.paddingRight = attributesHepler.select_bg_pading;
    selectBg.paddingTop = attributesHepler.select_bg_pading;
    selectBg.paddingBottom = attributesHepler.select_bg_pading;
    selectBg.bgColor = attributesHepler.select_bgcolor;
    selectBg.textColor = attributesHepler.select_textcolor;
    if (!StringUtils.isEmpty(supData)) {
      selectBg.supTop = getSupTop(attributesHepler, supData);
    }
    return selectBg;
  }

  /**
   * 获取左上脚的，脚标bean
   *
   * @param attributesHepler
   * @param data
   * @return
   */
  public NumberBean.SupTop getSupTop(Attributes attributesHepler, String data) {
    NumberBean.SupTop supTop = new NumberBean.SupTop();
    supTop.r = attributesHepler.sup_radius;
    supTop.data = data;//重复数据
    supTop.textSize = attributesHepler.sup_textsize;
    supTop.bgColor = attributesHepler.sup_bgcolor;
    supTop.textColor = attributesHepler.sup_textcolor;
    return supTop;
  }

  /**
   * 号码是否选中
   *
   * @return
   */
  private boolean isSelect(Integer integer) {
    return integer == 0;
  }

  /**
   * 格式化号码
   *
   * @param integer
   * @return
   */
  private String formatData(Integer integer) {
    String str = "";
    if (integer < 10) {
      str = "0";
    }
    return str + integer;
  }

  /**
   * 暂时重复号码没有处理，只是处理了选中的号码
   *
   * @param missdataList
   * @param attributesHepler
   * @return
   */
  private List<NumberBean> toStatListNumbers(List<Integer> missdataList, Attributes attributesHepler, int statType) {
    ArrayList<NumberBean> numberBeens = new ArrayList<>();
    NumberBean numberBean = null;
    int data = 0;
    for (int i = 0; i < missdataList.size(); i++) {
      data = missdataList.get(i);
      numberBean = getStatNumberBean(data + "", attributesHepler, statType);
      numberBeens.add(numberBean);
    }
    return numberBeens;
  }

  /**
   * 获取统计NumberBean
   *
   * @param noramlData
   * @param attributesHepler
   * @param statType
   * @return
   */
  private NumberBean getStatNumberBean(String noramlData, Attributes attributesHepler, int statType) {
    NumberBean bean = new NumberBean();
    bean.data = noramlData;
    bean.width = attributesHepler.nuberWidth;
    bean.heith = attributesHepler.nuberHeiht;
    bean.normalTextSize = attributesHepler.normal_textsize;
    bean.normalTextColor = attributesHepler.getStatColor(statType);
    return bean;
  }

  /**
   * 获取左边列表数据的展示，
   *
   * @param attributesHepler 左边控件的自定义属性
   * @param isSelect         产生的bean是否是选中状态
   * @return
   */
  public List<NumberBean> getLeftListNumbers(List<TermData> termDatas, Attributes attributesHepler, boolean isSelect) {
    ArrayList<NumberBean> numberBeens = new ArrayList<>();
    if (!ListUtils.isHaveData(termDatas)) {
      return numberBeens;
    }
    TermData termData = null;
    for (int i = 0; i < termDatas.size(); i++) {
      termData = termDatas.get(i);
      numberBeens.add(getNumberBean(StringUtils.isEmpty(termData.issue) ? "" : termData.issue, null, isSelect, attributesHepler));
    }
    return numberBeens;
  }

  /**
   * 获取顶端列表的数据
   *
   * @param attributesHepler 中间数据的自定义属性集合
   * @return
   */
  public List<NumberBean> convertTopListNumber(Attributes attributesHepler) {
    ArrayList<NumberBean> numberBeens = new ArrayList<>();
    for (int i = 0; i < lineNumber; i++) {
      numberBeens.add(getNumberBean(formatData(i), null, false, attributesHepler));
    }
    return numberBeens;
  }

  /**
   * 每一次进行数据转换之后掉用
   *
   * @return
   */
  public List<LineNumberBean> getTotalData() {
    ArrayList<LineNumberBean> lineNumberBeen = new ArrayList<>();
    lineNumberBeen.addAll(centerNoStatData);
    if (statisNumbers != null) {
      lineNumberBeen.addAll(statisNumbers.numbersData);
    }
    return lineNumberBeen;
  }

  /**
   * 提取冷热数据
   *
   * @param stats     统计数据
   * @param termDatas 遗漏数据
   * @param statKey   玩法对应的统计数据的key
   * @param missKey   遗漏数据玩法对应的key
   * @return 对于遗漏数据，如果没有返回的-1,如果统计数据没有，返回一个空几何
   */
  public List<List<Integer>> obtainHotData(Map<String, Map<String, Stat>> stats, List<TermData> termDatas, String statKey, String missKey) {
    ArrayList<List<Integer>> lists = new ArrayList<>();
    Map<String, Stat> statData = getStatData(statKey, stats);
    List<Integer> missData = null;
    TermData termData = null;
    Map<String, List<Integer>> missNumber = null;
    if (ListUtils.isHaveData(termDatas)) {
      termData = termDatas.get(termDatas.size());
    }
    if (termData != null) {
      missNumber = termData.missNumber;
    }
    if (missNumber != null) {
      missData = missNumber.get(missKey);
    }
    boolean haveData = ListUtils.isHaveData(missData);
    if (statData == null) {
      return lists;
    }
    List<Integer> count_30 = statData.get(MissTrendKey.StatKey.S30).count;
    List<Integer> count_50 = statData.get(MissTrendKey.StatKey.S50).count;
    List<Integer> count_100 = statData.get(MissTrendKey.StatKey.S100).count;
    List<Integer> count_200 = statData.get(MissTrendKey.StatKey.S200).count;
    if (!ListUtils.isHaveData(count_30)) {
      return lists;
    }
    ArrayList<Integer> line;
    for (int i = 0; i < count_30.size(); i++) {
      line = new ArrayList<>();
      line.add(count_30.get(i));
      line.add(count_50.get(i));
      line.add(count_100.get(i));
      line.add(count_200.get(i));
      line.add(haveData ? missData.get(i) : -1);
      lists.add(line);
    }
    return lists;
  }

  /**
   * 提取开奖号码,只是简单的提取开奖号码
   *
   * @param termDatas
   * @return 如果期次为null, 返回空集合
   */
  public List<List<String>> obtainOpenWin(List<TermData> termDatas) {
    ArrayList<List<String>> integers = new ArrayList<>();
    if (!ListUtils.isHaveData(termDatas)) {
      return integers;
    }
    for (int i = 0; i < termDatas.size(); i++) {
      integers.add(ListUtils.isHaveData(termDatas.get(i).winNumber) ? termDatas.get(i).winNumber : new ArrayList<String>());
    }
    return integers;
  }

}
