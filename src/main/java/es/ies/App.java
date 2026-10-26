package es.ies;

//Prueba de push ppor mi parte.

import javafx.application.Application;
import javafx.geometry.Insets;
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
import java.util.ArrayList;

public class App extends Application {

    private ComboBox<String> comboFiltrar;
    private TextField campoTexto;
    private TextField cuadroBusqueda;
    private TextField ponerNombre;
    private TextField ponerDescripcion;
    private Button botonAñadir;
    private Label mensaje;
    private ListView<String> listaIncidencias;
    private ArrayList<Incidencia> incidencias;
    private Button consultarDatos;
    private Button cambiarEstado;
    private Button limpiarResueltas;
    private Button eliminarIncidencia;
    private Label contIncidencias;
    private Label contResueltas;
    private Label contEnProceso;
    private Label contadorAbiertos;
    private Label notificaciones;
    private int contIncid = 3;
    private int contRes = 1;
    private int contEnProc = 1;
    private int contAb = 1;

    public void start(Stage stage) throws Exception {

        Label titulo = new Label("Gestión de Incidencias");
        titulo.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        comboFiltrar = new ComboBox<>();
        comboFiltrar.getItems().addAll("Abierta", "En proceso ", "Resuelta");
        comboFiltrar.setPromptText("Filtrar por estado");

        cuadroBusqueda = new TextField();
        cuadroBusqueda.setPromptText("Buscar incidencia");
        cuadroBusqueda.setPrefWidth(300);

        // He usado la IA para poner el margin a la izquierda del comboBox
        HBox.setMargin(
                comboFiltrar,
                new Insets(0, 0, 0, 150));

        HBox header = new HBox(30, titulo, comboFiltrar, cuadroBusqueda);

        campoTexto = new TextField();
        campoTexto.setPromptText("Ingrese el nombre de la incidencia");

        HBox.setHgrow(campoTexto, Priority.ALWAYS);

        botonAñadir = new Button("Añadir tarea");
        botonAñadir.setDefaultButton(true);
        botonAñadir.setOnAction(e -> aniadirIncidencia(new Stage()));

        HBox formulario = new HBox(10,
                new Label("Nueva incidencia:"),
                campoTexto,
                botonAñadir);

        formulario.setAlignment(Pos.CENTER_LEFT);

        listaIncidencias = new ListView<>();
        listaIncidencias.setPrefHeight(300);
        listaIncidencias.setPrefWidth(800);
        // He usado IA para añadir las incidencias, pero vamos, que es lo mismo que en
        // un ComboBox

        listaIncidencias.getItems().addAll(
                "[Abierta] Error al iniciar sesión",
                "[En proceso] No carga la página principal",
                "[Resuelta] Error al guardar los cambios");
        incidencias = new ArrayList<>();
        incidencias.add(new Incidencia("Error al iniciar sesión", "No se puede inicar seisón por motivos deconocidos",
                "Abierta"));
        incidencias.add(new Incidencia("No carga la página principal",
                "La página principal del programa no carga y no sabemos por qué", "En proceso"));
        incidencias.add(new Incidencia("Error al guardar", "Al guardar aparecía una ventana de error", "Resuelta"));

        VBox botonesAccion = new VBox(30,
                consultarDatos = new Button("Consultar datos"),
                cambiarEstado = new Button("Cambiar estado"),
                limpiarResueltas = new Button("Limpiar resueltas"),
                eliminarIncidencia = new Button("Eliminar incidencia"));

        consultarDatos.setPrefSize(130, 50);
        cambiarEstado.setPrefSize(130, 50);
        limpiarResueltas.setPrefSize(130, 50);
        eliminarIncidencia.setPrefSize(130, 50);
        eliminarIncidencia.setOnAction(e -> borrar());
        botonesAccion.setAlignment(Pos.CENTER);

        HBox ListaYBotones = new HBox(20, listaIncidencias, botonesAccion);
        ListaYBotones.setAlignment(Pos.CENTER);

        contIncidencias = new Label("Incidencias:  " + "                              " + contIncid);
        contResueltas = new Label(" Resueltas:  " + "                              " + contRes);
        contEnProceso = new Label(" En proceso:  " + "                              " + contEnProc);
        contadorAbiertos = new Label(" Abiertas:  " + "                              " + contAb);
        notificaciones = new Label("Se ha guardado algo");

        // He usado la IA para asegurarme de como poner un borde a los label y dejar el
        // radio del borde circular

        contIncidencias.setPrefWidth(200);
        contResueltas.setPrefWidth(200);
        contEnProceso.setPrefWidth(200);
        contadorAbiertos.setPrefWidth(200);

        contIncidencias.setStyle(
                "-fx-border-color: black;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 10px;");

        contResueltas.setStyle(
                "-fx-border-color: black;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 10px;");

        contEnProceso.setStyle(
                "-fx-border-color: black;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 10px;");

        contadorAbiertos.setStyle(
                "-fx-border-color: black;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 10px;");

        VBox contadoresIzq = new VBox(5, contIncidencias, contResueltas);
        VBox contadoresDer = new VBox(5, contEnProceso, contadorAbiertos);

        HBox contadores = new HBox(20, contadoresIzq, contadoresDer);
        HBox informacion = new HBox(200, contadores, notificaciones);
        contadores.setAlignment(Pos.CENTER);
        informacion.setAlignment(Pos.CENTER);

        VBox raiz = new VBox(14,
                header,
                formulario,
                ListaYBotones,
                informacion);

        raiz.setPadding(new Insets(20));

        // 16. Permitimos que la lista crezca verticalmente si crece la ventana
        VBox.setVgrow(listaIncidencias, Priority.ALWAYS);

        Scene scene = new Scene(raiz, 1000, 500);
        stage.setTitle("Gestor de incidencias"); // Le pongo título a la ventana
        stage.setScene(scene); // Asigno el contenido (scene) a la ventana (stage)
        stage.show();

    }

