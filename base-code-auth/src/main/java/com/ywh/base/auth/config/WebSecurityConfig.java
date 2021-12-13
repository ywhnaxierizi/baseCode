package com.ywh.base.auth.config;

import com.ywh.base.auth.filter.AuthUserPasswdAuthenFilter;
import com.ywh.base.auth.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author ywh
 * @description security的配置类
 * @Date 2021/11/16 09:58
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;

    /**
     * 用户认证
     * @param builder
     */
    public void authenticationConfiguration(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(loginAuthenticationProvider);
    }

    /**
     * 忽略静态资源
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**","/css/**","/image/**");
    }

    /**
     * 拦截url
     * @param http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //无需拦截的
                .antMatchers("/","/swagger-ui.html","swagger-resources/**").permitAll()
                .and()
                .logout()
                //登出的url
                .logoutUrl("/auth/logout")
                //登出时删除的cookie名
                .deleteCookies("verifycode")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/**","/auth/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                //自定义登录过滤器
                .addFilterAt(authUserPasswdAuthenFilter(), UsernamePasswordAuthenticationFilter.class)
                //添加过滤器
                .addFilter(new JwtAuthFilter(authenticationManagerBean()));
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 注册自定义登录过滤器
     * @return
     * @throws Exception
     */
    @Bean
    public AuthUserPasswdAuthenFilter authUserPasswdAuthenFilter() throws Exception {
        AuthUserPasswdAuthenFilter authUserPasswdAuthenFilter = new AuthUserPasswdAuthenFilter();
        authUserPasswdAuthenFilter.setAuthenticationManager(authenticationManagerBean());
        return authUserPasswdAuthenFilter;
    }

    /**
     * 在用户登录认证时，用户密码是需要加密的
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
