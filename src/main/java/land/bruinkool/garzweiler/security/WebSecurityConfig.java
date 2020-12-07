package land.bruinkool.garzweiler.security;

import land.bruinkool.garzweiler.api.handler.CustomAccessDeniedHandler;
import land.bruinkool.garzweiler.security.jwt.AuthEntryPointJwt;
import land.bruinkool.garzweiler.security.jwt.AuthTokenFilter;
import land.bruinkool.garzweiler.security.userdetails.BruinkoolUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    BruinkoolUserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurity base = http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler).and();


        if (!activeProfile.equals("dev")) {
            base.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/error").permitAll()
                    .antMatchers("/api/v1/auth/**").permitAll();
        } else {
            base.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/error").permitAll()
                    .antMatchers("/api/v1/auth/**").permitAll()
                    .antMatchers("/swagger-ui.html").permitAll()
                    .antMatchers("/api-docs/**").permitAll()
                    .antMatchers("/api-docs.yaml").permitAll()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .anyRequest().authenticated();
        }


        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
