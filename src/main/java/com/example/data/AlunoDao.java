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

    public void inserir(Aluno aluno) throws SQLException {
        var conexao = DriverManager.getConnection(URL, USER, PASS);

        var sql = "INSERT INTO alunos (nome, turma, rm) VALUES (?, ?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setString(1, aluno.nome());
        comando.setString(2, aluno.turma());
        comando.setInt(3, aluno.rm());
        comando.executeUpdate();

        conexao.close();

    }

    public List<Aluno> buscarTodos() throws SQLException {
        var alunos = new ArrayList<Aluno>();
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var rs = conexao.createStatement().executeQuery("SELECT * FROM alunos ORDER BY nome");

        while (rs.next()) {
            alunos.add(new Aluno(
                    rs.getString("nome"),
                    rs.getString("turma"),
                    rs.getInt("rm")));
        }

        conexao.close();
        return alunos;

    }

    public void apagar(Aluno aluno) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var stm = con.prepareStatement("DELETE FROM alunos WHERE rm=?");
        stm.setInt(1, aluno.rm() );
        stm.executeUpdate();
        con.close();
    }

}
