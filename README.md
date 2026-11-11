Gestor de Incidencias (JavaFX)
Aplicación de escritorio en JavaFX para la gestión, filtrado y consulta de incidencias con interfaces adaptativas.

Estructura de la Interfaz
 - Cabecera: Título y filtros distribuidos mediante un Region elástico.
 - Formulario: Campo de texto para el registro rápido de incidencias.
 - Panel Inferior: Cajas de contadores y barra de notificaciones separadas por un Region elástico.

¿Para qué sirve cada parte del programa?
    - La ventana principal: Muestra un resumen general con contadores rápidos de cuántas incidencias hay abiertas, en proceso o resueltas, junto con un listado central donde se ven todas las tareas registradas.
    - El buscador y los filtros: Permiten encontrar rápidamente cualquier incidencia escribiendo una palabra clave o seleccionando un estado concreto para ver solo lo que interesa en cada momento.    
    - El formulario de registro: Permite dar de alta nuevas incidencias indicando un nombre y una descripción detallada, asegurando que quede constancia de cualquier problema que surja. 
    - Las opciones de control: Ofrecen la posibilidad de consultar los datos completos de una tarea, cambiar su estado de forma sencilla o limpiar aquellas que ya se hayan solucionado.

Índice de Mejoras Realizadas
1. Documentación del Proyecto
Creación del archivo README.md, documentacion con comentarios en el codigo para facilitar la lectura del codigo

2. Diseño e Interfaz de Usuario (UI)
Maquetación flexible y adaptativa: Se ha optimizado la interfaz reemplazando layouts rígidos por contenedores flexibles (GridPane y Region), aplicando condiciones de crecimiento y ajustando las dimensiones de los formularios para una mejor experiencia visual (especialmente en la sección de contadores).

3. Experiencia de Usuario y Textos
Revisión de mensajes del sistema: Se han unificado y corregido los mensajes y alertas mostrados al usuario para garantizar coherencia y claridad en todos los controles básicos.

4. Gestión de Datos y Estructuras
Migración a ObservableList: Se ha corregido la inconsistencia y duplicidad de datos entre el ListView y el ArrayList (donde el orden no coincidía y los contadores se gestionaban manualmente). Ahora se utiliza una lista observable integrada con JavaFX, manteniendo los datos sincronizados y ordenados de forma automática.
