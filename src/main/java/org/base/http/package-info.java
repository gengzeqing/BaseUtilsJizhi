package org.base.http;
/**
 * 官网地址 : https://github.com/burukeYou/UniAPI
 *
 * 一个声明式的Http接口 对接框架
 *
 * maven依赖
 * <dependency>
 *      <groupId>io.github.burukeyou</groupId>
 *      <artifactId>uniapi-http</artifactId>
 *      <version>0.0.4</version>
 * </dependency>
 *
 * 注解：
 *  0.@JZHttpApi 自定义继承注解 
 *  1.@HttpApi 用于标记接口上，该接口上的方法会被代理，指定自定义的Http代理逻辑， 也可配置请求的url路径等等。
 *  2.@QueryPar 标记Http请求url的查询参数
 *      {
 *          @PostHttpInterface
 *          BaseRsp<String> getUser(@QueryPar("id")  String id,  //  普通值
 *          @QueryPar("ids") List<Integer> idsList, //  普通值集合
 *          @QueryPar User user,  // 对象
 *          @QueryPar Map<String, Object> map); // Map
 *
 *      }
 *  3.@PathPar 标记Http请求路径变量参数，仅支持标记普通值类型
 *      {
 *        @PostHttpInterface("/getUser/{userId}/detail")
 *        BaseRsp<String> getUser(@PathPar("userId")  String id);  //  普通值
 *      }
 *  4.@HeaderPar 标记Http请求头参数
 *     {
 *             @PostHttpInterface
 *             BaseRsp<String> getUser(@HeaderPar("id")  String id,  //  普通值
 *             @HeaderPar User user,  // 对象
 *             @HeaderPar Map<String, Object> map); // Map
 *     }
 *   5.@CookiePar 用于标记Http请求的cookie请求头
 *   {
 *           @PostHttpInterface
 *           BaseRsp<String> getUser(@CookiePar("id")  String cookiePar,  //   普通值 （指定name）当成单个cookie键值对处理
 *           @CookiePar String cookieString,  //  普通值 （不指定name），当成完整的cookie字符串处理
 *           @CookiePar com.burukeyou.uniapi.http.support.Cookie cookieObj,  // 单个Cookie对象
 *           @CookiePar List<com.burukeyou.uniapi.http.support.Cookie> cookieList // Cookie对象列表
 *           @CookiePar Map<String, Object> map); // Map
 *   }
 *
 *   6.@ComposePar  这个注解本身不是对Http请求内容的配置，仅用于标记一个对象，然后会对该对象内的所有标记了其他@Par注解的字段进行嵌套解析处理， 目的是减少方法参数数量，支持都内聚到一起配置
 *   {
 *            @PostHttpInterface
 *            BaseRsp<String> getUser(@ComposePar UserReq req);
 *
 *            比如UserReq里面的字段可以嵌套标记其他@Par注解，具体支持的标记类型和逻辑与前面一致
 *            class UserReq {
 *                 @QueryPar
 *                 private Long id;
 *                 @HeaderPar
 *                 private String name;
 *                 @BodyJsonPar
 *                 private Add4DTO req;
 *                 @CookiePar
 *                 private String cook;
 *            }
 *   }
 *  7.HttpApiProcessor 生命周期钩子
 *  HttpApiProcessor表示是一个发送和响应和反序列化一个Http请求接口的各种生命周期钩子，开发者可以在里面自定义编写各种对接逻辑
 *
 *              postBeforeHttpMetadata                (请求发送前)在发送请求之前，对Http请求体后置处理
 *                     |
 *                     V
 *              postSendingHttpRequest                (请求发送时)在Http请求发送时处理
 *                     |
 *                     V
 *           postAfterHttpResponseBodyString          (请求响应后)对响应body文本字符串进行后置处理
 *                     |
 *                     V
 *          postAfterHttpResponseBodyResult           (请求响应后)对响应body反序列化后的结果进行后置处理
 *                     |
 *                     V
 *          postAfterMethodReturnValue                (请求响应后)对代理的方法的返回值进行后置处理，类似aop的后置处理
 *
 *  1、postBeforeHttpMetadata: 可在发送http请求之前对请求体进行二次处理，比如加签之类
 *
 *  2、postSendHttpRequest: Http请求发送时会回调该方法，可以在该方法执行自定义的发送逻辑或者打印发送日志
 *
 *  3、postAfterHttpResponseBodyString： Http请求响应后，对响应body字符串进行进行后置处理，比如如果是加密数据可以进行解密
 *
 *  4、postAfterHttpResponseBodyResult： Http请求响应后，对响应body反序列化后的对象进行后置处理，比如填充默认返回值
 *
 *  5、postAfterMethodReturnValue： Http请求响应后，对代理的方法的返回值进行后置处理，类似aop的后置处理
 *
 * 其他
 *
 * HttpMetadata: 表示此次Http请求的请求体，包含请求url，请求头、请求方式、请求cookie、请求体、请求参数等等。
 * HttpApiMethodInvocation: 继承自MethodInvocation， 表示被代理的方法调用上下文，可以拿到被代理的类，被代理的方法，被代理的HttpAPI注解、HttpInterface注解等信息
 *
 */