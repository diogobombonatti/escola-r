package com.example.controller;

import java.sql.SQLException;

import com.example.data.AlunoDao;
import com.example.model.Aluno;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    TextField txtNome;
    @FXML
    TextField txtTurma;
    @FXML
    TextField txtRm;
    @FXML
    ListView<Aluno> lista;
    @FXML
    RadioButton optOrdenarPorNome;
    @FXML
    RadioButton optOrdenarPorTurma;

    AlunoDao dao = new AlunoDao();

    public void adicionarAluno() {
        var aluno = carregarAlunoDoFormulario();

        try {
            dao.inserir(aluno);
            limparFormulario();
            atualizarTela();
        } catch (SQLException e) {
            mostrarMensagem(AlertType.ERROR, "Erro", e.getMessage());
        }

    }

    private void limparFormulario() {
        txtNome.clear();
        txtTurma.clear();
        txtRm.clear();
    }

    private void mostrarMensagem(AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }

    private Aluno carregarAlunoDoFormulario() {
        return new Aluno(
                txtNome.getText(),
                txtTurma.getText(),
                Integer.valueOf(txtRm.getText()));
    }

    public void atualizarTela() {
        lista.getItems().clear();
        
        try {
            dao.buscarTodos().forEach(aluno -> lista.getItems().add(aluno));
        } catch (SQLException e) {
            mostrarMensagem(AlertType.ERROR, "ERRO", e.getMessage());
        }

    }

    public void apagar(){
        var aluno = lista.getSelectionModel().getSelectedItem();
        try {
            dao.apagar(aluno);
            atualizarTela();
        } catch (SQLException e) {
            mostrarMensagem(AlertType.ERROR, "Erro", e.getMessage());
            
        }
    }

}
