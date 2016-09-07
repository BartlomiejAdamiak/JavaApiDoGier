package pl.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * JavaApiDoGier - program służący do przedstawiania statystyk gracza
 * Copyright (C) 19../20.. Bartłomiej Adamiak, Adam Szczeciński,
 * Michał Kudlewski, Beata Cabaj
 * <p>
 * Niniejszy program jest wolnym oprogramowaniem; możesz go
 * rozprowadzać dalej i/lub modyfikować na warunkach Powszechnej
 * Licencji Publicznej GNU, wydanej przez Fundację Wolnego
 * Oprogramowania - według wersji 2-giej tej Licencji lub którejś
 * z późniejszych wersji.
 * <p>
 * Niniejszy program rozpowszechniany jest z nadzieją, iż będzie on
 * użyteczny - jednak BEZ JAKIEJKOLWIEK GWARANCJI, nawet domyślnej
 * gwarancji PRZYDATNOŚCI HANDLOWEJ albo PRZYDATNOŚCI DO OKREŚLONYCH
 * ZASTOSOWAŃ. W celu uzyskania bliższych informacji - Powszechna
 * Licencja Publiczna GNU.
 * <p>
 * Z pewnością wraz z niniejszym programem otrzymałeś też egzemplarz
 * Powszechnej Licencji Publicznej GNU (GNU General Public License);
 * jeśli nie - napisz do Free Software Foundation, Inc., 675 Mass Ave,
 * Cambridge, MA 02139, USA.
 */

@Configuration
@EnableAspectJAutoProxy
@ImportResource("classpath:applicationContext.xml")
@ComponentScan("pl")
public class MyConfig {
}
