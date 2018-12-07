# Luffy
Android字节码插件，编译期间动态修改代码



2018.12.07 参考神策的全埋点日志SDK,进行重构,应用到线上应用
主要修改:
1、针对日志采集的全埋点对各个常见十多种控件进行埋点监听及处理(AutoTrackHelper类)
-onFragmentViewCreated
-trackFragmentResume
-trackFragmentSetUserVisibleHint
-trackOnHiddenChanged
-trackFragmentAppViewScreen
-trackExpandableListViewOnGroupClick
-trackExpandableListViewOnChildClick
-trackListView
-trackTabHost
-trackTabLayoutSelected
-trackMenuItem
-trackRadioGroup
-trackDialog
-trackDrawerOpened
-trackDrawerClosed
-trackViewOnClick
-trackViewOnClick

2、可以在build.gradle进行多个自定义插桩配置
3、针对埋点控件及自定义配置功能的logapp测试应用




2018.03.28 插件扩展，新增自动埋点实战功能
针对以下的方法进行埋点监听，并实现了View的唯一区别链
- 1、View的onClick(View v)方法
- 2、Fragment的onResume()方法
- 3、Fragment的onPause()方法
- 4、Fragment的setUserVisibleHint(boolean b)方法
- 5、Fragment的onHiddenChanged(boolean b)方法
- 6、在app的module中手动设置的监听条件：指定方法或注解方法




要是使用演示的话，因为还没上传到jcenter库，所以只能本地仓库打包插件，记得要先把依赖都注释掉，插件打包完成后再启用，不然会编译不过去的。
本地打包及使用步骤:
 1、AndroidStudio右侧Gradle->:plugin->upload->uploadArchives,打包成功会在项目目录snapshotRepo中
 2、根build.gradle添加插件
 ```
 dependencies {
     classpath 'oms.mmc:autotrack-gradle-plugin:1.0.0-SNAPSHOT'
 }

 ```
 3、app的build.gradle中进行配置
 apply plugin: 'oms.mmc.autotrack'

 xiaoqingwa {
     // 是否打印日志
     isDebug = true
     // 是否打开SDK的日志全埋点采集
     isOpenLogTrack = true
     // 支持自定义配置,可选
     matchData = [[
                          //方法的匹配，可以通过类名或者实现的接口名匹配
                          'ClassName'    : 'com.mmc.lamandys.liba_datapick.Counter2',
                          'InterfaceName': '',
                          'MethodName'   : 'test2',
                          'MethodDes'    : '(Landroid/view/View;)V',
                          'isAnnotation' : true,
                          //插入的字节码，方法的执行顺序visitAnnotation->onMethodEnter->onMethodExit
                          'MethodVisitor': {
                              MethodVisitor methodVisitor, int access, String name, String desc ->
                                  MethodVisitor adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                                      boolean isAnnotation = false

                                      @Override
                                      protected void onMethodExit(int opcode) {
                                          super.onMethodExit(opcode)
                                          //使用注解找对应方法的时候得加这个判断
                                          if (!isAnnotation) {
                                              return
                                          }
                                          // INVOKESTATIC
                                          methodVisitor.visitMethodInsn(184, "com/mmc/lamandys/liba_datapick/AutoHelper", "onClick2", "()V", false)
                                      }

                                      /**
                                       * 需要通过注解的方式加字节码才会重写这个方法来进行条件过滤
                                       */
                                      @Override
                                      AnnotationVisitor visitAnnotation(String des, boolean visible) {
                                          if (des == 'Lcom/xishuang/annotation/AutoCount;') {
                                              println "注解匹配：" + des
                                              isAnnotation = true
                                          }
                                          return super.visitAnnotation(des, visible)
                                      }
                                  }
                                  return adapter
                          }
                  ],
                  [
                          //方法的匹配，可以通过类名或者实现的接口名匹配
                          'ClassName'    : 'com.mmc.lamandys.liba_datapick.Counter',
                          'InterfaceName': '',
                          'MethodName'   : 'test',
                          'MethodDes'    : '()V',
                          'isAnnotation' : false,
                          //插入的字节码，方法的执行顺序visitAnnotation->onMethodEnter->onMethodExit
                          'MethodVisitor': {
                              MethodVisitor methodVisitor, int access, String name, String desc ->
                                  MethodVisitor adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {

                                      @Override
                                      protected void onMethodEnter() {
                                          super.onMethodEnter()
                                      }

                                      @Override
                                      protected void onMethodExit(int opcode) {
                                          super.onMethodExit(opcode)
                                          // INVOKESTATIC
                                          methodVisitor.visitMethodInsn(INVOKESTATIC, "com/mmc/lamandys/liba_datapick/AutoHelper", "onClick3", "()V", false)
                                      }
                                  }
                                  return adapter
                          }
                  ]
     ]
 }
 ```




