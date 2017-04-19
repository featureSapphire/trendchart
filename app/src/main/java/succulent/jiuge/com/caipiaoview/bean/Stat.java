package succulent.jiuge.com.caipiaoview.bean;

import java.util.List;

public class Stat {

  /** 出现次数 */
  public List<Integer> count;

  /** 平均遗漏 */
  public List<Integer> avgMiss;

  /** 最大遗漏 */
  public List<Integer> maxMiss;

  /** 最大连出 */
  public List<Integer> maxSeries;

  @Override
  public String toString() {
    return "Stat [count=" + count + ", avgMiss=" + avgMiss + ", maxMiss=" + maxMiss + ", maxSeries="
        + maxSeries + "]";
  }

}
