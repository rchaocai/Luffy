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

xiaoqingwa{
    name = "小傻逼"
    isDebug = true
    //具体配置
    matchData = [
            //是否使用注解来找对应方法
            'isAnotation': false,
            //方法的匹配，可以通过类名或者实现的接口名匹配
            'ClassFilter': [
                    ['ClassName': 'com.xishuang.plugintest.MainActivity', 'InterfaceName': 'android/view/View$OnClickListener',
                     'MethodName':'onClick', 'MethodDes':'(Landroid/view/View;)V']
            ],
            //插入的字节码，方法的执行顺序visitAnnotation->onMethodEnter->onMethodExit
            'MethodVisitor':{
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

                            methodVisitor.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/MainActivity", "notifyInsert", "()V", false)
                            methodVisitor.visitLdcInsn(name)
                            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
                            methodVisitor.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/TimeCache", "setStartTime", "(Ljava/lang/String;J)V", false)
                        }

                        @Override
                        protected void onMethodExit(int opcode) {
                            super.onMethodExit(opcode)
                            //使用注解找对应方法的时候得加这个判断
//                            if (!isAnnotation){
//                                return
//                            }

                            methodVisitor.visitLdcInsn(name)
                            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
                            methodVisitor.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/TimeCache", "setEndTime", "(Ljava/lang/String;J)V", false)
                            methodVisitor.visitLdcInsn("耗时")
                            methodVisitor.visitLdcInsn(name)
                            methodVisitor.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/TimeCache", "getCostTime", "(Ljava/lang/String;)Ljava/lang/String;", false)
                            methodVisitor.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false)
                        }

                        /**
                         * 需要通过注解的方式加字节码才会重写这个方法来进行条件过滤
                         */
                        @Override
                        AnnotationVisitor visitAnnotation(String des, boolean visible) {
//                            if (des.equals("Lcom/xishuang/annotation/AutoCount;")) {
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
要是使用演示的话，因为还没上传到jcenter库，所以只能本地仓库打包插件，记得要先把依赖都注释掉，插件打包完成后再启用，不然会编译不过去的。




