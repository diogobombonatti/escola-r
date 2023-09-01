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

    // Método executado quando o botão de adicionar aluno é clicado.
    public void adicionarAluno() {
        var aluno = carregarAlunoDoFormulario();

        try {
            dao.inserir(aluno); // Insere o aluno no banco de dados.
            limparFormulario(); // Limpa os campos do formulário.
            atualizarTela(); // Atualiza a lista na tela.
        } catch (SQLException e) {
            mostrarMensagem(AlertType.ERROR, "Erro", e.getMessage()); // Mostra mensagem de erro.
        }
    }

    // Limpa os campos do formulário.
    private void limparFormulario() {
        txtNome.clear();
        txtTurma.clear();
        txtRm.clear();
    }

    // Mostra uma mensagem em uma janela de alerta.
    private void mostrarMensagem(AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }

    // Carrega um objeto Aluno com base nos dados do formulário.
    private Aluno carregarAlunoDoFormulario() {
        return new Aluno(
                txtNome.getText(),
                txtTurma.getText(),
                Integer.valueOf(txtRm.getText()));
    }

    // Atualiza a lista de alunos na tela.
    public void atualizarTela() {
        lista.getItems().clear(); // Limpa a lista existente.
        
        try {
            dao.buscarTodos().forEach(aluno -> lista.getItems().add(aluno)); // Busca e adiciona alunos à lista.
        } catch (SQLException e) {
            mostrarMensagem(AlertType.ERROR, "ERRO", e.getMessage()); // Mostra mensagem de erro.
        }
    }

    // Método executado quando o botão de apagar aluno é clicado.
    public void apagar(){
        var aluno = lista.getSelectionModel().getSelectedItem(); // Pega o aluno selecionado na lista.
        try {
            dao.apagar(aluno); // Apaga o aluno do banco de dados.
            atualizarTela(); // Atualiza a lista na tela.
        } catch (SQLException e) {
            mostrarMensagem(AlertType.ERROR, "Erro", e.getMessage()); // Mostra mensagem de erro.
        }
    }
}
