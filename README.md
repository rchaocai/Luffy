# Luffy
Android字节码插件，编译期间动态修改代码
###5.1、插件配置
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
                    ['ClassName': null, 'InterfaceName':null,
                     'MethodName':null, 'MethodDes':null]
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
                        }

                        @Override
                        protected void onMethodExit(int opcode) {
                            super.onMethodExit(opcode)
                            //使用注解找对应方法的时候得加这个判断
                        }

                        /**
                         * 需要通过注解的方式加字节码才会重写这个方法来进行条件过滤
                         */
                        @Override
                        AnnotationVisitor visitAnnotation(String des, boolean visible) {
                            return super.visitAnnotation(des, visible)
                        }
                    }
                    return adapter
            }
    ]
}
```
要是使用演示的话，因为还没上传到jcenter库，所以只能本地仓库打包插件，记得要先把依赖都注释掉，插件打包完成后再启用，不然会编译不过去的。
```xiaoqingwa{}```里头的配置信息先不用管，等会会讲到，主要是为了能够不修改插件进行动态更换插桩的方法。



