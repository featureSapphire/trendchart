package succulent.jiuge.com.caipiaoview.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.R.attr.key;

public class TermData {

  /** 期次 */
  public String issue;

  /** 开奖号码 */
  public List<String> winNumber;

  /** 遗漏 */
  public Map<String, List<Integer>> missNumber;

  /** 重号个数 */
  public Map<String, List<Integer>> overNumber;

  @Override
  public String toString() {
    return "TermData{" +
        "issue='" + issue + '\'' +
        ", winNumber=" + winNumber +
        ", missNumber=" + missNumber +
        ", overNumber=" + overNumber +
        '}';
  }

  /**
   * 获取遗漏数据，红球的遗漏
   * @return
   */
  public List<Integer> getRedMissDataSSQDDT(){
    String keyss=MissTrendKey.T01F01.redmiss.toString();
   return missNumber.get(keyss);
  } /**
   * 获取遗漏数据，篮球的遗漏

   * @return
   */
  public List<Integer> getBlueMissDataSSQDDT(){
    String keyss=MissTrendKey.T01F01.bluemiss.toString();
   return missNumber.get(keyss);
  }
}
