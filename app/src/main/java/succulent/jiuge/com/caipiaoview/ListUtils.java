package succulent.jiuge.com.caipiaoview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by 远 on 2015/1/8.
 */
public class ListUtils {

  /**
   * 下拉菜单中用来翻转Terms并使size保持为10
   *
   * @param list 数据源
   * @param <T>  泛型 一般情况下为Term
   * @return list
   */
  public static <T> List<T> reverse(List<T> list) {
    List<T> temp = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      temp.add(list.get(i));
    }
    Collections.reverse(temp);
    return temp;
  }

  /**
   * 是否有数据
   *
   * @param collection
   * @return
   */
  public static boolean isHaveData(Collection collection) {
    return collection != null && !collection.isEmpty();
  }


}
