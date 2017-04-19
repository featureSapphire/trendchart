package succulent.jiuge.com.caipiaoview;

import android.util.Log;

import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

import org.junit.Before;
import org.junit.Test;

import succulent.jiuge.com.caipiaoview.bean.MissTrendKey;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Before
  public void step(){
    Settings settings = Logger.init("EllisonLog");
    settings.methodCount(3); // 配置Log中调用堆栈的函数行数
    settings.hideThreadInfo(); // 隐藏Log中的线程信息
    settings.methodOffset(0); // 设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息
    settings.logLevel(LogLevel.FULL);

    settings.logAdapter(new LogAdapter() {
      @Override
      public void d(String tag, String message) {

      }

      @Override
      public void e(String tag, String message) {

      }

      @Override
      public void w(String tag, String message) {

      }

      @Override
      public void i(String tag, String message) {
        System.out.println(tag+"----"+message);
      }

      @Override
      public void v(String tag, String message) {

      }

      @Override
      public void wtf(String tag, String message) {

      }
    });




  }
  @Test
  public void addition_isCorrect() throws Exception {
    String name = MissTrendKey.T03F02.zhixuanfushi.name();
    Logger.i("测试log"+name);
  }
}