package com.vanar.restApiPulsePoint.DAO;

import com.vanar.restApiPulsePoint.Model.DadosForms;
import org.hibernate.annotations.processing.SQL;

import javax.xml.transform.Result;
import java.sql.*;

public class DadosFormsDAO {

    private final String connection = "";
    private final String user = "";
    private final String password = "";

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(connection, user, password);

    }

    public void save(DadosForms dados) throws SQLException{
        String sql = "INSERT INTO dados_clinicos (id, age, weight, height, bloodType) VALUES(?,?,?,?,?)";
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setLong(1, dados.getId());
        stmt.setInt(2, dados.getAge());
        stmt.setFloat(3, dados.getWeight());
        stmt.setFloat(4, dados.getHeight());
        stmt.setString(5, dados.getBloodType());

        stmt.executeUpdate();
    }

    public float calc_imc(float weight, float height) throws SQLException {
        String sql = "SELECT calc_imc(weight, height) values (?,?)";

        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setFloat(1, weight);
        stmt.setFloat(2, height);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return rs.getFloat("resultado");
        }
        return -1;
    }

    public String alert_imc(float weight, float height) throws SQLException{
        Connection conn = getConnection();
        CallableStatement stmt = conn.prepareCall("{CALL alert_imc(?, ?)}");

        stmt.setFloat(1, weight);
        stmt.setFloat(2, height);
        stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
        stmt.execute();

        return stmt.getString(3);
    }

}
