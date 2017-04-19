package succulent.jiuge.com.caipiaoview;

import android.content.Context;

/**
 * Created by wdd on 2017/3/3.
 */

public class ResuorceHepler {
  public static float getDimension(Context context,int dimensionId){
    return context.getResources().getDimension(dimensionId);
  }
  public static int getColor(Context context,int colorId){
    return context.getResources().getColor(colorId);
  }
  public static String getString(Context context,int valueId){
    return context.getResources().getString(valueId);
  }
}
