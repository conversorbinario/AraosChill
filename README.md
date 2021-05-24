# Proyecto fin de ciclo


## Descripción

Araos es una aplicación pensada para los aficionados a la ornitología.
Su funcionamiento es sencillo. Los usuarios pueden registrar los avistamientos de diferentes aves y su localización. La aplicación permite mostrar en un mapa dónde se ha producido el avistamiento y de qué especie es la ave. Si no sabes identificarla, puedes consultar material que te ayudará a ello: contamos con información sobre todas las especies y puedes escuchar el canto de cada una. También puedes apoyarte en el resto de usuarios, que pueden ayudarte a identificar la ave o, si quieres, identificar tu las aves de otros usuarios.

## Instalación / Puesta en marcha

En un emulador Android: Con Android Studio encendido y el amulador creado, selecciona al lado de botón de Play (Run) tu dispositivo emulado.

En un dispositivo real:
Primero, en tu móvil Android, activa las opciones de desarrollador. El modo de hacer esto varía entre dispositivos; generalmente, desde de Configuración/AcercaDelDispositivo/InformacionDelSofware se puede activar clicando repetidas veces en Numero de Compilacion. Acto seguido, en Configuracion aparecerá una nueva opción que es Opciones de desarrollador. En este apartado, activamos la Depuración USB. Más información en: 
https://developer.android.com/studio/debug/dev-options

Segundo, debemos efectuar algunos pasos desde nuestro ordenador. Estos pasos dependen a su vez del Sistema Operativo empleado. En Ubuntu Linux, desde la terminal.
    1. Ejecutamos sudo usermod -aG plugdev $LOGNAME. Cerramos sesión (Log out) y nos volvemos a loguear.
    2. Ejecutamos sudo apt-get install android-sdk-platform-tools-common

Más información en: https://developer.android.com/studio/run/

En este punto, si nos situamos en el directorio android_sdk/platform-tools/ y ejecutamos el comando "adb devices" deberíamos ver nuestro dispositivo real conectado (comprobar que hemos permitido tal conexión en nuestro ordenador).

Finalmente, con el Android Studio encendido, seleccionamos el dispositivo y ejecutamos la aplicación. En este punto, deberíamos poder probar ya nuestra app.





## Uso

Primero, regístrate en la misma para poder subir tus avistamientos, o identificar los de otros.
Si solo quieres consultar información, clica en Ver más. Tendrás acceso a enlaces con información de todas las especies registradas y a sus cantos. También puedes hacer click en Nuevo Avistamiento. Sólo tienes que seguir los pasos que se te indicarán. 
Alternativamente, haz click en Ver Todos para examinar los avistamientos realizados por otros usuarios y su ubicación

## Sobre el autor

Mi nombre es Manuel. Acabo de titularme en el CS de Aplicaciones Multiplataforma en el IES San Clemente. 
Me interesa todo lo relacionado con el desarrollo backend y mi objetivo es seguir formándome en este campo. Actualmente, tengo experiencia en Java y SQL, conozco los fundamentos de Python, y estoy realizando las prácticas en una empresa que se dedica sobre todo al desarrollo Web, con lo que tengo cierta agilidad con HTML/CSS.
Este proyecto surge de mezclar una afición -la ornitología- con el objetivo de aprender a manejarme en algunas tecnologías que no conocía hasta el momento.
Puedes contactarme en mgposes@gmail.com


## Índice

> *TODO*: Simplemente indexa ordenadamente todo tu proyecto.

1. Anteproyecto
    * 1.1. [Idea](doc/templates/1_idea.md)
    * 1.2. [Necesidades](doc/templates/2_necesidades.md)
2. [Análisis](doc/templates/3_analise.md)
3. [Planificación](doc/templates/4_planificacion.md)
4. [Diseño](doc/templates/5_deseño.md)
5. [Implantación](doc/templates/6_implantacion.md)


## Guía de contribución


Todas las ideas de mejora son bienvenidas. Actualmente, una mejora interesante sería mostrar fotos de cada especie cuando se reproduce su canto, en el apartado Ver Más de la aplicación. Contáctame para cualquier otra mejora.
