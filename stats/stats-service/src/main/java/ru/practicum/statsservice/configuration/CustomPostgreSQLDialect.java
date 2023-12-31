package ru.practicum.statsservice.configuration;

import io.hypersistence.utils.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class CustomPostgreSQLDialect extends PostgreSQL10Dialect {

    public CustomPostgreSQLDialect() {
        super();
        registerHibernateType(Types.OTHER, JsonNodeBinaryType.class.getName());

        registerFunction(
                "jsonb_extract_path_text",
                new StandardSQLFunction(
                        "jsonb_extract_path_text",
                        StandardBasicTypes.STRING
                )
        );

        registerFunction(
                "replace",
                new SQLFunctionTemplate(
                        StandardBasicTypes.STRING,
                        "replace(?1, ?2, ?3)"
                )
        );
    }

}