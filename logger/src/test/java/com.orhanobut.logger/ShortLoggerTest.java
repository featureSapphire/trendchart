package com.orhanobut.logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.orhanobut.logger.Logger.ASSERT;
import static com.orhanobut.logger.Logger.DEBUG;
import static com.orhanobut.logger.Logger.ERROR;
import static com.orhanobut.logger.Logger.INFO;
import static com.orhanobut.logger.Logger.VERBOSE;
import static com.orhanobut.logger.Logger.WARN;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ShortLoggerTest {

  private static final String DEFAULT_TAG = "PRETTYLOGGER";

  String threadName;

  @Before public void setup() {
    threadName = Thread.currentThread().getName();

    Logger.init().briefMode();
  }

  @After public void tearDown() {
    Logger.resetSettings();
  }

  @Test public void testLog() {
    Logger.log(DEBUG, null, "message", null);
    assertLog(DEBUG).hasMessageWithShortSettings("message");

    Logger.log(DEBUG, "tag", "message", null);
    assertLog(DEFAULT_TAG + "-tag", DEBUG).hasMessageWithShortSettings("message");
  }

  @Ignore
  @Test public void testLogThrowable() {
    Throwable throwable = new Throwable("throwable");
    String stackString = "message : " + Helper.getStackTraceString(throwable);
    String[] stackItems = stackString.split("\\n");
    Logger.log(DEBUG, null, "message", throwable);

    assertLog(DEBUG).hasMessageWithShortSettings(stackItems);
  }

  @Test public void debugLog() {
    Logger.d("message");

    assertLog(DEBUG).hasMessageWithShortSettings("message");
  }

  @Test public void verboseLog() {
    Logger.v("message");

    assertLog(VERBOSE).hasMessageWithShortSettings("message");
  }

  @Test public void warningLog() {
    Logger.w("message");

    assertLog(WARN).hasMessageWithShortSettings("message");
  }

  @Test public void errorLog() {
    Logger.e("message");

    assertLog(ERROR).hasMessageWithShortSettings("message");
  }

  @Ignore("Through Terminal somehow not working, but on Studio it works, needs investigation")
  @Test public void errorLogWithThrowable() {
    Throwable throwable = new Throwable("throwable");
    Logger.e(throwable, "message");

    String stackString = "message : " + Helper.getStackTraceString(throwable);
    String[] stackItems = stackString.split("\\n");

    assertLog(ERROR).hasMessageWithShortSettings(stackItems);
  }

  @Ignore("Through Terminal somehow not working, but on Studio it works, needs investigation")
  @Test public void errorLogWithThrowableWithoutMessage() {
    Throwable throwable = new Throwable("throwable");
    Logger.e(throwable, null);

    String stackString = Helper.getStackTraceString(throwable);
    String[] stackItems = stackString.split("\\n");

    assertLog(ERROR).hasMessageWithShortSettings(stackItems);
  }

  @Ignore("Through Terminal somehow not working, but on Studio it works, needs investigation")
  @Test public void errorLogNoThrowableNoMessage() {
    Logger.e(null, null);

    assertLog(ERROR).hasMessageWithShortSettings("No message/exception is set");
  }

  @Test public void infoLog() {
    Logger.i("message");

    assertLog(INFO).hasMessageWithShortSettings("message");
  }

  @Test public void wtfLog() {
    Logger.wtf("message");

    assertLog(ASSERT).hasMessageWithShortSettings("message");
  }

  @Test public void logArray() {
    double[][] doubles = {{1.2, 1.6, 1.7, 30, 33},
        {1.2, 1.6, 1.7, 30, 33},
        {1.2, 1.6, 1.7, 30, 33},
        {1.2, 1.6, 1.7, 30, 33}};

    Logger.d(doubles);

    String message = Arrays.deepToString(doubles);
    assertLog(DEBUG).hasMessageWithShortSettings(message);
  }

  @Test public void logList() {
    List<String> list = Arrays.asList("foo", "bar");
    Logger.d(list);

    assertLog(DEBUG).hasMessageWithShortSettings(list.toString());
  }

  @Test public void logMap() {
    Map<String, String> map = new HashMap<>();
    map.put("key", "value");
    map.put("key2", "value2");

    Logger.d(map);

    assertLog(DEBUG).hasMessageWithShortSettings(map.toString());
  }

  @Test public void logSet() {
    Set<String> set = new HashSet<>();
    set.add("key");
    set.add("key1");

    Logger.d(set);

    assertLog(DEBUG).hasMessageWithShortSettings(set.toString());
  }

  @Test public void jsonLObjectLog() {
    String[] messages = new String[]{"{", "  \"key\": 3", "}"};

    Logger.json("  {\"key\":3}");

    assertLog(DEBUG).hasMessageWithShortSettings(messages);
  }

  @Test public void jsonArrayLog() {
    String[] messages = new String[]{
        "[",
        "  {",
        "    \"key\": 3",
        "  }",
        "]"
    };

    Logger.json("[{\"key\":3}]");

    assertLog(DEBUG).hasMessageWithShortSettings(messages);
  }

  @Test public void testInvalidJsonLog() {
    Logger.json("no json");
    assertLog(ERROR).hasMessageWithShortSettings("Invalid Json");

    Logger.json("{ missing end");
    assertLog(ERROR).hasMessageWithShortSettings("Invalid Json");
  }

  @Test public void jsonLogEmptyOrNull() {
    Logger.json(null);
    assertLog(DEBUG).hasMessageWithShortSettings("Empty/Null json content");

    Logger.json("");
    assertLog(DEBUG).hasMessageWithShortSettings("Empty/Null json content");
  }

  @Test public void xmlLog() {
    String[] messages = new String[]{
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
        "<xml>Test</xml>"
    };

    Logger.xml("<xml>Test</xml>");

    assertLog(DEBUG).hasMessageWithShortSettings(messages);
  }

  @Test public void invalidXmlLog() {
    Logger.xml("xml>Test</xml>");

    assertLog(ERROR).hasMessageWithShortSettings("Invalid xml");
  }

  @Test public void xmlLogNullOrEmpty() {
    Logger.xml(null);
    assertLog(DEBUG).hasMessageWithShortSettings("Empty/Null xml content");

    Logger.xml("");
    assertLog(DEBUG).hasMessageWithShortSettings("Empty/Null xml content");
  }

  @Test public void logWithoutThread() {
    Logger.init().hideThreadInfo().briefMode();
    Logger.i("message");
    assertLog(INFO)
        .skip()
        .skip()
        .hasMessage("message")
        .hasNoMoreMessages();
  }

  @Test public void logWithCustomTag() {
    Logger.init("CustomTag").briefMode();
    Logger.i("message");
    assertLog("CustomTag", INFO)
        .hasShortThread(threadName)
        .skip()
        .skip()
        .hasMessage("message")
        .hasNoMoreMessages();
  }

  @Test public void logWithOneMethodInfo() {
    Logger.init().methodCount(1).briefMode();
    Logger.i("message");
    assertLog(INFO)
        .hasShortThread(threadName)
        .skip()
        .hasMessage("message")
        .hasNoMoreMessages();
  }

  @Test public void logWithNoMethodInfo() {
    Logger.init().methodCount(0).briefMode();
    Logger.i("message");

    assertLog(INFO)
        .hasShortThread(threadName)
        .hasMessage("message")
        // .hasBottomBorder()
        .hasNoMoreMessages();
  }

  @Test public void logWithNoMethodInfoAndNoThreadInfo() {
    Logger.init().methodCount(0).hideThreadInfo().briefMode();
    Logger.i("message");

    assertLog(INFO)
        .hasMessage("message")
        .hasNoMoreMessages();
  }

  @Test public void logWithOnlyOnceCustomTag() {
    Logger.init().hideThreadInfo().methodCount(0).briefMode();
    Logger.t("CustomTag").i("message");
    Logger.i("message");

    assertLog("PRETTYLOGGER-CustomTag", INFO)
        .hasMessage("message")
        .defaultTag()
        .hasMessage("message")
        .hasNoMoreMessages();
  }

  @Test public void logWithOnlyOnceMethodInfo() {
    Logger.init().hideThreadInfo().methodCount(0).briefMode();
    Logger.t(1).i("message");
    Logger.i("message");

    assertLog(INFO)
        .skip()
        .hasMessage("message")
        .hasMessage("message")
        .hasNoMoreMessages();
  }

  @Test public void logWithOnlyOnceMethodInfoAndCustomTag() {
    Logger.init().hideThreadInfo().methodCount(0).briefMode();
    Logger.t("CustomTag", 1).i("message");
    Logger.i("message");

    assertLog("PRETTYLOGGER-CustomTag", INFO)
        .skip()
        .hasMessage("message")
        .defaultTag()
        .hasMessage("message")
        .hasNoMoreMessages();
  }

  @Test public void logNone() {
    Logger.init().logLevel(LogLevel.NONE).briefMode();
    Logger.i("message");

    assertLog(INFO)
        .hasNoMoreMessages();
  }

  @Test public void useDefaultSettingsIfInitNotCalled() {
    Logger.i("message");
    assertLog(INFO)
        .hasShortThread(threadName)
        .skip()
        .skip()
        .hasMessage("message")
        .hasNoMoreMessages();
  }

  private void print() {
    for (ShadowLog.LogItem logItem : ShadowLog.getLogs()) {
      System.out.print(logItem.msg + "\n");
    }
  }

  private static LogAssert assertLog(int priority) {
    return assertLog(null, priority);
  }

  private static LogAssert assertLog(String tag, int priority) {
    return new LogAssert(ShadowLog.getLogs(), tag, priority);
  }

}
