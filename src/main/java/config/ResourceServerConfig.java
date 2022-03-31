package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {



    @Override
    public void configure(HttpSecurity http) throws Exception {
       /* http.antMatcher("/api/**").permitAll();
*/
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**", "/api/**","/resources/**", "/static/**", "/css/**",
                "/js/**", "/images/**", "/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/health", "/info");

        }
}