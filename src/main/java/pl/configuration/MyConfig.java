package pl.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Adam on 2016-08-29.
 */

//klasa odpowiedzialana za zaczytanie applicationContext.xml z katalogu resources.
@Configuration
@EnableAspectJAutoProxy
@ImportResource("classpath:applicationContext.xml")
@ComponentScan("pl")
public class MyConfig {
}