    private void aniadirIncidencia(Stage stage) {
        ponerNombre = new TextField();
        ponerNombre.setPromptText("¿Cuál es la nueva incidencia?");
        ponerNombre.setPrefWidth(500);

        ComboBox<String> comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Abierta", "En proceso ", "Resuelta");
        comboEstado.setPromptText("Estado de la incidencia");

        HBox nombreYestado = new HBox(30,
                new Label("Nombre de la incidencia:"),
                ponerNombre,
                comboEstado);
        nombreYestado.setAlignment(Pos.CENTER);

        ponerDescripcion = new TextField();
        ponerDescripcion.setPrefWidth(600);
        ponerDescripcion.setPrefHeight(300);

        HBox descripcion = new HBox(30,
                new VBox(6,
                        new Label("Descripción de la incidencia:"),
                        ponerDescripcion));
        descripcion.setAlignment(Pos.CENTER);

        Button aniadir = new Button("Añadir");
        aniadir.setOnAction(e -> confirmarAniadir(
                ponerNombre.getText(),
                ponerDescripcion.getText(),
                comboEstado.getValue(),
                stage));
        Button cancelar = new Button("Cancelar");
        cancelar.setOnAction(e -> cancelar(stage));

        HBox aniadirYcancelar = new HBox(10,
                aniadir,
                cancelar);
        aniadirYcancelar.setAlignment(Pos.CENTER);

        VBox formIncidencia = new VBox(14,
                nombreYestado,
                descripcion,
                aniadirYcancelar);
        formIncidencia.setPadding(new Insets(20));

        Scene scene = new Scene(formIncidencia, 900, 350);
        stage.setTitle("Nueva incidencia");
        stage.setScene(scene);
        stage.setResizable(false); // Esto lo he buscado en intrnet que no estaba seguro de cómo se hacía
        stage.show();
    }

    private void cancelar(Stage stage) {
        stage.close();
    }

    private void confirmarAniadir(String nom, String desc, String estado, Stage stage) {
        listaIncidencias.getItems().addAll("[" + estado + "] " + nom);
        contIncid++;
        contIncidencias.setText("Incidencias:  " + "                              " + contIncid);
        // El setText() lo he puesto para que se actualice el texto y el número cambie,
        // pero no sabía que método usar así que he preguntado a la IA y me dijo
        // setText().
        switch (estado) {
            case "Abierta":
                contAb++;
                contadorAbiertos.setText(" Abiertas:  " + "                                  " + contAb);
                break;
            case "En proceso ":
                contEnProc++;
                contEnProceso.setText(" En Proceso:  " + "                              " + contEnProc);
                break;
            case "Resuelta":
                contRes++;
                contResueltas.setText(" Resueltas:  " + "                              " + contRes);
                break;
        }
        incidencias.add(new Incidencia(nom, desc, estado));
        stage.close();
    }

    public void borrar(){
        int ind = listaIncidencias.getSelectionModel().getSelectedIndex();
        incidencias.remove(ind);
        listaIncidencias.getItems().remove(ind);
    }

    public void cambiarEstado(Stage stage){
        int ind = listaIncidencias.getSelectionModel().getSelectedIndex();
    }

    private void mostrarMensaje(String text) {
        mensaje.setText(text);
    }

    public static void main(String[] args) {
        launch();

        // Comentario para probar primer push gitHub
    }
}