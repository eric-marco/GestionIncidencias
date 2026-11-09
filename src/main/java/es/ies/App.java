package es.ies;

import java.util.Optional;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private ComboBox<String> comboFiltrar;
    private TextField campoTexto;
    private TextField cuadroBusqueda;
    private TextField ponerNombre;
    private TextArea ponerDescripcion;
    private Button botonAñadir;
    private ListView<Incidencia> listaIncidencias;
    private ObservableList<Incidencia> incidencias;
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
    private int resueltas = 1;
    private int enProceso = 1;
    private int abiertas = 1;

    public void start(Stage stage) throws Exception {

        Label titulo = new Label("Gestión de Incidencias");
        titulo.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        Region regionHeader = new Region();
        HBox.setHgrow(regionHeader, Priority.ALWAYS);

        comboFiltrar = new ComboBox<>();
        comboFiltrar.getItems().addAll("Abierta", "En proceso", "Resuelta", "Todas");
        comboFiltrar.setPromptText("Filtrar por estado");
        comboFiltrar.setOnAction(e -> filtrar());

        cuadroBusqueda = new TextField();
        cuadroBusqueda.setPromptText("Buscar incidencia");
        cuadroBusqueda.setPrefWidth(300);
        cuadroBusqueda.setOnKeyPressed(e -> buscarPorTexto());

        HBox header = new HBox(30, titulo, regionHeader, comboFiltrar, cuadroBusqueda);
        header.setAlignment(Pos.CENTER);

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

        // He usado IA para añadir las incidencias, pero vamos, que es lo mismo que en
        // un ComboBox

        incidencias = FXCollections.observableArrayList();
        incidencias.add(new Incidencia("Error al iniciar sesión", "No se puede inicar seisón por motivos deconocidos",
                "Abierta"));
        incidencias.add(new Incidencia("No carga la página principal",
                "La página principal del programa no carga y no sabemos por qué", "En proceso"));
        incidencias.add(new Incidencia("Error al guardar", "Al guardar aparecía una ventana de error", "Resuelta"));

        listaIncidencias = new ListView<>(incidencias);
        listaIncidencias.setPrefHeight(300);
        listaIncidencias.setPrefWidth(800);

        listaIncidencias.setCellFactory(param -> new ListCell<Incidencia>() {
            @Override
            protected void updateItem(Incidencia incidencia, boolean vacio) {
                super.updateItem(incidencia, vacio);

                if (vacio || incidencia == null) {
                    setText(null);
                } else {
                    setText("[" + incidencia.getEstado() + "] " + incidencia.getNombre());
                }
            }
        });

        VBox botonesAccion = new VBox(30,
                consultarDatos = new Button("Consultar datos"),
                cambiarEstado = new Button("Cambiar estado"),
                limpiarResueltas = new Button("Limpiar resueltas"),
                eliminarIncidencia = new Button("Eliminar incidencia"));

        consultarDatos.setPrefSize(130, 50);
        consultarDatos.setOnAction(
                e -> consultarInformacion(new Stage(), listaIncidencias.getSelectionModel().getSelectedIndex()));

        cambiarEstado.setPrefSize(130, 50);
        cambiarEstado.setOnAction(e -> cambiarEstado(new Stage()));

        limpiarResueltas.setPrefSize(130, 50);
        limpiarResueltas.setOnAction(e -> limpiarRes());

        eliminarIncidencia.setPrefSize(130, 50);
        eliminarIncidencia.setOnAction(e -> borrar(listaIncidencias.getSelectionModel().getSelectedIndex(), 1));

        botonesAccion.setAlignment(Pos.CENTER);

        HBox ListaYBotones = new HBox(20, listaIncidencias, botonesAccion);
        ListaYBotones.setAlignment(Pos.CENTER);

        // Creamos labels independientes para el título y el valor numérico
        Label lblIncidencias = new Label("Incidencias:");
        contIncidencias = new Label(String.valueOf(contIncid));

        Label lblResueltas = new Label("Resueltas:");
        contResueltas = new Label(String.valueOf(resueltas));

        Label lblEnProceso = new Label("En proceso:");
        contEnProceso = new Label(String.valueOf(enProceso));

        Label lblAbiertas = new Label("Abiertas:");
        contadorAbiertos = new Label(String.valueOf(abiertas));

        notificaciones = new Label();
        notificaciones.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        notificaciones.setMinWidth(Region.USE_PREF_SIZE); // Evita que se recorte el texto de la derecha

        String estiloTarjeta = "-fx-border-color: black;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;" +
                "-fx-background-color: white;" +
                "-fx-padding: 8px 12px;";

        // Llamamos al método para empaquetar cada título y su valor con el estilo de
        // tarjeta
        HBox caja1 = crearCajaContador(lblIncidencias, contIncidencias, estiloTarjeta);
        HBox caja2 = crearCajaContador(lblResueltas, contResueltas, estiloTarjeta);
        HBox caja3 = crearCajaContador(lblEnProceso, contEnProceso, estiloTarjeta);
        HBox caja4 = crearCajaContador(lblAbiertas, contadorAbiertos, estiloTarjeta);

        VBox contadoresIzq = new VBox(5, caja1, caja2);
        VBox contadoresDer = new VBox(5, caja3, caja4);
        HBox contadores = new HBox(20, contadoresIzq, contadoresDer);

        Region separadorInfo = new Region();
        HBox.setHgrow(separadorInfo, Priority.ALWAYS);

        HBox informacion = new HBox(20, contadores, separadorInfo, notificaciones);
        informacion.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(notificaciones, new Insets(0, 20, 0, 0));

        VBox raiz = new VBox(14,
                header,
                formulario,
                ListaYBotones,
                informacion);

        raiz.setPadding(new Insets(20));
        raiz.setStyle("-fx-background-color: #DCE6F1");

        // 16. Permitimos que la lista crezca verticalmente si crece la ventana
        VBox.setVgrow(listaIncidencias, Priority.ALWAYS);

        Scene scene = new Scene(raiz, 1000, 500);
        stage.setTitle("Gestor de incidencias"); // Le pongo título a la ventana
        stage.setResizable(false);
        stage.setScene(scene); // Asigno el contenido (scene) a la ventana (stage)
        stage.show();

    }

    // Funciones para añadir incidencias.
    private void aniadirIncidencia(Stage stage) {

        ponerNombre = new TextField();
        ponerNombre.setPromptText("¿Cuál es la nueva incidencia?");
        ponerNombre.setText(campoTexto.getText());
        ponerNombre.setPrefWidth(500);
        // El TextField ponerNombre esta si el usuario no pone nombre o quiere poner un
        // nombre distinto
        // a la incidencia despues de poner el nombre en el TextField campotexto

        ComboBox<String> comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Abierta", "En proceso", "Resuelta");
        comboEstado.setValue("Abierta");

        HBox nombreYestado = new HBox(30,
                new Label("Nombre de la incidencia:"),
                ponerNombre,
                comboEstado);
        nombreYestado.setAlignment(Pos.CENTER);

        ponerDescripcion = new TextArea();
        ponerDescripcion.setPrefWidth(600);
        ponerDescripcion.setPrefHeight(300);
        ponerDescripcion.setWrapText(true);

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
        aniadir.setDefaultButton(true);
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
        formIncidencia.setStyle("-fx-background-color: #E8EDF5;");

        Scene scene = new Scene(formIncidencia, 900, 350);
        stage.setTitle("Nueva incidencia");
        stage.setScene(scene);
        stage.setResizable(false); // Esto lo he buscado en intrnet que no estaba seguro de cómo se hacía
        stage.show();
    }

    private void confirmarAniadir(String nom, String desc, String estado, Stage stage) {

        if (nom.trim().equals("") || nom == null) {

            mostrarError("No se puede añadir\n" + "una incidencia sin nombre");

        } else {

            listaIncidencias.getItems().addAll(new Incidencia(nom, desc, estado));
            actualizarContadores();
            incidencias.add(new Incidencia(nom, desc, estado));
            mostrarMensaje("Se ha añadido la incidencia.");

        }
        stage.close();
    }

    // Funciones para borrar incidencias
    public void borrar(int ind, int llegada) {
        boolean confirmar = false;
        if (llegada == 1) {
            if (ind < 0) {
                mostrarError("Selecciona una incidencia\n para poder borrarla.");
            } else {
                Alert confirmacion = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "¿Estás seguro de que quieres borrar esta incidencia?",
                        ButtonType.YES,
                        ButtonType.NO);
                Optional respuesta = confirmacion.showAndWait();
                if (respuesta.isPresent() && respuesta.get() == ButtonType.YES)
                    confirmar = true;
            }

        }
        if (llegada == 2 || confirmar) {
            String estado = incidencias.get(ind).getEstado();
            contIncid--;

            switch (estado) {
                case "Abierta":
                    abiertas--;
                    contadorAbiertos.setText(" Abiertas:  " + abiertas);
                    break;
                case "En proceso":
                    enProceso--;
                    contEnProceso.setText(" En Proceso:  " + enProceso);
                    break;
                case "Resuelta":
                    resueltas--;
                    contResueltas.setText(" Resueltas:  " + resueltas);
                    break;
            }
            contIncidencias.setText("Incidencias:  " + contIncid);
            incidencias.remove(ind);
            listaIncidencias.getItems().remove(ind);
            mostrarMensaje("Se ha borrado la incidencia");
        }
    }

    private void limpiarRes() {
        // En este método me compliqué la vida yo solo intentando usar Iterator, y al
        // final
        // la IA me dijo que usase un for y lo resolví inmediatamente.

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION,
                "¿Estás seguro de que quieres borrar esta incidencia?",
                ButtonType.YES,
                ButtonType.NO);
        Optional respuesta = confirmacion.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.YES) {

            for (int ind = 0; ind < incidencias.size(); ind++) {
                Incidencia inc = incidencias.get(ind);
                if (inc.getEstado().equals("Resuelta")) {
                    borrar(ind, 2);
                    ind--;
                }
                mostrarMensaje("Se han limpiado las\n" + "incidencias resueltas");
            }
        }
    }

    // Funciones para cambiar el estado de una incidencia
    public void cambiarEstado(Stage stage) {
        int ind = listaIncidencias.getSelectionModel().getSelectedIndex();
        if (ind < 0) {
            mostrarError("Selecciona una incidencia\n para poder cambiar su estado.");
        }
        ComboBox<String> comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Abierta", "En proceso", "Resuelta");
        comboEstado.setValue(incidencias.get(ind).getEstado());
        comboEstado.setStyle("-fx-font-size: 16px;");

        Label mensajeEstadoActual = new Label("Estado actual de la incidencia: ");
        Label estadoAntiguo = new Label(incidencias.get(ind).getEstado());
        Label estadoNuevo = new Label("Nuevo estado de la incidencia:");

        mensajeEstadoActual.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        estadoAntiguo.setStyle("-fx-font-size: 16px;");
        estadoNuevo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        HBox zonaEstadoAntiguo = new HBox(5,
                mensajeEstadoActual,
                estadoAntiguo);
        zonaEstadoAntiguo.setAlignment(Pos.CENTER);

        HBox zonaEstados = new HBox(20,
                zonaEstadoAntiguo,
                estadoNuevo,
                comboEstado);
        zonaEstados.setAlignment(Pos.CENTER);

        Button cambiar = new Button("Cambiar");
        cambiar.setOnAction(e -> confirmarCambio(stage, comboEstado.getValue(), incidencias.get(ind).getEstado(), ind));
        cambiar.setDefaultButton(true);
        Button cancelar = new Button("Cancelar");
        cancelar.setOnAction(e -> cancelar(stage));

        HBox cambiarYcancelar = new HBox(10,
                cambiar,
                cancelar);
        cambiarYcancelar.setAlignment(Pos.CENTER);

        VBox cambioEstado = new VBox(50, zonaEstados, cambiarYcancelar);
        cambioEstado.setPadding(new Insets(60));
        cambioEstado.setAlignment(Pos.CENTER);
        cambioEstado.setStyle("-fx-background-color: #E8EDF5;");

        Scene scene = new Scene(cambioEstado, 900, 220);
        stage.setTitle("Cambiar estado de la incidencia");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void confirmarCambio(Stage stage, String nEstado, String vEstado, int i) {
        if (nEstado != vEstado) { // Esto está por si el ususario se le va y pone el mismo estado, así ahorramos
                                  // recursos
            incidencias.get(i).setEstado(nEstado);

            actualizarContadores();
            // listaIncidencias.getItems().set();
            // Para cambiar el valor de los objetos de ListView no sabía que necesitaba
            // set(), así que lo estaba intendo con un =
            // y luego le he preguntado a la IA como se cambiaba y me ha mostrado el método
            // set() de Listew.
            // Actualizo los 3 contadores porque buscar cuáles 2 son los que
            // hay que cambiar seguramente es más complicado que hacer esto.
        }
        stage.close();
        mostrarMensaje("Se ha cambiado el\n" + "estado de la incidencia.");
    }

    // Funciones informativas, busqueda y filtrado
    private void consultarInformacion(Stage stage, int i) {

        Label nombreIncidencia = new Label("Nombre: ");
        Label textoNombre = new Label(incidencias.get(i).getNombre());
        textoNombre.setWrapText(true);
        textoNombre.setMaxWidth(750);
        HBox nombreYtexto = new HBox(10, nombreIncidencia, textoNombre);

        Label descripcionIncidencia = new Label("Descripción: ");
        Label textoDescripcion = new Label(incidencias.get(i).getDescripcion());
        textoDescripcion.setWrapText(true);
        textoDescripcion.setMaxWidth(720);
        HBox descripcionYtexto = new HBox(10, descripcionIncidencia, textoDescripcion);

        Label estadoIncidencia = new Label("Estado: ");
        Label textoEstado = new Label(incidencias.get(i).getEstado());
        textoEstado.setWrapText(true);
        HBox estadoYtexto = new HBox(10, estadoIncidencia, textoEstado);

        nombreIncidencia.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        textoNombre.setStyle("-fx-font-size: 16px;");

        descripcionIncidencia.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        textoDescripcion.setStyle("-fx-font-size: 16px;");

        estadoIncidencia.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        textoEstado.setStyle("-fx-font-size: 16px;");

        VBox informacion = new VBox(20,
                nombreYtexto,
                descripcionYtexto,
                estadoYtexto);
        informacion.setPadding(new Insets(60));
        informacion.setAlignment(Pos.CENTER_LEFT);
        VBox.setVgrow(informacion, Priority.ALWAYS);
        informacion.setStyle("-fx-background-color: #E8EDF5;");

        Scene scene = new Scene(informacion, 900, 220);
        stage.setTitle("Informacion de la incidencia");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();

    }

private void buscarPorTexto() {

    String textoBusqueda = cuadroBusqueda.getText().toLowerCase();

    ObservableList<Incidencia> resultados = FXCollections.observableArrayList();

    for (Incidencia inc : incidencias) {

        if (inc.getNombre().toLowerCase().contains(textoBusqueda)) {
            resultados.add(inc);
        }
    }

    listaIncidencias.setItems(resultados);
}

private void filtrar() {

    String estadoFiltrar = comboFiltrar.getValue();

    ObservableList<Incidencia> resultados = FXCollections.observableArrayList();

    for (Incidencia inc : incidencias) {

        if (estadoFiltrar.equals("Todas") ||
                inc.getEstado().equals(estadoFiltrar)) {

            resultados.add(inc);
        }
    }

    listaIncidencias.setItems(resultados);
}

    private void mostrarMensaje(String text) {
        notificaciones.setText(text);
        notificaciones.setStyle("-fx-background-color:transparent;" + "-fx-text-fill:black;-fx-font-size: 16px;"
                + "-fx-font-weight: bold;");
    }

    private void mostrarError(String text) {
        notificaciones.setText(text);
        notificaciones.setStyle("-fx-border-color: red; -fx-border-style: solid; -fx-border-width: 2px;" +
                "-fx-background-color:#FF746D;" + "-fx-text-fill: red;" + "-fx-font-size: 16px;-fx-font-weight: bold;");
    }

    private void actualizarContadores() {
        
    }

    private void cancelar(Stage stage) {
        stage.close();
    }

    private HBox crearCajaContador(Label lblTexto, Label lblValor, String estilo) {
        Region muelle = new Region();
        HBox.setHgrow(muelle, Priority.ALWAYS); // Esto hace de muelle y empuja el valor a la derecha

        HBox caja = new HBox(lblTexto, muelle, lblValor);
        caja.setPrefWidth(220);
        caja.setAlignment(Pos.CENTER_LEFT);
        caja.setStyle(estilo);
        return caja;
    }

    public static void main(String[] args) {
        launch();
    }
}