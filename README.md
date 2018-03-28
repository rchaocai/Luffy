# Luffy
Android字节码插件，编译期间动态修改代码
先打包一下插件到本地仓库进行引用，在项目的根build.gradle加入插件的依赖

```
dependencies {
    classpath 'com.xixi.plugin:plugin:1.0.1-SNAPSHOT'
}

```
在app的build.gradle中

```
apply plugin: 'apk.move.plugin'

xiaoqingwa {
    name = "小傻逼"
    isDebug = true
    //具体配置
    matchData = [
            //是否使用注解来找对应方法
            'isAnotation'  : false,
            //方法的匹配，可以通过类名或者实现的接口名匹配
            'ClassFilter'  : [
                    ['ClassName' : 'com/xishuang/plugintest.Counter2', 'InterfaceName': '',
                     'MethodName': 'test2', 'MethodDes': '()V']
            ],
            //插入的字节码，方法的执行顺序visitAnnotation->onMethodEnter->onMethodExit
            'MethodVisitor': {
                MethodVisitor methodVisitor, int access, String name, String desc ->
                    MethodVisitor adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                        boolean isAnnotation = false

                        @Override
                        protected void onMethodEnter() {
                            super.onMethodEnter()
                            //使用注解找对应方法的时候得加这个判断
//                            if (!isAnnotation){
//                                return
//                            }
                        }

                        @Override
                        protected void onMethodExit(int opcode) {
                            super.onMethodExit(opcode)
                            //使用注解找对应方法的时候得加这个判断
//                            if (!isAnnotation) {
//                                return
//                            }
                            // INVOKESTATIC
                            methodVisitor.visitMethodInsn(184, "com/xishuang/plugintest/AutoHelper", "onClick", "()V", false)
                        }

                        /**
                         * 需要通过注解的方式加字节码才会重写这个方法来进行条件过滤
                         */
                        @Override
                        AnnotationVisitor visitAnnotation(String des, boolean visible) {
//                            if (des == 'Lcom/xishuang/annotation/AutoCount;') {
//                                println "注解匹配：" + des
//                                isAnnotation = true
//                            }
                            return super.visitAnnotation(des, visible)
                        }
                    }
                    return adapter
            }
    ]
}
```
2018.03.28 插件扩展，新增自动埋点实战功能
针对以下的方法进行埋点监听，并实现了View的唯一区别链
- 1、View的onClick(View v)方法
- 2、Fragment的onResume()方法
- 3、Fragment的onPause()方法
- 4、Fragment的setUserVisibleHint(boolean b)方法
- 5、Fragment的onHiddenChanged(boolean b)方法
- 6、在app的module中手动设置的监听条件：指定方法或注解方法

插件的增加自动埋点处理类主要是ChoiceUtil

App中对监听的方法处理的类是AutoHelper.java
```
/**
 * Author:xishuang
 * Date:2018.03.01
 * Des:自动埋点帮助类
 */
public class AutoHelper {
    private static final String TAG = AutoHelper.class.getSimpleName();
    private static Context context = AutoApplication.getInstance().getApplicationContext();


    /**
     * 实现onClick点击事件的自动注入处理
     */
    public static void onClick(View view) {
        String path = AutoUtil.getPath(context, view);
        String activityName = AutoUtil.getActivityName(view);
        path = activityName + ":onClick:" + path;
        Log.d(TAG, path);
    }

    /**
     * 测试方法，测试注解方法以及在module中设置的方法的自动注入处理
     */
    public static void onClick() {
        Log.d(TAG, "onClick()");
    }

    public static void onFragmentResume(Fragment fragment) {
        Log.d(TAG, "onFragmentResume" + fragment.getClass().getSimpleName());
    }

    public static void onFragmentPause(Fragment fragment) {
        Log.d(TAG, "onFragmentPause"  + fragment.getClass().getSimpleName());
    }

    public static void setFragmentUserVisibleHint(Fragment fragment, boolean isVisibleToUser) {
        Log.d(TAG, "setFragmentUserVisibleHint->" + isVisibleToUser + "->" + fragment.getClass().getSimpleName());
    }

    public static void onFragmentHiddenChanged(Fragment fragment, boolean hidden) {
        Log.d(TAG, "onFragmentHiddenChanged->" + hidden + "->" + fragment.getClass().getSimpleName());
    }
```

 ![image](https://github.com/JieYuShi/Luffy/blob/master/img.png)

要是使用演示的话，因为还没上传到jcenter库，所以只能本地仓库打包插件，记得要先把依赖都注释掉，插件打包完成后再启用，不然会编译不过去的。




