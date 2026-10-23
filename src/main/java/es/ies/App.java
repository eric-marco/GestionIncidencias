package es.ies;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private ComboBox<String> comboFiltrar;
    private Button botonAñadir;
    private TextField campoTexto;
    private Label mensaje;
    private ListView<String> listaTareas;
    private Button consultarDatos;
    private Button cambiarEstado;
    private Button limpiarResueltas;
    private Button eliminarIncidencia;
    private Label contIncidencias;
    private Label contResueltas;
    private Label contEnProceso;
    private Label contadorAbiertos;
    private Label notificaciones;

    public void start(Stage stage) throws Exception {

        Label titulo = new Label("Gestión de Incidencias");
        titulo.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        campoTexto = new TextField();
        campoTexto.setPromptText("Ingrese el nombre de la incidencia");

        HBox.setHgrow(campoTexto, Priority.ALWAYS);

        botonAñadir = new Button("Añadir tarea");
        botonAñadir.setDefaultButton(true);

        HBox formulario = new HBox(10,
                new Label("Nueva incidencia:"),
                campoTexto,
                new Label("Estado:"),
                botonAñadir);

        formulario.setAlignment(Pos.CENTER_LEFT);

        listaTareas = new ListView<>();
        listaTareas.setPrefHeight(300);
        listaTareas.setPrefWidth(800);

        VBox botonesAccion = new VBox(10,
                consultarDatos = new Button("Consultar datos"),
                cambiarEstado = new Button("Cambiar estado"),
                limpiarResueltas = new Button("Limpiar resueltas"),
                eliminarIncidencia = new Button("Eliminar incidencia"));

        HBox ListaYBotones = new HBox(10, listaTareas, botonesAccion);
        ListaYBotones.setAlignment(Pos.CENTER);

        contIncidencias = new Label("Incidencias:  " + "contadorIncidencias");
        contResueltas = new Label(" Resueltas:  " + "contadorResueltas");
        contEnProceso = new Label(" En proceso:  " + "contadorEnProceso");
        contadorAbiertos = new Label(" Abiertas:  " + "contadorAbiertos");
        notificaciones = new Label("Se ha guardado algo");

        VBox contadoresIzq = new VBox(5, contIncidencias, contResueltas);
        VBox contadoresDer = new VBox(5, contEnProceso, contadorAbiertos);

        HBox contadores = new HBox(20, contadoresIzq, contadoresDer, notificaciones);


        VBox raiz = new VBox(14,
                titulo,
                formulario,
                ListaYBotones,
                contadores
            );

        Scene scene = new Scene(raiz, 820, 500);
        stage.setTitle("Gestor de tareas en JavaFX"); // Le pongo título a la ventana
        stage.setScene(scene); // Asigno el contenido (scene) a la ventana (stage)
        stage.show();

    }

    private void mostrarMensaje(String text) {
        mensaje.setText(text);
    }

    public static void main(String[] args) {
        launch();

        //Comentario para probar primer push gitHub
    }
}
