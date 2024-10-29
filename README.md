# examenAndroid3
 
link al repositorio: https://github.com/mbp4/examenAndroid3.git

## Descripción 

En este proyecto se nos pedia realizar una aplicacion encarga del control de tareas, cuenta con una clase de Registro, de Listado, de Detalles y una data class de Tarea que será Serailizable para manejarla en varias clases.

### Listado

En esta clase se le muestra al usuario un listado de tareas, este es el pseudocódigo: 

```
Clase ListadoActivity extiende ComponentActivity

    Declarar variables:
        botonImagen, lista, btnPendientes, btnHechas, adapter, recordatoriosLista
        pendiente es Boolean y se inicializa en verdadero

    Función onCreate(Bundle estado):
        Llamar a super.onCreate(estado)
        Establecer el diseño de la actividad como activity_listado

        Inicializar componentes de interfaz:
            botonImagen <- buscar elemento por id imageButton
            lista <- buscar elemento por id lista
            btnPendientes <- buscar elemento por id Pendientes
            btnHechas <- buscar elemento por id Hechas

        Inicializar lista de recordatorios como nueva lista vacía
        Inicializar adapter como un ArrayAdapter con recordatoriosLista
        Asignar adapter a lista

        Registrar lista para menú contextual

        Asignar evento de clic a botonImagen:
            Crear intent para abrir RegistroActivity
            Iniciar RegistroActivity con el intent

        Asignar evento de clic a btnPendientes:
            Establecer pendientes a verdadero
            Llamar a actualizarLista()

        Asignar evento de clic a btnHechas:
            Establecer pendientes a falso
            Llamar a actualizarLista()

        Asignar evento de clic a los elementos de lista:
            Obtener tarea según si pendientes es verdadero o falso
            Crear intent para abrir DetallesActivity
            Agregar tareaSeleccionada al intent
            Iniciar DetallesActivity con el intent

        Llamar a actualizarLista()

    Función actualizarLista():
        Limpiar elementos en recordatoriosLista
        Si pendientes es verdadero:
            Agregar nombres de tareas pendientes a recordatoriosLista
        Si no:
            Agregar nombres de tareas completadas a recordatoriosLista
        Notificar al adapter que los datos han cambiado

    Función onCreateContextMenu(menu, vista, menuInfo):
        Llamar a super.onCreateContextMenu(menu, vista, menuInfo)
        Inflar menú contextual desde context_menu

    Función onContextItemSelected(item):
        Obtener información del menú contextual desde item
        Si item es acción eliminar:
            Remover tarea de recordatoriosLista en la posición seleccionada
            Notificar cambio en adapter
            Remover tarea correspondiente en RegistroActivity.pendientes
            Mostrar mensaje con tarea eliminada
            Retornar verdadero

        Si item es acción realizada:
            Obtener nombre de la tarea seleccionada en recordatoriosLista
            Buscar tarea en RegistroActivity.pendientes
            Si se encuentra tarea:
                Remover tarea de RegistroActivity.pendientes
                Agregar tarea a RegistroActivity.completadas
                Marcar tarea como hecha en recordatoriosLista
                Notificar cambio en adapter
                Mostrar mensaje de tarea marcada como hecha
            Retornar verdadero

        En otro caso:
            Llamar a super.onContextItemSelected(item)

```

La actividad cuenta con:

 -> Un botón que lleva al usuario a la pantalla de registro.

 -> Un ListView con todas las tareas añadidas. 

 -> Un botón que muestre solo las tareas pendientes. 

 -> Un botón que muestre solo las tareas hechas.

 -> Un contextMenu para modificar las tareas.

 -> Al pulsar en una tarea se redirige a los detalles de esa tarea. 

### Registro

En esta clase se le muestra al usuario un fromulario para añadir tareas, este es el pseudocódigo: 

