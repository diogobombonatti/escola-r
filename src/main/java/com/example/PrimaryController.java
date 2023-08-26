package com.example;

import java.sql.DriverManager;
import java.sql.SQLException;
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

        final String URL = "jdbc:mysql://auth-db719.hstgr.io:3306/u553405907_fiap";
        //final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        final String USER = "u553405907_fiap";
        final String PASS = "u553405907_FIAP";

        // conectar com o banco de dados
        try {
            var conexao = DriverManager.getConnection(URL, USER, PASS);
            var sql = "INSERT INTO alunos (nome, turma, rm) VALUES (?, ?, ?) ";
            var comando = conexao.prepareStatement(sql);
            comando.setString(1, aluno.nome());
            comando.setString(2, aluno.turma());
            comando.setInt(3, aluno.rm());
            comando.executeUpdate();
            
            conexao.close();
        } catch (SQLException e) {
            Alert mensagem = new Alert(AlertType.ERROR);
            mensagem.setTitle("ERRO");
            mensagem.setContentText(e.getMessage());
            mensagem.show();
        }

        // fazer o insert

        // fechar a conexao

        txtNome.clear();
        txtTurma.clear();
        txtRm.clear();

        atualizarTela();
    }

    public void atualizarTela(){
        lista.getItems().clear();

        // abrir conexao
        final String URL = "jdbc:mysql://auth-db719.hstgr.io:3306/u553405907_fiap";
        //final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        final String USER = "u553405907_fiap";
        final String PASS = "u553405907_FIAP";

        try {
            var conexao = DriverManager.getConnection(URL, USER, PASS);
            //executar o select
            var comando = conexao.createStatement();
            var alunos = comando.executeQuery("SELECT * FROM alunos ORDER BY nome");
            
            //preencher o listView
            while(alunos.next()){
                var aluno = new Aluno(
                    alunos.getString("nome"),
                    alunos.getString("turma"),
                    alunos.getInt("rm")
                );
                lista.getItems().add(aluno);
            }

            conexao.close();
        } catch (SQLException e) {
            Alert mensagem = new Alert(AlertType.ERROR);
            mensagem.setTitle("ERRO");
            mensagem.setContentText(e.getMessage());
            mensagem.show();
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

