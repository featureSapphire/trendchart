package succulent.jiuge.com.caipiaoview;

/**
 * Created by zhanglijun on 16/5/18.
 */
public class StringUtils {
  private static final String BLANK[] = new String[]{};

  /**
   * 拆分字符串.
   *
   * 如果string 为null， 返回空的数组
   * 如果regularExpression为null, 返回原字符串
   *
   * @param string 要拆分的字符串.
   * @param regularExpression 拆分使用的正则表达式字符串
   * @return 拆分后的字符串数组.
   */
  public static String[] split(String string, String regularExpression) {
    if (null == string) {
      return BLANK;
    }

    if (null == regularExpression) {
      return new String[]{string};
    }

    return string.split(regularExpression);
  }

  /**
   * 是否为null
   * @param charSequence
   * @return
   */
  public static boolean isEmpty(CharSequence charSequence){
    return charSequence==null||charSequence.length()==0;

  }
}
