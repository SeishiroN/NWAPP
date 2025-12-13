# 🚀 INSTRUCCIONES PARA EJECUTAR LAS PRUEBAS

## ✅ MÉTODO 1: Desde Android Studio (RECOMENDADO)

### Opción A: Ejecutar una prueba individual
1. Abre el archivo de prueba que quieres ejecutar, por ejemplo:
   - `app/src/test/java/cl/duoc/nwapp/model/DatosTest.kt`
2. Haz clic derecho en el nombre de la clase `DatosTest`
3. Selecciona **"Run 'DatosTest'"** o presiona `Ctrl+Shift+F10`
4. ¡Listo! Verás los resultados en la ventana de abajo

### Opción B: Ejecutar todas las pruebas de un paquete
1. En la vista de proyecto, navega a:
   - `app/src/test/java/cl/duoc/nwapp`
2. Haz clic derecho en la carpeta
3. Selecciona **"Run 'Tests in 'cl.duoc.nwapp''"**

### Opción C: Ejecutar todas las pruebas unitarias
1. En la vista de proyecto, haz clic derecho en:
   - `app/src/test/java`
2. Selecciona **"Run 'All Tests'"**

---

## ⚙️ MÉTODO 2: Desde la Terminal de Android Studio

1. Abre la terminal en Android Studio (View → Tool Windows → Terminal)
2. Ejecuta uno de estos comandos:

```bash
# Ejecutar TODAS las pruebas unitarias
./gradlew test

# Ejecutar solo una clase específica
./gradlew test --tests "cl.duoc.nwapp.model.DatosTest"

# Ejecutar con más detalles
./gradlew test --info

# Ver reporte HTML después
start app/build/reports/tests/testDebugUnitTest/index.html
```

---

## 💻 MÉTODO 3: Desde PowerShell (Fuera de Android Studio)

1. Abre PowerShell
2. Navega al directorio del proyecto:
```powershell
cd D:\Codificacion\Androidd\NWAPP
```

3. Ejecuta las pruebas:
```powershell
.\gradlew.bat test
```

4. O ejecuta el script que creé:
```powershell
.\ejecutar-pruebas.bat
```

---

## 📊 MÉTODO 4: Ver Reporte HTML de Resultados

Después de ejecutar las pruebas, abre el reporte:

**Ruta del reporte:**
```
D:\Codificacion\Androidd\NWAPP\app\build\reports\tests\testDebugUnitTest\index.html
```

En PowerShell:
```powershell
start app\build\reports\tests\testDebugUnitTest\index.html
```

---

## 🎯 PRUEBAS RECOMENDADAS PARA EMPEZAR

### 1. Primera prueba (la más fácil):
```
Archivo: app/src/test/java/cl/duoc/nwapp/model/DatosTest.kt
Comando: ./gradlew test --tests "cl.duoc.nwapp.model.DatosTest"
```

### 2. Segunda prueba (validaciones):
```
Archivo: app/src/test/java/cl/duoc/nwapp/utils/ValidationUtilsTest.kt
Comando: ./gradlew test --tests "cl.duoc.nwapp.utils.ValidationUtilsTest"
```

### 3. Tercera prueba (modelos API):
```
Archivo: app/src/test/java/cl/duoc/nwapp/data/model/AuthModelsTest.kt
Comando: ./gradlew test --tests "cl.duoc.nwapp.data.model.AuthModelsTest"
```

---

## ⚡ EJECUCIÓN RÁPIDA - COPIA Y PEGA

### En Terminal de Android Studio o PowerShell:
```bash
cd D:\Codificacion\Androidd\NWAPP
./gradlew test
```

### Para ver solo las pruebas más sencillas:
```bash
./gradlew test --tests "cl.duoc.nwapp.model.*"
./gradlew test --tests "cl.duoc.nwapp.utils.*"
```

---

## 🔍 INTERPRETANDO RESULTADOS

### ✅ Resultado Exitoso:
```
> Task :app:testDebugUnitTest

cl.duoc.nwapp.model.DatosTest > cuando se crea un Datos... PASSED ✓

BUILD SUCCESSFUL
```

### ❌ Resultado con Fallos:
```
cl.duoc.nwapp.model.DatosTest > test FAILED
    Expected: "Parque"
    but was: "Plaza"
```

---

## 🐛 SOLUCIÓN DE PROBLEMAS

### Problema: "Task 'test' not found"
**Solución:** Usa `./gradlew :app:test` en su lugar

### Problema: "Gradle sync needed"
**Solución:** En Android Studio: File → Sync Project with Gradle Files

### Problema: No se generan reportes
**Solución:** Ejecuta `./gradlew clean test`

---

## 📝 NOTAS IMPORTANTES

1. **Primera vez:** La primera ejecución puede tardar más porque Gradle descarga dependencias
2. **Sincronización:** Asegúrate de sincronizar Gradle antes (File → Sync Project)
3. **Errores de compilación:** Si hay errores, primero ejecuta `./gradlew build`
4. **Cache:** Si hay problemas extraños, ejecuta `./gradlew clean test`

---

## 🎓 SIGUIENTE PASO

**¡EMPIEZA AHORA!**

La forma MÁS FÁCIL:
1. Abre Android Studio
2. Abre el archivo: `DatosTest.kt`
3. Haz clic derecho en `DatosTest`
4. Selecciona "Run 'DatosTest'"
5. ¡Observa cómo todas las pruebas pasan en verde! ✅

---

¡Listo para ejecutar! 🚀

