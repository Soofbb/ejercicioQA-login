package com.testqalogin.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.net.URL;
import java.net.URISyntaxException;
import java.time.Duration; //para config el tiempo de espera en java
import java.util.List; 

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull; //para verificacion de que se encontró el elemento
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    private WebDriver driver;
    private String fileUrl; //url de archivo 

    @BeforeEach
    public void setUp() {
        //ubicación del ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sofia\\driver\\chromedriver-win64\\chromedriver.exe");
        // inicializar Webdriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        //cargar html local
        try {
            URL resourceUrl = getClass().getClassLoader().getResource("login.html");

            if (resourceUrl == null) {
                throw new RuntimeException("Error: El archivo login.html no fue encontrado en src/test/resources");
            }

            fileUrl = new File(resourceUrl.toURI()).getAbsolutePath();

        } catch (URISyntaxException e) {
            throw new RuntimeException("Error al obtener la URI del archivo login.html", e);
        }

        driver.get("file:///" + fileUrl.replace("\\", "/"));
    }

    //prueba para verificar la presencia de elementos clave 
    @Test
    public void testLoginPageElementsPresence() {
        System.out.println("Ejecutando test: Verificando presencia de elementos de login");

        //verificar el título "Welcome"
        WebElement welcomeTitle = driver.findElement(By.xpath("//h1[text()='Welcome']"));
        assertNotNull(welcomeTitle, "El título 'Welcome' no fue encontrado");
        assertTrue(welcomeTitle.isDisplayed(), "El título 'Welcome' no es visible");
        System.out.println("Título 'Welcome' encontrado y visible");

        //verificar el campo de entrada de Email Address
        WebElement emailInput = driver.findElement(By.id("username"));
        assertNotNull(emailInput, "El campo de 'Email Address' no fue encontrado");
        assertTrue(emailInput.isDisplayed(), "El campo de 'Email Address' no es visible");
        System.out.println("Campo de 'Email Address' encontrado y visible");

        //verificar el campo de entrada de Password
        WebElement passwordInput = driver.findElement(By.id("password"));
        assertNotNull(passwordInput, "El campo de 'Password' no fue encontrado");
        assertTrue(passwordInput.isDisplayed(), "El campo de 'Password' no es visible");
        System.out.println("Campo de 'Password' encontrado y visible");


        //definir el localizador del botón
        By continueButtonLocator = By.cssSelector("button[type='submit']");

        //instancia de WebDriverWait. Esperará hasta 10 segundos.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //espera hasta que el elemento sea visible
        WebElement continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(continueButtonLocator));


        //verificar el botón "Continue"
        assertNotNull(continueButton, "El botón 'Continue' no fue encontrado después de esperar");
        assertTrue(continueButton.isDisplayed(), "El botón 'Continue' no es visible después de esperar");
        assertTrue(continueButton.isEnabled(), "El botón 'Continue' no está habilitado después de esperar");
        System.out.println("Botón 'Continue' encontrado, visible y habilitado después de esperar");

        System.out.println("Todos los elementos clave de la página de login están presentes y visibles");
    }
    
  //prueba para verificar enlaces de login y boton de google 
    @Test
    public void testPresenceOfLoginOptions() {
        System.out.println("ejecutando test: verificar presencia de enlaces para login, y opción 'Continue with Google' ");

        //verificar "Forgot password?"
        WebElement forgotPasswordLink = driver.findElement(By.xpath("//a[text()='Forgot password?']")); 
        assertNotNull(forgotPasswordLink, "Enlace 'Forgot password?' no fue encontrado");
        assertTrue(forgotPasswordLink.isDisplayed(), "Enlace 'Forgot password?' no está visible");
        System.out.println("Enlace 'Forgot password?' encontrado y visible");

        //verificar el enlace "Sign up"
        WebElement signUpLink = driver.findElement(By.xpath("//a[text()='Sign up']")); 
        assertNotNull(signUpLink, "Enlace 'Sign up' no fue encontrado");
        assertTrue(signUpLink.isDisplayed(), "Enlace 'Sign up' no está visible");
        System.out.println("Enlace 'Sign up' encontrado y visible");


        //verificar el botón "Continue with Google"
        //localizarlo por su data-provider
        WebElement continueWithGoogleButton = driver.findElement(By.cssSelector("button[data-provider='google']")); 
        assertNotNull(continueWithGoogleButton, "Botón 'Continue with Google' no fue encontrado");
        assertTrue(continueWithGoogleButton.isDisplayed(), "Botón 'Continue with Google' no está visible");
        assertTrue(continueWithGoogleButton.isEnabled(), "Botón 'Continue with Google' no está habilitado");
        System.out.println("Botón 'Continue with Google' encontrado, visible y habilitado");


        System.out.println("Verificación de presencia de enlaces y boton de inicio alternativo completada");
    }
    
    //prueba para verificar duplicados
    @Test
    public void testNoDuplicateKeyElements() {
        System.out.println("Ejecutando test: Verificar que no haya elementos clave duplicados");

        //verificar que solo hay un título "Welcome"
        List<WebElement> welcomeTitles = driver.findElements(By.xpath("//h1[text()='Welcome']")); //
        //el tamaño de la lista debe ser exactamente 1
        assertEquals(1, welcomeTitles.size(), "Se encontró más de un título 'Welcome'");
        System.out.println("Verificado: Solo hay un título 'Welcome'");


        //verificar que solo hay una etiqueta "Email address" visible para el usuario
        List<WebElement> emailLabels = driver.findElements(By.xpath("//label[@for='username' and text()='Email address']"));
        assertEquals(1, emailLabels.size(), "Se encontró más de una etiqueta 'Email address' principal");
        System.out.println("Verificado: Solo hay una etiqueta 'Email address' principal.");

        //verificar que solo hay una etiqueta "Password" visible para el usuario
        List<WebElement> passwordLabels = driver.findElements(By.xpath("//label[@for='password' and text()='Password']")); 
        assertEquals(1, passwordLabels.size(), "Se encontró más de una etiqueta 'Password'");
        System.out.println("Verificado: Solo hay una etiqueta 'Password'");

        //verificar que solo haya un boton submit principal
        List<WebElement> submitButtons = driver.findElements(By.cssSelector("button[type='submit']"));
         assertEquals(1, submitButtons.size(), "Se encontró más de un botón de tipo 'submit' principal");
         System.out.println("Verificado: Solo hay un botón de tipo 'submit' principal");

        System.out.println("Verificación de elementos duplicados completada");
    }
    
 //prueba visibilidad del password
    @Test
    public void testPasswordVisibility() {
        System.out.println("Ejecutando test: Verificar alternancia de visibilidad del campo de password");

        //localizar el campo de password
        WebElement passwordInput = driver.findElement(By.id("password")); //

        //verificar que por defecto "password" está oculto
        assertEquals("password", passwordInput.getAttribute("type"), "El campo de password no es de tipo 'password' por defecto");
        System.out.println("Verificado: El campo de password está oculto por defecto");

        //localizar el botón de alternar visibilidad
        WebElement toggleButton = driver.findElement(By.cssSelector("button[data-action='toggle']")); //

        //localizar los tooltips "Show password" y "Hide password" para verificar su visibilidad
        WebElement showPasswordTooltip = driver.findElement(By.cssSelector("span.show-password-tooltip")); //
        WebElement hidePasswordTooltip = driver.findElement(By.cssSelector("span.hide-password-tooltip")); //

        //verificar que "Show password" es visible y "Hide password" está oculto por defecto
        assertTrue(showPasswordTooltip.isDisplayed(), "'Show password' tooltip no está visible por defecto");
        assertFalse(hidePasswordTooltip.isDisplayed(), "'Hide password' tooltip está visible por defecto cuando no debería");
        System.out.println("Verificado: Tooltip 'Show password' visible, 'Hide password' oculto por defecto");

        //clic en el botón de alternar
        toggleButton.click();
        System.out.println("Clic en el botón de alternar visibilidad");


        //verificar que el type del campo de password ha cambiado a text
        assertEquals("text", passwordInput.getAttribute("type"), "El campo de password no cambió a tipo 'text' después del clic ");
        System.out.println("Verificado: El campo de password ahora es de tipo 'text' visible ");


        //clic de nuevo para verificar que vuelve a ocultarse
        toggleButton.click();
         System.out.println("Clic de nuevo en el botón de alternar visibilidad ");

        //verificar que type ha vuelto a ser "password"
        assertEquals("password", passwordInput.getAttribute("type"), "El campo de password no volvió a ser de tipo 'password' después del segundo clic ");
        System.out.println("Verificado: El campo de password volvió a ser de tipo 'password' oculto ");
    }
    
    //cerrar navegador por cada prueba
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}