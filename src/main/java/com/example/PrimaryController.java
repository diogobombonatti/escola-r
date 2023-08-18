package com.example;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class PrimaryController {
    
    @FXML TextField txtNome;
    @FXML TextField txtTurma;
    @FXML TextField txtRm;
    @FXML ListView<Aluno> lista;
    @FXML RadioButton optOrdenarPorNome;
    @FXML RadioButton optOrdenarPorTurma;

    ArrayList<Aluno> alunos = new ArrayList<>();

    public void adicionarAluno(){
        var aluno = new Aluno(
            txtNome.getText(),
            txtTurma.getText(),
            Integer.valueOf( txtRm.getText() )
        );

        alunos.add(aluno);

        txtNome.clear();
        txtTurma.clear();
        txtRm.clear();

        atualizarTela();
    }

    public void atualizarTela(){
        ordenar();
        lista.getItems().clear();

        //alunos.forEach(nome -> txtAlunos.appendText(nome + "\n"));

        for (var aluno: alunos){
            lista.getItems().add(aluno);
        }
    }


    private void ordenar(){
        if (optOrdenarPorNome.isSelected()){
            alunos.sort( (o1, o2) -> o1.nome().compareToIgnoreCase(o2.nome()) );
        }

        if (optOrdenarPorTurma.isSelected()){
            alunos.sort( (o1, o2) -> o1.turma().compareToIgnoreCase(o2.turma()) );
        }

    }

    public void apagar(){
        
        Alert mensagem = new Alert(AlertType.CONFIRMATION);
        mensagem.setTitle("Aviso");
        mensagem.setHeaderText("Confirmação");
        mensagem.setContentText("Tem certeza que quer apagar o aluno selecionado? Essa op não pode ser desfeita.");
        var resposta = mensagem.showAndWait();

        if (resposta.isPresent() && resposta.get().equals(ButtonType.OK)){
            var aluno = lista.getSelectionModel().getSelectedItem();
            alunos.remove(aluno);
            atualizarTela();
        }
    }

}

//inner class

