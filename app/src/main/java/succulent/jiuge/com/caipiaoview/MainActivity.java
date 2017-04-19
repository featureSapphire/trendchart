package succulent.jiuge.com.caipiaoview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import succulent.jiuge.com.caipiaoview.bean.GetMissNumberResponse;
import succulent.jiuge.com.caipiaoview.bean.MissTrendKey;
import succulent.jiuge.com.caipiaoview.bean.Stat;
import succulent.jiuge.com.caipiaoview.bean.TermData;
import succulent.jiuge.com.caipiaoview.view.viewbean.Attributes;
import succulent.jiuge.com.caipiaoview.view.BastChartView;
import succulent.jiuge.com.caipiaoview.view.helper.ConvertCenterData;
import succulent.jiuge.com.caipiaoview.view.CenterTrendView;
import succulent.jiuge.com.caipiaoview.view.LeftListView;
import succulent.jiuge.com.caipiaoview.view.viewbean.LineNumberBean;
import succulent.jiuge.com.caipiaoview.view.viewbean.NumberBean;
import succulent.jiuge.com.caipiaoview.view.viewbean.StatisNumbers;

public class MainActivity extends AppCompatActivity {
  float moveX;
  private Settings settings;
  ConvertCenterData convertCenterData;
  private GetMissNumberResponse response;
  private List<TermData> termDataList;
  Map<String, Map<String, Stat>> stringMapMap;
  private EditText input;
  private String key352=MissTrendKey.StatKey.S50.getKey();
  private CenterTrendView centerTrendView;
  private LeftListView leftView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    input = (EditText) findViewById(R.id.input);
    findViewById(R.id.hidemiss).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        centerTrendView.setHideMissData(!centerTrendView.isOnlyDrawSelect());
      }
    }); findViewById(R.id.hidestat).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        centerTrendView.setHideStatData(!centerTrendView.isHideStatData());
        leftView.setHideStatData(!leftView.isHideStatData());
      }
    });
    findViewById(R.id.hideconnet).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        centerTrendView.setHideConnetLine(!centerTrendView.isHideConnetLine());
      }
    });
    findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
       // startActivity(new Intent(MainActivity.this,DemoRecycyler.class));
        Integer integer = Integer.valueOf(input.getText().toString());
        termDataList=convertCenterData.getTermsByTerm(response,integer);
        if(integer==30){
          key352=MissTrendKey.StatKey.S30.getKey();
        }else if(integer==50){
          key352=MissTrendKey.StatKey.S50.getKey();
        }else if(integer==100){
          key352=MissTrendKey.StatKey.S100.getKey();
        }else if(integer==200){
          key352=MissTrendKey.StatKey.S200.getKey();
        }
        centerTrendView.upData();
        leftView.upData();

      }
    });
    initLogSetting();

    Attributes attributesHepler = new Attributes();
    attributesHepler.init(this,null);
    Logger.i("attributesHepler"+attributesHepler);
    Logger.e(new Throwable("测试异常哈哈哈哈"),"异常名称");
    centerTrendView = (CenterTrendView) findViewById(R.id.demoView);
    convertCenterData=new ConvertCenterData(20);
    response=getMissNumberResponse(200,7,20,MissTrendKey.T01F01.redstat.name(),MissTrendKey.T01F01.redmiss.name());
    stringMapMap=response.stats;
    termDataList=convertCenterData.getTermsByTerm(response,50);
    centerTrendView.setConvertData(new CenterTrendView.ConvertData() {
      @Override
      public List<LineNumberBean> convertMissData(Attributes attributesHepler, float chartWidth) {
      //  return convertCenterData.convertMissData(termDataList,chartWidth,attributesHepler, MissTrendKey.T01F01.redmiss.name());
        return null;
      }

      @Override
      public StatisNumbers convertStatisNumbers(Attributes attributesHepler, float chartWidth) {
        return convertCenterData.convertStatisNumbers(MissTrendKey.T01F01.redstat.name()
            ,key352,stringMapMap,attributesHepler,chartWidth);
      }

      @Override
      public List<NumberBean> convertTopOrLeftListNumber(Attributes attributesHepler) {
        List<NumberBean> numberBeen = convertCenterData.convertTopListNumber(attributesHepler);

       // return numberBeen;
        return null;
      }
    });
    leftView = (LeftListView) findViewById(R.id.leftView);
    leftView.setlastPositonLine(4);
    final HorizontalScrollView horizontal = (HorizontalScrollView) findViewById(R.id.horizontal);
    leftView.setConvertLeftData(new BastChartView.ConvertDataLeftOrTop() {


      @Override
      public List<NumberBean> convertTopOrLeftListNumber(Attributes attributesHepler) {
       List<NumberBean> numberBeen= convertCenterData.getLeftListNumbers(termDataList,attributesHepler,false);
        numberBeen.add(convertCenterData.getNumberBean("出现次数",null,false,attributesHepler));
        numberBeen.add(convertCenterData.getNumberBean("平均遗漏",null,false,attributesHepler));
        numberBeen.add(convertCenterData.getNumberBean("最大遗漏",null,false,attributesHepler));
        numberBeen.add(convertCenterData.getNumberBean("最大连出",null,false,attributesHepler));
       // return numberBeen;
        return null;
      }
    });
    leftView.addOnTranslationListener(new BastChartView.OnTranslationListener() {
      @Override
      public void onTranslationChanger(float translationX, float translationY) {
        centerTrendView.moveToY(translationY);
        centerTrendView.moveToX(translationX);
      }

      @Override
      public void onDistanceChanger(float distanceX, float distanceY) {

      }
    });
    centerTrendView.addOnTranslationListener(new CenterTrendView.OnTranslationListener() {
      @Override
      public void onTranslationChanger(float translationX, float translationY) {
       // horizontal.scrollTo((int) Math.abs(Math.round(translationX)),0);
        Log.i("onScrollChange","onScrollChange"+Math.abs(translationX));
        leftView.moveToY(translationY);
        leftView.moveToX(translationX);
      }

      @Override
      public void onDistanceChanger(float distanceX, float distanceY) {
        moveX=moveX+distanceX;
       // horizontal.scrollTo(-(int) moveX,0);
      }
    });
    centerTrendView.setSizeChangeListener(leftView);
    //  Log.i("onScrollChange","onScrollChange--"+i+"--"+i1+"---"+i2+"---"+i3);

    new Thread(new Runnable() {
      @Override
      public void run() {
        ToastUtils.showToast(MainActivity.this,"在子线程谈吐司");
      }
    }).start();


  }



  private void initLogSetting() {
    // 配置tag
    settings = Logger.init("EllisonLog");
    settings.methodCount(3); // 配置Log中调用堆栈的函数行数
    settings.hideThreadInfo(); // 隐藏Log中的线程信息
    settings.methodOffset(0); // 设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息
    settings.logLevel(LogLevel.FULL);
    settings.briefMode();
    Logger.json("{'key':'values'}");
  }

  private GetMissNumberResponse getMissNumberResponse(int termCount,int winNumberCount,int lineCount,String statKey,String missKEy){
    GetMissNumberResponse getMissNumberResponse = new GetMissNumberResponse();
    getMissNumberResponse.data=new ArrayList<>();
    TermData termData=null;
    for(int i=0;i<termCount;i++){
      termData=new TermData();
      termData.winNumber=getPlaceholderData(winNumberCount,i+"");
      termData.issue=i+"";
      termData.overNumber=null;
      termData.missNumber=new HashMap<>();
      termData.missNumber.put(missKEy,i%3==0?null:getPlaceholderData(lineCount));
      getMissNumberResponse.data.add(termData);
    }
  /*  getMissNumberResponse.stats=new HashMap<>();
    HashMap<String, Stat> stringListHashMap = new HashMap<>();
    Stat stat = new Stat();
    stat.count=getPlaceholderData(lineCount,5);
    stat.avgMiss=getPlaceholderData(lineCount,6);
    stat.maxMiss=getPlaceholderData(lineCount,7);
    stat.maxSeries=getPlaceholderData(lineCount,8);
    stringListHashMap.put(MissTrendKey.StatKey.S50.getKey()  ,stat);

    Stat stat1 = new Stat();
    stat1.count=getPlaceholderData(lineCount,1);
    stat1.avgMiss=getPlaceholderData(lineCount,2);
    stat1.maxMiss=getPlaceholderData(lineCount,3);
    stat1.maxSeries=getPlaceholderData(lineCount,4);
    stringListHashMap.put(MissTrendKey.StatKey.S30.getKey()  ,stat1);


    Stat stat2 = new Stat();
    stat2.count=getPlaceholderData(lineCount,9);
    stat2.avgMiss=getPlaceholderData(lineCount,10);
    stat2.maxMiss=getPlaceholderData(lineCount,12);
    stat2.maxSeries=getPlaceholderData(lineCount,13);
    stringListHashMap.put(MissTrendKey.StatKey.S100.getKey()  ,stat2);

    Stat stat3 = new Stat();
    stat3.count=getPlaceholderData(lineCount,14);
    stat3.avgMiss=getPlaceholderData(lineCount,15);
    stat3.maxMiss=getPlaceholderData(lineCount,16);
    stat3.maxSeries=getPlaceholderData(lineCount,17);
    stringListHashMap.put(MissTrendKey.StatKey.S200.getKey()  ,null);
    getMissNumberResponse.stats.put(statKey,stringListHashMap);*/
    getMissNumberResponse.stats=getStats(lineCount,statKey);
    return getMissNumberResponse;
  }

  /**
   * 获取统计数据
   * @param lineCount
   * @param statKey
   * @return
   */
  public Map<String, Map<String, Stat>> getStats(int lineCount,String statKey){
    Map<String, Map<String, Stat>> stats=new HashMap<>();
    HashMap<String, Stat> stringListHashMap = new HashMap<>();
    Stat stat = new Stat();
    stat.count=getPlaceholderData(lineCount,5);
    stat.avgMiss=getPlaceholderData(lineCount,6);
    stat.maxMiss=getPlaceholderData(lineCount,7);
    stat.maxSeries=getPlaceholderData(lineCount,8);
    stringListHashMap.put(MissTrendKey.StatKey.S50.getKey()  ,stat);

    Stat stat1 = new Stat();
    stat1.count=getPlaceholderData(lineCount,1);
    stat1.avgMiss=getPlaceholderData(lineCount,2);
    stat1.maxMiss=getPlaceholderData(lineCount,3);
    stat1.maxSeries=getPlaceholderData(lineCount,4);
    stringListHashMap.put(MissTrendKey.StatKey.S30.getKey()  ,stat1);


    Stat stat2 = new Stat();
    stat2.count=getPlaceholderData(lineCount,9);
    stat2.avgMiss=getPlaceholderData(lineCount,10);
    stat2.maxMiss=getPlaceholderData(lineCount,12);
    stat2.maxSeries=getPlaceholderData(lineCount,13);
    stringListHashMap.put(MissTrendKey.StatKey.S100.getKey()  ,stat2);

    Stat stat3 = new Stat();
    stat3.count=getPlaceholderData(lineCount,14);
    stat3.avgMiss=getPlaceholderData(lineCount,15);
    stat3.maxMiss=getPlaceholderData(lineCount,16);
    stat3.maxSeries=getPlaceholderData(lineCount,17);
    stringListHashMap.put(MissTrendKey.StatKey.S200.getKey()  ,null);
    stats.put(statKey,stringListHashMap);
    return stats;
  }


  /**
   *
   * @return
   */
  private List<Integer> getPlaceholderData(int count){
    ArrayList<Integer> integers = new ArrayList<>();
    for(int i=0;i<count;i++){
      integers.add(i%6==0?0:2);
    }
    return integers;
  }

  private List<Integer> getPlaceholderData(int count,int content){
    ArrayList<Integer> integers = new ArrayList<>();
    for(int i=0;i<count;i++){
      integers.add(content);
    }
    return integers;
  }

  private List<String> getPlaceholderData(int count,String data){
    ArrayList<String> integers = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      integers.add(i+data);
    }
    return integers;
  }

}
