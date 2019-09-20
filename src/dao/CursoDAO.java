/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Curso;
import conexao.ConexaoMySql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsável pelo conteúdo SQL para tabela Curso CRUD - Create, Recuperate,
 * Update e Delete
 *
 * @author bruno
 */
public class CursoDAO {

    //realiza insert no BD
    public boolean gravarCurso(Curso c) {
        //criar conexão com BD usando método estático
        Connection conn = ConexaoMySql.conexao();
        //instrução para o BD
        String sql = "INSERT INTO cursos(nomeCurso,"
                + "descricao) VALUES (?, ?);";
        try {
            //criação do statement de comunicação
            PreparedStatement ps = conn.prepareStatement(sql);
            //definir os valores dos "?"
            ps.setString(1, c.getNome());
            ps.setString(2, c.getDescricao());
            //executar a instrução SQL
            ps.executeUpdate();
            //encerrar a conexão
            ps.close();
            conn.close();
            //retorno de ok
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // recuperar todos os cursos cadastrados 
    public List<Curso> recuperaTodosCursos() {

        //Criar coneção com banco
        Connection conexao = ConexaoMySql.conexao();
        //Instrução sql
        String sql = "select*from cursos";
        try {
            //statement de conexão
            PreparedStatement ps = conexao.prepareStatement(sql);
            // recebe a tablea de retorno do banco de dados em formato java
            ResultSet rs = ps.executeQuery();
            //criar a lista de retorno
            List<Curso> lista = new ArrayList<>();
            // tratar o retorno do BD
            while (rs.next()) {
                // criar um objeto modelo do tipo do retorno
                Curso c = new Curso();
                c.setIdCurso(rs.getInt(1));
                c.setNome(rs.getString(2));
                c.setDescricao(rs.getString(3));
                //adicionar na lista
                lista.add(c);
            }
            //retornar a lista  preenchida
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // metodo que pesquisa curso
    //de um criterio de seleção
    public List<Curso> pesquisaCursos(String pesquisa) {
        //Criar coneção com banco
        Connection conexao = ConexaoMySql.conexao();
        //Instrução sql
        //LIKE= palavra reservada usado para pesquisa textual
        String sql = "SELECT*FROM cursos" + " WHERE nomeCurso LIKE ?;";
        try {
            //statement de conexão
            PreparedStatement ps = conexao.prepareStatement(sql);
            // define o criterio de pesquisa
            ps.setString(1, "%" + pesquisa + "%");
            //recebe a tabela de retorno a bd
            ResultSet rs = ps.executeQuery();
            //criar a lista de retorno
            List<Curso> lista = new ArrayList<>();
            while (rs.next()) {
                // criar um objeto modelo do tipo do retorno
                Curso c = new Curso();
                c.setIdCurso(rs.getInt(1));
                c.setNome(rs.getString(2));
                c.setDescricao(rs.getString(3));
                //adicionar na lista
                lista.add(c);
            }
            //retornar a lista  preenchida
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
