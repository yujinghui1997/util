#注意事项
~~~~
1.使用时请先排除spring的自动配置类
2.扫描com.yjh包
~~~~
~~~~
@SpringBootApplication(scanBasePackages = {"com.yjh"},exclude = {
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class
})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
~~~~
# util开发常用工具
~~~~
1，com.yjh.annotation 注解包
2，com.yjh.core.MyHttpStatus Http 响应码
3，com.yjh.core.ResData Http 响应数据格式
4，com.yjh.util.MyBeanUtil 拷贝属性
5，com.yjh.util.MyFileUtil 文件操作
6，com.yjh.util.MyHttpUtil Http请求操作
7，com.yjh.util.MyJwtUtil jwt 认证
8，com.yjh.util.MyLdtUtil 日期时间操作
9，com.yjh.util.MyLdUtil 日期操作
10，com.yjh.util.MyLtUtil 时间操作
11，com.yjh.util.MyRedisUtil redis操作
12，com.yjh.util.MyStrUtil 字符串操作
~~~~
# application.properties 配置文件详解
~~~~
#是否 连接mysql 数据库   默认tue  false不连接
com.yjh.mysql.open=false
#mysql地址  格式  ip:port/dbName
com.yjh.mysql.url=127.0.0.1:3306/ec
#mysql 登陆账号
com.yjh.mysql.username=root
#mysql 登陆密码
com.yjh.mysql.password=root
#是否支持跨域请求  默认 true   false 不支持
com.yjh.cros.open=true
#指定哪些接口支持跨域   填写url规则   默认 * （所有） 多个用英文逗号隔开
com.yjh.cros.url-pattern=*
# IP白名单  白名单中的 ip 才可以跨域访问   格式：  ip:port   不配置默认*（所有）不能写空
com.yjh.cros.white-list=*
~~~~
