package xd.webmovies.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private JwtAuthenticationEntryPoint entryPoint;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationEntryPoint entryPoint) {
        this.userDetailsService = userDetailsService;
        this.entryPoint = entryPoint;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }


    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/movies","/tvshows","/tvshows/{id}/comments","/movies/{id}/comments","/user/add","/movies/search").permitAll()
                .antMatchers(HttpMethod.GET,"/tvshows/{id}","/movies/{id}","/mytvshows/latest","/mymovies/latest","/tvshows/latest","/movies/latest","/tvshows/search").permitAll()
                .antMatchers(HttpMethod.POST,"/movies/add","/tvshows/add").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/movies/{id}","/tvshows/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/movies/{id}","/tvshows/{id}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
