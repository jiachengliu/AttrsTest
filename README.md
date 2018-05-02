# AttrsTest
*attr、style*在Android开发中是必不可少的基础知识，无论是自己开发还是阅读别人的源代码都会遇到。如果这个掌握不好，很难学到别人代码的精华，故想整理一下相关知识点。
>- **attr**，我的理解是“一些动态属性”，比如：name，name本身可能为不确定的值，所以需要根据不同的场景动态赋值。
>- **style**，是把一组属性及其对应的值封装在一起，可以简化代码，是代码简洁、易读。>

接下来通过一个完整的实例进行讲解，将通过自定义View实现如图一：
![实例.jpg](https://upload-images.jianshu.io/upload_images/5624790-646694c4089bdcd6.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
接下来将通过代码讲解说明实现过程。首先[新建一个项目](https://www.jianshu.com/p/a7540f6a7c69)，
先在values文件夹下面新建一个attrs文件，值得注意的是，format多个值的用法，可以百度一下，也很好理解。写入如下代码：
~~~
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="attrTest">
        <attr name="name" format="string"/><!--姓名-->
        <attr name="age" format="integer"/><!--年龄-->
        <attr name="score" format="float"/><!--分数-->
        <attr name="sex" >                 <!--性别-->
            <enum name="man" value="1"/>
            <enum name="woman" value="2"/>
        </attr>
    </declare-styleable>
</resources>
~~~
接着新建一个styles文件，写入代码如下：
~~~
<resources>
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Base.Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
    <style name="customView">
        <item name="name">@string/user_name</item>
        <item name="age">10</item>
        <item name="sex">man</item>
        <item name="score">90.5</item>
    </style>
</resources>
~~~
新建一个类继承View，名为：CustomView，注意构造方法的写法，自定义View时构造方法必须要写。具体代码如下：
~~~
 /**
     * 绘制的文字
     */
    private String mText;
    private String mText1;
    /**
     * 文字的颜色
     */
    private int mTextColor;
    /**
     * 文字的大小
     */
    private int mTextSize;
    /**
     * 绘制控制文本的范围
     */
    private Rect mBound;

    private Paint mPaint;

    public CustomView(Context context) {
        this(context,null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.attrTest,defStyleAttr,0);//最后一个参数是代码默认res。
        float score = typedArray.getFloat(R.styleable.attrTest_score,0);
        int age = typedArray.getInteger(R.styleable.attrTest_age,0);
        String name = typedArray.getString(R.styleable.attrTest_name);
        int sex = typedArray.getInt(R.styleable.attrTest_sex,1);//默认1表示男
        String sexStr = "男";
        if (sex==2) sexStr = "女";
        mText = "姓名:"+name+",性别："+sexStr;
        mText1 = "年龄:"+age+",分数:"+score;
        mTextColor = Color.BLACK;
        mTextSize = 80;
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        //获得绘制文本的宽和高
        mBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 参数解释：text:待绘制的文字，x:绘制的起点x轴坐标，y:绘制的起点的y轴坐标，Paint:绘画的笔（其有对应的对个属性）。
         */
        canvas.drawText(mText, getWidth() / 2 - mBound.width() / 2, getHeight()/3 + mBound.height() / 2, mPaint);
        canvas.drawText(mText1, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }
~~~
接下来是activity_main，感兴趣的同学可以搜索ConstraintLayout的用法，比RelativeLayout有很多优点，代码如下：
~~~
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:custom="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.android.attrstest.MainActivity">

    <TextView
        android:id="@+id/text_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="@android:color/black"
        android:text="@string/user_title" />


    <com.android.attrstest.view.CustomView
        android:id="@+id/text_des"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        style="@style/customView"
        custom:layout_constraintTop_toBottomOf="@+id/text_title"
        android:layout_marginTop="10dp"
        />

</android.support.constraint.ConstraintLayout>
~~~
MainActivity代码较为简单，就是调用一下setContentView()方法,
~~~
    @BindView(R.id.text_des)
    CustomView text_des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }
~~~
此实例较为简单，只是简单的用法，只有基础的知识打牢，才有助于进一步的提升。
可以点击**[代码地址](https://github.com/jiachengliu/AttrsTest)**，进行clone。
#**以下为插叙**
>在我写这个实例的时候，我使用的'com.android.tools.build:gradle:3.0.1'，当我使用ButterKnife 8.6.0时，报如下错误：
~~~
Error:Unable to find method 'com.android.build.gradle.api.BaseVariant.getOutputs()Ljava/util/List;'.
Possible causes for this unexpected error include:<ul><li>Gradle's dependency cache may be corrupt (this sometimes occurs after a network connection timeout.)
<a href="syncProject">Re-download dependencies and sync project (requires network)</a></li><li>The state of a Gradle build process (daemon) may be corrupt. Stopping all Gradle daemons may solve this problem.
<a href="stopGradleDaemons">Stop Gradle build processes (requires restart)</a></li><li>Your project may be using a third-party plugin which is not compatible with the other plugins in the project or the version of Gradle requested by the project.</li></ul>In the case of corrupt Gradle processes, you can also try closing the IDE and then killing all Java processes.
~~~
>多次尝试都还是报错，无奈去官网查看，终于找到[解决方案](https://github.com/JakeWharton/butterknife/issues/963)，说是butterknife8.6.0暂时不支持3.0.0，要使用butterknife8.4.0才行。

解决方法如下：
~~~
apply plugin: 'com.android.application'
apply plugin: 'com.jakewharton.butterknife'

android {
    compileSdkVersion 26
    buildToolsVersion "26.0.2"

    defaultConfig {
        applicationId "com.android.attrstest"
        minSdkVersion 15
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:26.0.2'
    implementation 'com.android.support.constraint:constraint-layout:1.1.0'
    implementation 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'

    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.1'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.1'
}
~~~
~~~
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.1'
        classpath 'com.jakewharton:butterknife-gradle-plugin:8.4.0'


        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
~~~

