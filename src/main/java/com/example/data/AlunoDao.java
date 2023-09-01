package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Aluno;

public class AlunoDao {

    final String URL = "jdbc:mysql://auth-db719.hstgr.io:3306/u553405907_fiap";
    // final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    final String USER = "u553405907_fiap";
    final String PASS = "u553405907_FIAP";

    // Insere um aluno na tabela de alunos.
    public void inserir(Aluno aluno) throws SQLException {
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        
        // Define a instrução SQL para inserção de dados.
        var sql = "INSERT INTO alunos (nome, turma, rm) VALUES (?, ?, ?) ";
        var comando = conexao.prepareStatement(sql);
        
        // Define os parâmetros da instrução SQL usando os atributos do objeto Aluno.
        comando.setString(1, aluno.nome());
        comando.setString(2, aluno.turma());
        comando.setInt(3, aluno.rm());
        
        // Executa a instrução SQL de inserção.
        comando.executeUpdate();
        
        // Fecha a conexão com o banco de dados.
        conexao.close();
    }

    // Busca todos os alunos na tabela de alunos.
    public List<Aluno> buscarTodos() throws SQLException {
        var alunos = new ArrayList<Aluno>();
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        
        // Executa a consulta SQL para buscar todos os alunos.
        var rs = conexao.createStatement().executeQuery("SELECT * FROM alunos ORDER BY nome");

        // Percorre o resultado da consulta e cria objetos Aluno para cada linha.
        while (rs.next()) {
            alunos.add(new Aluno(
                    rs.getString("nome"),
                    rs.getString("turma"),
                    rs.getInt("rm")));
        }
        
        // Fecha a conexão com o banco de dados.
        conexao.close();
        return alunos;
    }

    // Apaga um aluno da tabela de alunos.
    public void apagar(Aluno aluno) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        
        // Define a instrução SQL para apagar um aluno.
        var stm = con.prepareStatement("DELETE FROM alunos WHERE rm=?");
        stm.setInt(1, aluno.rm() );
        
        // Executa a instrução SQL de deleção.
        stm.executeUpdate();
        
        // Fecha a conexão com o banco de dados.
        con.close();
    }
}
