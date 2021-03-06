/*
 * Copyright 2014-2019 michael-simons.eu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ac.simons.biking2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.env.EnvironmentEndpoint;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest.to;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author Michael J. Simons, 2014-02-19
 */
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SuppressWarnings({"squid:S1118"}) // This is not a utility class. It cannot have a private constructor.
public class SecurityConfig {

    @Bean
    WebSecurityConfigurerAdapter defaultWebSecurityConfigurerAdapter(@Value("${biking2.require-ssl:false}") final boolean requireSSL) {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(final HttpSecurity http) throws Exception {
                // @formatter:off
                http
                    .httpBasic()
                        .and()
                    .authorizeRequests()
                        .antMatchers("/api/system/env/java.runtime.version")
                            .permitAll()
                        .requestMatchers(to(HealthEndpoint.class, InfoEndpoint.class, MetricsEndpoint.class))
                            .permitAll()
                        .requestMatchers(to(EnvironmentEndpoint.class))
                            .authenticated()
                        .antMatchers("/**").permitAll()
                        .and()
                    .sessionManagement()
                        .sessionCreationPolicy(STATELESS)
                        .and()
                    .csrf()
                        .disable()
                    .headers()
                        .frameOptions() // OEmbedController#embedTrack uses an iframe
                        .disable();
                // @formatter:on
                if (requireSSL) {
                    http.requiresChannel().anyRequest().requiresSecure();
                }
            }
        };
    }
}
