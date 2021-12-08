#security说明
    1.增加security的相关配置，对前端的url进行拦截和放行，配置过滤器和自定义认证逻辑类
    2.AuthUserPasswdAuthenFilter继承了AbstractAuthenticationProcessingFilter，替换了UsernamePasswordAuthenticationFilter
    的作用，这个是适用于自定义认证路径和自定义认证字段；
    3.验证的代码走向：
        1）JwtAuthFilter：对请求的传参进行验证，比如验证码等，对密码进行解密处理
        2）LoginAuthenticationProvider：这里是用户的验证逻辑，通过securityService从数据库里获取用户信息，与传入的用户信息进行比对
        3）JwtAuthFilter：用户验证成功/失败的处理
    
    4.验证中，SecurityServiceImpl是获取用户信息的业务类，需要继承UserDetailsService
            SysUser是用户信息的实体类，需要继承UserDetails；