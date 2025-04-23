# Ejercicio QA Automation - Login (Selenium, Java, JUnit, Maven)
Esta prueba muestra la automatización de pruebas de interfaz de usuario para una página de login, utilizando un archivo HTML proporcionado como base. Se emplearon las tecnologías Selenium WebDriver, Java 11, el framework de pruebas JUnit 5 y el gestor de proyectos Maven.

# Objetivo
El objetivo fue diseñar e implementar casos de prueba automatizados para verificar la correcta presentación y el comportamiento básico de los elementos en la página de login representada por el archivo estático login.html ( aclarando que al ser estático, no se pueden testear funcionalidades como validar credenciales, etc).
# Tecnologías utilizadas 
* Selenium WebDriver: Para la interacción con el navegador.
* Java 11: Lenguaje de programación.
* JUnit 5: Framework para estructurar, organizar y ejecutar las pruebas.
* Maven: Gestor de dependencias y herramienta de construcción del proyecto.
* ChromeDriver: El driver específico para automatizar el navegador Google Chrome.

# Enfoque de Diseño de Pruebas
El diseño se basó en la identificación de los aspectos clave de la página de login a verificar y en la organización de estas verificaciones en métodos de prueba unitarios e independientes utilizando JUnit. Cada método "@Test" se centra en probar una funcionalidad o estado específico de la UI(Interfaz de Usuario).

# Casos de Prueba Implementados
Verificación de Carga Básica de Página _(testPageLoadsSuccessfullyBasic)_:

* Propósito: Confirmar que el archivo HTML se carga correctamente en el navegador y que la estructura principal esperada de la página está presente.
* Implementación: Este test verifica la presencia y visibilidad de elementos contenedores clave (como la sección principal o el formulario) después de que Selenium carga el archivo HTML local.

Verificación de Presencia y Visibilidad de Elementos Clave _(testLoginPageElementsPresence)_:

* Propósito: Asegurar que todos los elementos fundamentales y necesarios para la interacción de login (título "Welcome", campos de email y password, botón "Continue") están presentes en el DOM y son visibles para el usuario.
* Implementación: Utilicé localizadores (By.id, By.cssSelector, By.xpath) para encontrar cada elemento. Se emplean aserciones (assertNotNull, assertTrue(isDisplayed())) para validar su existencia y visibilidad. Para asegurar la fiabilidad de la prueba, especialmente con elementos que podrían cargarse o renderizarse dinámicamente, implementé Esperas Explícitas (WebDriverWait con ExpectedConditions). Esto indica a Selenium que espere un tiempo determinado hasta que un elemento cumpla una condición específica (como ser visible) antes de interactuar, evitando fallos por problemas de timing.
  
Verificación de Ausencia de Elementos Duplicados _(testNoDuplicateKeyElements)_:

* Propósito: Confirmar la consistencia de la interfaz verificando que ciertos elementos clave que deberían ser únicos (como el título principal "Welcome" o las etiquetas principales de los campos de entrada) no aparezcan duplicados inesperadamente en el HTML.
* Implementación: Utilicé driver.findElements() (en plural) para obtener una lista de todos los elementos que coinciden con un localizador particular. Luego, se usa una aserción (assertEquals(lista.size(), 1)) para verificar que el tamaño de esta lista es exactamente 1, asegurando que el elemento no está duplicado.
  
Verificación de la Funcionalidad de Visibilidad del Password _(testPasswordVisibility)_:

* Propósito: Testear la característica interactiva de mostrar u ocultar el contenido del campo de password.
* Implementación: La prueba localiza el campo de password y el botón de alternar visibilidad. Verifica el estado inicial del campo de password consultando su atributo type (esperando "password"). Simula un clic en el botón de alternar y verifica que el atributo type cambia (esperando "text"). Se puede extender para simular un segundo clic y verificar que vuelve al estado original. Se utiliza getAttribute() para obtener el valor del atributo type y click() para simular la interacción.

Verificación de Presencia de Opciones de Login Alternativas _(testPresenceOfLoginOptions)_:

* Propósito: Asegurar que los enlaces para recuperar contraseña ("Forgot password?"), registrarse ("Sign up") y el botón para login con Google ("Continue with Google") están presentes y visibles en la página de login.
* Implementación: Localizo estos elementos utilizando selectores(XPath para los enlaces por texto, selector CSS para el botón por atributo) y se verifican su presencia y visibilidad (assertNotNull, assertTrue(isDisplayed())). Para el botón de Google, también se puede verificar que está habilitado (assertTrue(isEnabled())).