```

Clase RegistroActivity extiende ComponentActivity

    Definir objeto companion:
        lista: lista mutable de objetos Tarea
        pendientes: lista de tareas pendientes
        completadas: lista de tareas completadas

    Declarar variables:
        textNombre, textDescripcion, textFecha, spinnerPrioridad, textCoste
        btnRegistrar, btnCancelar, fechaSeleccionada como LocalDate

    Función onCreate(Bundle estado):
        Llamar a super.onCreate(estado)
        Establecer el diseño de la actividad como activity_registro

        Inicializar elementos de la interfaz:
            textNombre <- buscar elemento por id textNombre
            textDescripcion <- buscar elemento por id textDescripcion
            textFecha <- buscar elemento por id textFecha
            spinnerPrioridad <- buscar elemento por id spinnerPrioridad
            textCoste <- buscar elemento por id textCoste
            btnRegistrar <- buscar elemento por id Registrar
            btnCancelar <- buscar elemento por id Cancelar

        Inicializar fechaSeleccionada con la fecha actual
        Asignar la fecha actual como texto en textFecha

        Definir opciones de prioridad como lista ["URGENTE", "NO URGENTE"]
        Crear adapter con opciones de prioridad y asignarlo a spinnerPrioridad

        Asignar evento de clic a textFecha:
            Obtener año, mes, y día actuales
            Mostrar un DatePickerDialog:
                Al seleccionar fecha en el DatePicker:
                    Actualizar fechaSeleccionada con el valor seleccionado
                    Establecer fecha seleccionada como texto en textFecha

        Asignar evento de clic a btnCancelar:
            Mostrar mensaje "Tarea cancelada"
            Crear intent para abrir ListadoActivity
            Iniciar ListadoActivity con el intent

        Asignar evento de clic a btnRegistrar:
            Llamar a registrarTarea()

    Función registrarTarea():
        Obtener valores de los campos de texto:
            nombre, descripcion, prioridad, coste
        Convertir coste a Double, o establecerlo en 0 si no es válido

        Si nombre no está vacío:
            Crear nueva tarea con los datos ingresados
            Agregar nueva tarea a lista y a pendientes
            Mostrar mensaje "Tarea registrada"
            Crear intent para abrir ListadoActivity
            Iniciar ListadoActivity con el intent
        Si no:
            Mostrar mensaje de error indicando que el nombre no debe estar vacío


```
La actividad cuenta con:

 -> Varios EditText para añadir los datos, donde: 

   -> Uno de ellos será un Spinner para elegir si la tarea es o no urgente.

   -> Y un calendario para elegir la fecha.

 -> Un botón que añada la tarea. 

 -> Un botón que cancele la acción y devuelva al usuario al listado.

### Detalles 

En esta clase se le muestra al usuario los detalles de la tarea elegida, este es el pseudocódigo: 

```
Clase DetallesActivity extiende ComponentActivity

    Declarar variables:
        textNombre, textDescripcion, textFecha, textPrioridad, textCoste, btnVolver

    Función onCreate(Bundle estado):
        Llamar a super.onCreate(estado)
        Establecer el diseño de la actividad como activity_detalles

        Inicializar elementos de la interfaz:
            textNombre <- buscar elemento por id textNombre
            textDescripcion <- buscar elemento por id textDescripcion
            textFecha <- buscar elemento por id textFecha
            textPrioridad <- buscar elemento por id textPrioridad
            textCoste <- buscar elemento por id textCoste
            btnVolver <- buscar elemento por id btnVolver

        Obtener tarea seleccionada desde intent como un objeto Tarea

        Si tarea no es nulo:
            Asignar a textNombre el nombre de la tarea
            Asignar a textDescripcion la descripción de la tarea
            Asignar a textFecha el texto "Para el" seguido de la fecha de la tarea
            Asignar a textPrioridad la prioridad de la tarea

            Si la prioridad de la tarea es "URGENTE":
                Cambiar color de texto en textPrioridad a rojo

            Asignar a textCoste el texto "Tiene un coste de" seguido del coste de la tarea y "€"

        Asignar evento de clic a btnVolver:
            Llamar a finish() para cerrar la actividad actual

```

La actividad cuenta con:

 -> Varios TextView para mostrar todos los detalles, donde: 

   -> Dependiendo de si la tarea es urgente o no se mostrará de color Rojo en el caso de que sea urgente.

 -> Un botón que devuelva al usuario al listado


