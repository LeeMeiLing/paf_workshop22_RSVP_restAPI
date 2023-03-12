package sg.edu.nus.iss.paf_workshop22.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException{
    
    private final String tableName;
    private final Integer primaryKey;
    private final String name;
    private final String email;

    // Constructors
    public RecordNotFoundException(String table, Integer pk){
        super(String.format("Cannot find record in %s table with primary key %d",table,pk));
        tableName = table;
        primaryKey = pk;
        this.name = null;
        this.email = null;
    }

    public RecordNotFoundException(String table, String name){
        super(String.format("Cannot find record in %s table with name %s",table,name));
        tableName = table;
        primaryKey = null;
        this.name = name;
        this.email = null;
    }

    public RecordNotFoundException(String table, String name, String email){
        super(String.format("Cannot find record in %s table with email %s",table,email));
        tableName = table;
        primaryKey = null;
        this.name = name;
        this.email = email;
    }

    // Getters
    public Integer getPrimaryKey() {
        return primaryKey;
    }

    public String getTableName() {
        return tableName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    
}
