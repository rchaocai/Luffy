# Luffy
Android字节码插件，编译期间动态修改代码


### 1、更新日志

###### 2018.12.07更新
参考神策的全埋点日志SDK,进行功能重构扩展完善,以方便正式应用到线上应用

1、针对日志采集的全埋点对各个常见十多种控件进行埋点监听及处理(AutoTrackHelper类)
- onFragmentViewCreated
- trackFragmentResume
- trackFragmentSetUserVisibleHint
- trackOnHiddenChanged
- trackFragmentAppViewScreen
- trackExpandableListViewOnGroupClick
- trackExpandableListViewOnChildClick
- trackListView
- trackTabHost
- trackTabLayoutSelected
- trackMenuItem
- trackRadioGroup
- trackDialog
- trackDrawerOpened
- trackDrawerClosed
- trackViewOnClick
- trackViewOnClick

2、可以在build.gradle进行多个自定义插桩配置

3、针对埋点控件及自定义配置功能的logapp测试应用


###### 2018.03.28更新
插件扩展，新增自动埋点实战功能
2018.03.28更新 插件扩展，新增自动埋点实战功能
- View的onClick(View v)方法
- Fragment的onResume()方法
- Fragment的onPause()方法
- Fragment的setUserVisibleHint(boolean b)方法
- Fragment的onHiddenChanged(boolean b)方法
- 在app的module中手动设置的监听条件：指定方法或注解方法

### 2、使用步骤

要是使用演示的话，因为还没上传到jcenter库，所以只能本地仓库打包插件，记得要先把依赖都注释掉，插件打包完成后再启用，不然会编译不过去的。

本地打包及使用步骤:
- 2.1、AndroidStudio右侧Gradle->:plugin->upload->uploadArchives,打包成功会在项目目录snapshotRepo中
- 2.2、根build.gradle添加插件
 ```
 dependencies {
     classpath 'oms.mmc:autotrack-gradle-plugin:1.0.0-SNAPSHOT'
 }
 ```
- 3、app的build.gradle中进行配置

 ```
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


 - 2.4、```Clean Project```打包应用

 在logapp->build->intermediates->transforms->AutoTrack->debug中可查看插桩后的类文件:![插桩类文件](https://github.com/JieYuShi/Luffy/blob/master/img/after_autotrack.png)

 具体编译的字节码可查看编译日志:![编译日志](https://github.com/JieYuShi/Luffy/blob/master/img/bulid_log.png)

 具体采集后的日志及处理可看应用日志:![应用日志](https://github.com/JieYuShi/Luffy/blob/master/img/app_log1.png)
 ![应用日志规范后效果](https://github.com/JieYuShi/Luffy/blob/master/img/app_log2.png)


