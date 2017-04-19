package succulent.jiuge.com.caipiaoview.bean;


import java.util.List;
import java.util.Map;

public class GetMissNumberResponse  {


  /** 遗漏集合 */
  public List<TermData> data;

  /** 统计集合 */
  public Map<String, Map<String, Stat>> stats;

  @Override
  public String toString() {
    return "GetMissNumberResponse [" + super.toString() + ", data=" + data + ", stats=" + stats
        + "]";
  }



}
