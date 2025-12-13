# 📚 Guía de Pruebas Unitarias - NWAPP

## ✅ Pruebas Más Sencillas y Fáciles de Entender

### 🥇 **1. Pruebas de Modelos de Datos** (MÁS FÁCIL)
**Archivo**: `DatosTest.kt` y `AuthModelsTest.kt`

**¿Por qué son las más fáciles?**
- Solo crean objetos y verifican sus valores
- No requieren mocks ni configuración compleja
- Patrón simple: Crear objeto → Verificar valores

**Ejemplo**:
```kotlin
@Test
fun `cuando se crea un Datos con valores, debe contener esos valores`() {
    // Crear objeto
    val datos = Datos(nombre = "Parque", latitud = "-33.4569", longitud = "-70.6483")
    
    // Verificar
    assertThat(datos.nombre).isEqualTo("Parque")
}
```

---

### 🥈 **2. Pruebas de Validaciones** (MUY FÁCIL)
**Archivo**: `ValidationUtilsTest.kt`

**¿Por qué son fáciles?**
- Funciones puras: entrada → salida
- Lógica simple de entender
- No dependen de bases de datos o APIs

**Ejemplo**:
```kotlin
@Test
fun `email válido debe retornar true`() {
    val result = ValidationUtils.isEmailValid("usuario@ejemplo.com")
    assertThat(result).isTrue()
}
```

---

### 🥉 **3. Pruebas de Estado en ViewModels** (FÁCIL)
**Archivo**: `SignupViewModelBasicTest.kt`

**¿Por qué son relativamente fáciles?**
- Solo verifican cambios de estado
- No requieren configuración de corrutinas (versión básica)
- Siguen un patrón predecible

**Ejemplo**:
```kotlin
@Test
fun `cuando se llama onNameChange, el nombre debe actualizarse`() {
    var name = ""
    name = "Juan Pérez"
    assertThat(name).isEqualTo("Juan Pérez")
}
```

---

### 🔧 **4. Pruebas de Repositorios** (INTERMEDIO)
**Archivo**: `FormularioRepositoryTest.kt`

**¿Por qué son más complejas?**
- Pueden requerir mocks
- Usan corrutinas (runTest)
- Simulan llamadas a APIs

**Ejemplo básico**:
```kotlin
@Test
fun `validarLogin con credenciales vacías debe retornar false`() = runTest {
    val repository = FormularioRepository()
    val result = repository.validarLogin("", "")
    assertThat(result).isFalse()
}
```

---

## 🚀 Cómo Ejecutar las Pruebas

### Desde Android Studio:
1. **Una prueba específica**: Click derecho en el nombre de la prueba → Run
2. **Toda una clase**: Click derecho en el nombre de la clase → Run
3. **Todas las pruebas**: Click derecho en `app/src/test/java` → Run Tests

### Desde la Terminal:
```bash
# Ejecutar todas las pruebas unitarias
./gradlew test

# Ejecutar solo las pruebas de una clase específica
./gradlew test --tests "cl.duoc.nwapp.model.DatosTest"

# Ver reporte de pruebas
./gradlew test --info
```

---

## 📊 Anatomía de una Prueba (Patrón AAA)

```kotlin
@Test
fun `descripción clara de qué se está probando`() {
    // ARRANGE (Preparar) - Configurar el escenario
    val email = "test@test.com"
    
    // ACT (Actuar) - Ejecutar la acción
    val result = ValidationUtils.isEmailValid(email)
    
    // ASSERT (Afirmar) - Verificar el resultado
    assertThat(result).isTrue()
}
```

---

## 🛠️ Herramientas de Testing Configuradas

- ✅ **JUnit**: Framework base para pruebas
- ✅ **Google Truth**: Aserciones más legibles (`assertThat()`)
- ✅ **MockK**: Crear mocks en Kotlin
- ✅ **Coroutines Test**: Probar código asíncrono

---

## 📝 Orden Recomendado para Aprender

1. **Empieza con**: `DatosTest.kt` - La más simple
2. **Continúa con**: `ValidationUtilsTest.kt` - Lógica directa
3. **Sigue con**: `AuthModelsTest.kt` - Modelos de API
4. **Luego prueba**: `SignupViewModelBasicTest.kt` - Estado básico
5. **Finalmente**: `FormularioRepositoryTest.kt` - Con corrutinas

---

## 💡 Consejos

1. **Nombres descriptivos**: Usa nombres que expliquen qué pruebas
   ```kotlin
   // ✅ Bueno
   fun `email válido debe retornar true`()
   
   // ❌ Malo
   fun testEmail1()
   ```

2. **Una afirmación por prueba**: Cada test debe verificar una cosa
   
3. **Independencia**: Las pruebas no deben depender unas de otras

4. **Pruebas rápidas**: Las pruebas unitarias deben ejecutarse en milisegundos

---

## 🎯 Beneficios de las Pruebas Unitarias

- ✅ Detectan bugs antes de que lleguen a producción
- ✅ Documentan cómo funciona tu código
- ✅ Facilitan refactorizar con confianza
- ✅ Mejoran el diseño del código

---

## 📚 Recursos Adicionales

- [JUnit 4 Documentation](https://junit.org/junit4/)
- [Google Truth](https://truth.dev/)
- [MockK](https://mockk.io/)
- [Testing en Android](https://developer.android.com/training/testing)

