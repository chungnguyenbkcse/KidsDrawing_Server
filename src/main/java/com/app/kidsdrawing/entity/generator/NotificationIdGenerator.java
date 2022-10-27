package com.app.kidsdrawing.entity.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class NotificationIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Connection connection = session.connection();

        try {
            Statement statement=connection.createStatement();
        
            ResultSet rs=statement.executeQuery("select count(id) as Id from notification");
        
            if(rs.next())
            {
                int id=rs.getInt(1)+1;
                Long generatedId = (long)id;
                return generatedId;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    return null;
    }
}
