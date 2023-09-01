package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 * Classe principal que herda da classe Application do JavaFX para criar e executar a aplicação.
 */
public class App extends Application {

    private static Scene scene; // Cena que será exibida na janela da aplicação.

    @Override
    public void start(Stage stage) throws IOException {
        // Configurando a cena com o conteúdo carregado do arquivo FXML chamando o método loadFXML.
        scene = new Scene(loadFXML("primary"), 600, 600);
        
        // Configurando o título da janela.
        stage.setTitle("Escola");
        
        // Configurando a largura mínima da janela.
        stage.setMinWidth(300);
        
        // Configurações adicionais que podem ser usadas:
        // stage.setResizable(false); // Impede o redimensionamento da janela.
        // stage.setAlwaysOnTop(true); // Mantém a janela sempre no topo.
        // stage.setOpacity(0.5); // Define a opacidade da janela.
        
        // Definindo a cena na janela e exibindo a janela.
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        // Define o conteúdo da cena com o conteúdo carregado do arquivo FXML.
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        // Carrega o arquivo FXML usando um FXMLLoader.
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        
        // Retorna o nó raiz (root) do conteúdo do arquivo FXML.
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        // Método principal que inicia a aplicação JavaFX.
        launch();
    }
}
