package succulent.jiuge.com.caipiaoview.bean;

public class MissTrendKey {

  /**
   * 双色球、大乐透
   *
   * @author ZHANGGQ
   *
   */
  public enum T01F01 {

    redmiss("红球遗漏"),

    bluemiss("蓝球遗漏"),

    redstat("红球统计"),

    bluestat("蓝球统计");

    private String desc;

    private T01F01(String desc) {
      this.desc = desc;
    }

    public String getDesc() {
      return desc;
    }

    public static String getDesc(String status) {
      for (T01F01 iss : T01F01.values()) {
        if (iss.name().equals(status)) {
          return iss.getDesc();
        }
      }
      return "";
    }
  }

  /**
   * 七星彩
   *
   */
  public enum T02 {

    num1("第一个球"),

    num2("第二个球"),

    num3("第三个球"),

    num4("第四个球"),

    num5("第五个球"),

    num6("第六个球"),

    num7("第七个球");

    private String desc;

    private T02(String desc) {
      this.desc = desc;
    }

    public String getDesc() {
      return desc;
    }

    public static String getDesc(String status) {
      for (T02 iss : T02.values()) {
        if (iss.name().equals(status)) {
          return iss.getDesc();
        }
      }
      return "";
    }
  }

  /**
   * 排列三、福彩3D
   *
   */
  public enum T03F02 {

    zhixuanfushi("直选复式"),

    zuliufushi("组六复式"),

    zusanfushi("组三复式");

    private String desc;

    private T03F02(String desc) {
      this.desc = desc;
    }

    public String getDesc() {
      return desc;
    }

    public static String getDesc(String status) {
      for (T03F02 iss : T03F02.values()) {
        if (iss.name().equals(status)) {
          return iss.getDesc();
        }
      }
      return "";
    }
  }

  /**
   * 排列五
   *
   */
  public enum T04 {

    zhixuanfushi("直选复式");

    private String desc;

    private T04(String desc) {
      this.desc = desc;
    }

    public String getDesc() {
      return desc;
    }

    public static String getDesc(String status) {
      for (T04 iss : T04.values()) {
        if (iss.name().equals(status)) {
          return iss.getDesc();
        }
      }
      return "";
    }
  }

  /**
   * 11选5
   *
   * @author ZHANGGQ
   *
   */
  public enum T05 {

    qian1_danshi("前一单式"),

    qian1_xingtai("前一形态"),

    qian2_zhixuan("前二直选"),

    qian2_zuxuan("前二组选"),

    qian3_zhixuan("前三直选"),

    qian3_zuxuan("前三组选"),

    renxuan("任选");

    private String desc;

    private T05(String desc) {
      this.desc = desc;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    public static String getName(String desc) {
   /*   return Stream.of(T05.values())
          .filter(e -> e.name().equals(desc))
          .findFirst()
          .orElseThrow(() -> new RuntimeException(String.format("非法键值:%s", desc)))
          .getDesc();*/
      return null;
    }
  }

  /**
   * 七乐彩
   *
   */
  public enum F03 {

    general("基本");

    private String desc;

    private F03(String desc) {
      this.desc = desc;
    }

    public String getDesc() {
      return desc;
    }

    public static String getDesc(String status) {
      for (F03 iss : F03.values()) {
        if (iss.name().equals(status)) {
          return iss.getDesc();
        }
      }
      return "";
    }
  }

  /**
   * 快三
   *
   */
  public enum F04 {

    erbutong("二不同"),

    ertong("二同"),

    shuzi("数字"),

    sum("和值"),

    xingtai("形态");

    private String desc;

    private F04(String desc) {
      this.desc = desc;
    }

    public String getDesc() {
      return desc;
    }

    public static String getDesc(String status) {
      for (F04 iss : F04.values()) {
        if (iss.name().equals(status)) {
          return iss.getDesc();
        }
      }
      return "";
    }
  }

  /**
   * 统计
   *
   * @author ZHANGGQ
   *
   */
  public enum StatKey {
    S30("30", "近30期"),

    S50("50", "近50期"),

    S100("100", "近100期"),

    S200("200", "近200期");

    private String key;

    private String desc;

    private StatKey(String key, String desc) {
      this.key = key;
      this.desc = desc;
    }

    public String getKey() {
      return key;
    }

    public String getDesc() {
      return desc;
    }

    public static String getDesc(String name) {
      for (StatKey iss : StatKey.values()) {
        if (iss.name().equals(name)) {
          return iss.getDesc();
        }
      }
      return "";
    }
  }
}
